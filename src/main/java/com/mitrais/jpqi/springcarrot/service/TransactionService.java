package com.mitrais.jpqi.springcarrot.service;

import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.model.AggregateModel.AchievementEachMonth;
import com.mitrais.jpqi.springcarrot.model.AggregateModel.Hasil;
import com.mitrais.jpqi.springcarrot.repository.*;
import com.mitrais.jpqi.springcarrot.responses.TransactionResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@EnableScheduling
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private CarrotRepository carrotRepository;
    @Autowired
    CarrotServiceUsingDB carrotServiceUsingDB;
    @Autowired
    private FreezerRepository freezerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private SocialFoundationRepository socialFoundationRepository;
    @Autowired
    private EmployeeServiceUsingDB employeeServiceUsingDB;
    @Autowired
    private BarnService barnService;
    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    private AwardRepository awardRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    SimpMessagingTemplate template;

    public TransactionResponse findAllTransactions() {
        TransactionResponse res = new TransactionResponse();
        res.setStatus(true);
        res.setMessage("find all transaction");
        res.setListTransaction(transactionRepository.findAll());
        return res;
    }

    public TransactionResponse updateTransaction(String id, Transaction transaction) {
        TransactionResponse res = new TransactionResponse();
        try {
            transaction.setId(id);
            transactionRepository.save(transaction);
            res.setStatus(true);
            res.setMessage("transaction successfully updated");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public TransactionResponse createTransaction(Transaction transaction) {
        transaction.setId(new ObjectId().toString());
        TransactionResponse res = new TransactionResponse();
        System.out.println("in create transaction function");
        //set the transaction date
        if (transaction.getTransaction_date() == null) { transaction.setTransaction_date(LocalDateTime.now()); }
        transaction.setStatus(Transaction.Status.PENDING);

        if (transaction.getType() == Transaction.Type.REWARD) {
            //System.out.println(new Gson().toJson(transaction));
            //Codes if the transaction is a reward (from manager freezer to employee's basket)
            if (transaction.getFreezer_from() != null) {
                System.out.println("==== In reward manager -> employee ====");
                Freezer f_from = transaction.getFreezer_from();
                Basket b_to = transaction.getDetail_to();

                transaction.setFrom(f_from.getName());
                transaction.setTo(b_to.getName());

                Notification notif = new Notification();
                notif.setRead(false);
                notif.setDetail(b_to.getEmployee().getName() + " claimed an achievement");
                this.sendNotifToEmployee(notif, f_from.getEmployee());
            }
            else {
                System.out.println("==== In reward system -> employee ====");
                Barn barn = barnService.getLatestBarn().get(0);
                long newAmount = barn.getCarrotLeft() - transaction.getCarrot_amt();
                barn.setCarrotLeft(newAmount);
                barnRepository.save(barn);

                Basket b_to = basketRepository.findById(transaction.getDetail_to().getId()).get();
                double newAmount1 = b_to.getCarrot_amt() + transaction.getCarrot_amt();
                b_to.setCarrot_amt(newAmount1);
                basketRepository.save(b_to);

                //Update Carrot ownership
                List<Carrot> carrots = carrotRepository.findCarrotByBarnIdAndType(new ObjectId(barn.getId()), "FRESH");
                int count = transaction.getCarrot_amt();
                for (int i = 0 ; i < count ; i++) {
                    Carrot c = carrots.get(i);
                    c.setBasket(b_to);
                    c.setUpdated_at(LocalDateTime.now());
                    c.setType(Carrot.Type.NORMAL);
                    carrotRepository.save(c);
                }
                transaction.setStatus(Transaction.Status.APPROVED);
            }
        }

        else if (transaction.getType() == Transaction.Type.SHARED) {

            Basket b_from = transaction.getDetail_from();
            Basket b_to = transaction.getDetail_to();

            //Edit the sender carrot amount in basket
            Optional<Basket> b = basketRepository.findById(b_from.getId());
            Basket basket = b.get();
            Double newAmount = basket.getCarrot_amt() - transaction.getCarrot_amt();
            basket.setCarrot_amt(newAmount);
            basketRepository.save(basket);

            //Edit the recipient carrot amount in basket
            Optional<Basket> b1 = basketRepository.findById(b_to.getId());
            Basket basket1 = b1.get();
            Double newAmount1 = basket1.getCarrot_amt() + transaction.getCarrot_amt();
            basket1.setCarrot_amt(newAmount1);
            basketRepository.save(basket1);

            //update the carrots in sender's basket into recipient's basket
            List<Carrot> carrots = carrotRepository.findByBasketId(new ObjectId(b_from.getId()));
            int count = transaction.getCarrot_amt();
            for (int i = 0 ; i <count ; i++) {
                Carrot c = carrots.get(i);
                c.getBasket().setId(b_to.getId());
                carrotRepository.save(c);
            }

            transaction.setFrom(b_from.getName());
            transaction.setTo(b_to.getName());
            transaction.setStatus(Transaction.Status.APPROVED);
        }

        // For Donation or claim item in bazaar
        else if (transaction.getType() == Transaction.Type.BAZAAR || transaction.getType() == Transaction.Type.DONATION) {
            Basket b_from = transaction.getDetail_from();
            if (basketRepository.findById(b_from.getId()).isPresent()){
                Basket basket = basketRepository.findById(b_from.getId()).get();
                if (basket.getCarrot_amt() >= transaction.getCarrot_amt()) {
                    double newAmount = basket.getCarrot_amt() - transaction.getCarrot_amt();
                    basket.setCarrot_amt(newAmount);
                    basketRepository.save(basket);
                    //update the carrots in sender's basket into recipient's basket
                    List<Carrot> carrots = carrotRepository.findByBasketId(new ObjectId(b_from.getId()))
                            .stream().filter(Carrot::isUsable).collect(Collectors.toList());
                    int count = transaction.getCarrot_amt();
                    for (int i = 0 ; i < count ; i++) {
                        Carrot c = carrots.get(i);
                        c.setUsable(false);
                        carrotRepository.save(c);
                    }
                    System.out.println("Finished updating carrot to unusable");

                    if (transaction.getType() == Transaction.Type.BAZAAR) {
                        Item requested_Item = transaction.getRequested_item();
                        Optional<Item> i = itemRepository.findById(requested_Item.getId());
                        Item item = i.get();
                        item.setItemSold((item.getItemSold() +1));
                        item.setTotalItem((item.getTotalItem() -1));
                        itemRepository.save(item);

                        transaction.setFrom(b_from.getName());
                        transaction.setTo(item.getBazaar().getBazaarName());


                        Notification notif = new Notification();
                        notif.setRead(false);
                        notif.setDetail(b_from.getEmployee().getName() + " bought an item from bazaar");
                        this.sendNotifToAdmin(notif, "ADMIN");
                    }
                    else {
                        SocialFoundation socialFoundation = transaction.getSocialFoundation();
                        Optional<SocialFoundation> sfs = socialFoundationRepository.findById(socialFoundation.getId());
                        if (sfs.isPresent()) {
                            SocialFoundation sf = sfs.get();
                            double newFoundationAmount = sf.getTotal_carrot() + transaction.getCarrot_amt();
                            sf.setTotal_carrot(newFoundationAmount);
                            if (sf.getPendingDonations() == null) {
                                sf.setPendingDonations(new ArrayList<>());
                            }
                            sf.getPendingDonations().add(transaction);
                            socialFoundationRepository.save(sf);

                            transaction.setFrom(b_from.getName());
                            transaction.setTo(socialFoundation.getName());

                            Notification notif = new Notification();
                            notif.setRead(false);
                            notif.setDetail(b_from.getEmployee().getName() + " donate her/his carrots");
                            this.sendNotifToAdmin(notif, "ROOT_ADMIN");
                        }
                    }
                }
                else {
                    System.out.println("Insufficient amount");
                    res.setStatus(false);
                    res.setMessage("Insufficient amount");
                    return res;
                }
            }
        }

        else if(transaction.getType() == Transaction.Type.FUNNEL){
            funnelTransaction(transaction);
            transaction.setStatus(Transaction.Status.APPROVED);
        }

        else if(transaction.getType() == Transaction.Type.REQUEST){
            System.out.println("Creating request for frozen carrot");
        }
        System.out.println("=====Finished updating other entity=====");
        try {
            transactionRepository.save(transaction);
            res.setStatus(true);
            res.setTransaction(transaction);
            res.setMessage("transaction successfully added");
        } catch (NullPointerException e) {
            res.setStatus(false);
            res.setMessage(e.getMessage());
        }
        return res;
    }

    public void funnelTransaction(Transaction transaction){
        //Funnel for From SM to M
        if(transaction.getFreezer_from() != null){
            Freezer f_from = freezerRepository.findByOwner(new ObjectId(transaction.getFreezer_from().getEmployee().getId()));
            Freezer f_to = freezerRepository.findByOwner(new ObjectId(transaction.getFreezer_to().getEmployee().getId()));

            //Update Freezer amount of SM
            double newCarrotAmount = f_from.getCarrot_amt() - transaction.getCarrot_amt();
            f_from.setCarrot_amt(newCarrotAmount);
            freezerRepository.save(f_from);

            //Update Freezer amount of M
            double newCarrotAmount1 = f_to.getCarrot_amt() + transaction.getCarrot_amt();
            f_to.setCarrot_amt(newCarrotAmount1);
            freezerRepository.save(f_to);

            //Update carrot ownership
            List<Carrot> carrots = carrotRepository.findByFreezerId(new ObjectId(f_from.getId()));
            int count = transaction.getCarrot_amt();
            for (int i = 0 ; i < count ; i++) {
                Carrot c = carrots.get(i);
                c.setFreezer(f_to);
                c.setUpdated_at(LocalDateTime.now());
                carrotRepository.save(c);
            }
        }
        //Funnel for from Barn to SM
        else if (transaction.getFreezer_from() == null) {
            System.out.println("=====Inside Funnel Barn=====");
            Freezer f_to = freezerRepository.findByOwner(new ObjectId(transaction.getFreezer_to().getEmployee().getId()));
            Barn barn = barnService.findBarnById(transaction.getBarn().getId()).getBarn();

            //Update SM freezer amount
            double newCarrotAmount = f_to.getCarrot_amt() + transaction.getCarrot_amt();
            f_to.setCarrot_amt(newCarrotAmount);
            freezerRepository.save(f_to);

            //Update Barn carrot left
            long newCarrotLeft = barn.getCarrotLeft() - transaction.getCarrot_amt();
            System.out.println(newCarrotLeft);
            barn.setCarrotLeft(newCarrotLeft);
            barnRepository.save(barn);

            //Update Carrot ownership
            List<Carrot> carrots = carrotRepository.findCarrotByBarnIdAndType(new ObjectId(barn.getId()), "FRESH");
            int count = transaction.getCarrot_amt();
            for (int i = 0 ; i < count ; i++) {
                Carrot c = carrots.get(i);
                c.setFreezer(f_to);
                c.setUpdated_at(LocalDateTime.now());
                c.setType(Carrot.Type.FROZEN);
                carrotRepository.save(c);
            }
        }
    }

    public TransactionResponse approveRequestTransaction (String id){
        TransactionResponse res = new TransactionResponse();

        if (transactionRepository.findById(id).isPresent()){
            Transaction transaction = transactionRepository.findById(id).get();
            try {
                transaction.setStatus(Transaction.Status.APPROVED);
                funnelTransaction(transaction);
                transactionRepository.save(transaction);
                res.setStatus(true);
                res.setMessage("transaction approved");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        }
        return res;
    }

    public TransactionResponse approveTransaction(String id) {
        TransactionResponse res = new TransactionResponse();

        if (transactionRepository.findById(id).isPresent()) {
            Transaction transaction = transactionRepository.findById(id).get();
            if (transaction.getType() == Transaction.Type.BAZAAR) {
                makeApprovedTransaction(transaction);
            }

            else if (transaction.getType() == Transaction.Type.REWARD) {
                Freezer f_from = transaction.getFreezer_from();
                Basket b_to = transaction.getDetail_to();

                //Add the achievement to employee
                String empId = transaction.getDetail_to().getEmployee().getId();
                employeeServiceUsingDB.addAchievementToEmployee(empId, transaction.getAchievementClaimed());

                //Edit the recipient carrot amount in basket
                Optional<Basket> b = basketRepository.findById(b_to.getId());
                Basket basket = b.get();
                basket.setCarrot_amt((basket.getCarrot_amt() + transaction.getCarrot_amt()));
                basketRepository.save(basket);

                //Change the carrot amount in manager's freezer
                Optional<Freezer> f = freezerRepository.findById(f_from.getId());
                Freezer freezer= f.get();
                freezer.setCarrot_amt((freezer.getCarrot_amt() - transaction.getCarrot_amt()));
                freezerRepository.save(freezer);

                //update the carrots in manager's freezer into employee's basket
                List<Carrot> carrots = carrotRepository.findByFreezerId(new ObjectId(f_from.getId()));
                int count = transaction.getCarrot_amt();

                for (int i = 0 ; i < count ; i++) {
                    Carrot c = carrots.get(i);
                    c.setBasket( new Basket());
                    c.getBasket().setId(b_to.getId());
                    c.setFreezer(null);
                    c.setType(Carrot.Type.NORMAL);
                    carrotRepository.save(c);
                }
                transaction.setFrom(f_from.getName());
                transaction.setTo(b_to.getName());
            }
            transaction.setStatus(Transaction.Status.APPROVED);
            try {
                transactionRepository.save(transaction);
                res.setStatus(true);
                res.setMessage("transaction approved");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
            return res;
        }

        res.setStatus(false);
        res.setMessage("transaction not found");
        return res;
    }

    public TransactionResponse approveDonation(String sfId) {
        TransactionResponse res = new TransactionResponse();

        if (socialFoundationRepository.findById(sfId).isPresent()) {
            SocialFoundation sf = socialFoundationRepository.findById(sfId).get();
            List<Transaction> pendingDonations = sf.getPendingDonations();

            try {
                pendingDonations.forEach(transaction -> {
                    makeApprovedTransaction(transaction);
                    System.out.println("==Approved Transactions==");
                    transaction.setStatus(Transaction.Status.APPROVED);
                    transactionRepository.save(transaction);
                });
                sf.setPendingDonations(null);
                sf.setTotal_carrot(0);
                socialFoundationRepository.save(sf);

                res.setStatus(true);
                res.setMessage("Donations to this Social Foundation are all successful");
            } catch (Exception e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        }
        return res;
    }

    public TransactionResponse declineDonation(String sfId) {
        TransactionResponse res = new TransactionResponse();

        if (socialFoundationRepository.findById(sfId).isPresent()) {
            SocialFoundation sf = socialFoundationRepository.findById(sfId).get();

            List<Transaction> pendingDonations = sf.getPendingDonations();

            try {
                pendingDonations.forEach(transaction -> makeDeclinedTransaction(transaction));
                res.setStatus(true);
                res.setMessage("Donations to this Social Foundation are all successful");
            } catch (Exception e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
        }

        return res;
    }

    //used by another method
    public void makeApprovedTransaction (Transaction transaction) {
        System.out.println(".Making approved transaction...");
        Basket requester = transaction.getDetail_from();
        List<Carrot> pendingCarrots = carrotRepository.findByBasketId(new ObjectId(requester.getId()))
                .stream().filter(c -> !c.isUsable()).collect(Collectors.toList());
        System.out.println("Pending carrots amount = " + pendingCarrots.size());
        int count = transaction.getCarrot_amt();
        System.out.println("Count = " + count);
        for (int i = 0; i < count; i++) {
            Carrot c = pendingCarrots.get(i);
            c.setType(Carrot.Type.INACTIVE);
            c.setBasket(null);
            carrotRepository.save(c);
            System.out.println("Iterasi =" +(i+1));
        }
    }

    public TransactionResponse declineTransaction(String id) {
        TransactionResponse res = new TransactionResponse();
        if (transactionRepository.findById(id).isPresent()) {
            Transaction transaction = transactionRepository.findById(id).get();
            if (transaction.getType() == Transaction.Type.BAZAAR) {
                makeDeclinedTransaction(transaction);

                Item requested_Item = transaction.getRequested_item();
                Optional<Item> i = itemRepository.findById(requested_Item.getId());
                Item item = i.get();
                item.setItemSold((item.getItemSold() -1));
                item.setTotalItem((item.getTotalItem() +1));
                itemRepository.save(item);
            }
            if (transaction.getType() == Transaction.Type.DONATION) {
                if(transaction.getSocialFoundation().getTotal_carrot()
                        < transaction.getSocialFoundation().getMin_carrot()){
                    makeDeclinedTransaction(transaction);
                }
            }

            transaction.setStatus(Transaction.Status.DECLINED);

            try {
                transactionRepository.save(transaction);
                res.setStatus(true);
                res.setMessage("transaction declined");
            } catch (NullPointerException e) {
                res.setStatus(false);
                res.setMessage(e.getMessage());
            }
            return res;
        }
        res.setStatus(false);
        res.setMessage("transaction not found");
        return res;
    }

    //used by another method
    public void makeDeclinedTransaction (Transaction transaction) {
        Basket requester = transaction.getDetail_from();
        List<Carrot> pendingCarrots = carrotRepository.findByBasketId(new ObjectId(requester.getId()))
                .stream().filter(c -> !c.isUsable()).collect(Collectors.toList());

        int count = transaction.getCarrot_amt();
        requester.setCarrot_amt(requester.getCarrot_amt()+count);
        basketRepository.save(requester);
        for (int i = 0; i<count;i++) {
            Carrot c = pendingCarrots.get(i);
            c.setUsable(true);
            carrotRepository.save(c);
        }
    }

    public TransactionResponse findTransactionByEmployee (String id) {
        TransactionResponse res = new TransactionResponse();
        List<Transaction> result = transactionRepository.findAllTransactionByAnEmployee(new ObjectId(id));
/*        List<Transaction> temp1 = transactionRepository.findFreezerFromByEmployeeId(new ObjectId(id));
        List<Transaction> temp2 = transactionRepository.
        List<Transaction> result = transactionRepository.findDetailToByEmployeeId(new ObjectId(id));

        result.addAll(temp);
        result.addAll(temp1);*/
        res.setStatus(true);
        if (result.size() > 0) {
            res.setMessage("Transaction found");
        } else {
            res.setMessage("Transaction not found");
        }
        res.setListTransaction(result);
        return res;
    }

    public TransactionResponse findTransactionByBazaarId (String id) {
        TransactionResponse res = new TransactionResponse();
        List<Transaction> transactionList = transactionRepository.findTransactionByBazaarId(new ObjectId(id));
        res.setStatus(true);
        if (transactionList.size() > 0) {
            res.setMessage("Transaction found");
        } else {
            res.setMessage("Transaction not found");
        }
        res.setListTransaction(transactionList);
        return res;
    }

    public TransactionResponse findAllPendingTransaction () {
        TransactionResponse res = new TransactionResponse();
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("PENDING"));
        List <Transaction> pendingTransactions = mongoTemplate.find(query, Transaction.class);
        res.setStatus(true);
        if (pendingTransactions.size() > 0) {
            res.setMessage("Transaction found");
        } else {
            res.setMessage("Transaction not found");
        }
        res.setListTransaction(pendingTransactions);
        return res;
    }

    public TransactionResponse findTransactionByType (String status) {
        TransactionResponse res = new TransactionResponse();
        List <Transaction> transactions = transactionRepository.findTransactionByType(status);
        res.setStatus(true);
        if (transactions.size() > 0) {
            res.setMessage("Transaction found");
        } else {
            res.setMessage("Transaction not found");
        }
        res.setListTransaction(transactions);
        return res;
    }

    public TransactionResponse findTransactionByDate(LocalDateTime startDate, LocalDateTime endDate) {
        TransactionResponse res = new TransactionResponse();
        List<Transaction> transactions = transactionRepository.findTransactionByDate(startDate, endDate);
        res.setStatus(true);
        if (transactions.size() > 0) {
            res.setMessage("Transaction found");
        } else {
            res.setMessage("Transaction not found");
        }
        res.setListTransaction(transactions);
        return res;
    }

    public TransactionResponse findTransactionByTypeAndDate(String[] status, LocalDateTime startDate,
                                                          LocalDateTime endDate) {
        TransactionResponse res = new TransactionResponse();
        List<Transaction> transactions = transactionRepository.findTransactionbByTypeAndDate(status,
                startDate, endDate);
        res.setStatus(true);
        if (transactions.size() > 0) {
            res.setMessage("Transaction found");
        } else {
            res.setMessage("Transaction not found");
        }
        res.setListTransaction(transactions);
        return res;
    }

    public List<Hasil> sortByMostEarn() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project()
                        .andExpression("detail_to.id").as("foo")
                        .andExpression("carrot_amt").as("carrot_amt")
                        .andExpression("detail_to").as("kk"),
                Aggregation.group("foo").sum("carrot_amt").as("total")
                        .last("kk").as("kk"),
                Aggregation.project()
                        .andExpression("total").as("total")
                        .andExpression("foo").as("id")
                        .andExpression("kk").as("detail"));
        AggregationResults<Hasil> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Hasil.class);
        List<Hasil> temp = groupResults.getMappedResults();
        List<Hasil> result = temp.subList(0,temp.size()-1);
        result.forEach( e -> System.out.println(e.getDetail().getName()));
        return result.stream()
                .map(e -> {
                    e.getDetail().getId();
                    int donation = this.countCarrotSpentForDonation(e.getDetail().getEmployee().getId());
                    e.setDonation(donation);
                    int reward = this.countCarrotSpentForRewardItem(e.getDetail().getEmployee().getId());
                    e.setReward(reward);
                    int sharing = this.countCarrotSpentForSharing(e.getDetail().getEmployee().getId());
                    e.setShared(sharing);
                    int earnThisMonth = this.countCarrotEarnedThisMonth(e.getDetail().getEmployee().getId());
                    e.setCarrotthisMonth(earnThisMonth);
                    Basket basket = basketRepository.findBasketByEmployeeId(new ObjectId(e.getDetail().getEmployee().getId()));
                    e.setDetail(basket);
                    return e;
                })
                .sorted(Comparator.comparingLong(Hasil::getTotal).reversed())
                .collect(Collectors.toList());
    }

    public List<Hasil> getTotalEarnedAmt(String id) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project()
                        .andExpression("detail_to.id").as("foo")
                        .andExpression("carrot_amt").as("carrot_amt")
                        .andExpression("detail_to").as("kk")
                        .andExpression("status").as("status")
                        .andExpression("detail_to.employee.id").as("employeeid"),
                Aggregation.match(Criteria.where("foo").is(new ObjectId(id))),
                Aggregation.match(Criteria.where("status").is("APPROVED")),
                Aggregation.group("foo").sum("carrot_amt").as("total")
                        .last("kk").as("kk"),
                Aggregation.project()
                        .andExpression("total").as("total")
                        .andExpression("foo").as("id")
                        .andExpression("kk").as("detail"));
        AggregationResults<Hasil> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Hasil.class);
        List<Hasil> hasil =  groupResults.getMappedResults().stream().map(e -> {
            e.getDetail().getId();
            Basket basket = basketRepository.findBasketByEmployeeId(new ObjectId(e.getDetail().getEmployee().getId()));
            e.setDetail(basket);
            System.out.println(basket.getCarrot_amt());
            return e;
        }).collect(Collectors.toList());
        return hasil;
    }
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
    private void sendNotifToAdmin(Notification notification, String role) {
        List<Employee> list = employeeServiceUsingDB.getStaffRole(role).getListEmployee();
        ListIterator<Employee> obj = list.listIterator();
        final int[] i = {0};
        while (obj.hasNext()) {
            try {
                Thread.sleep(4000);
                Employee emp = obj.next();
                emp.setName(emp.getName() + i[0]);
                notification.setId(ObjectId.get().toString());
                System.out.println(emp.getName());
                System.out.println(emp.getId());
                notification.setOwner(emp);
                template.convertAndSend("/topic/reply", notification);
                notificationService.createNotif(notification);
                i[0]++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotifToEmployee(Notification notification, Employee employee) {
        notification.setId(ObjectId.get().toString());
        System.out.println(employee.getName());
        System.out.println(employee.getId());
        notification.setOwner(employee);
        template.convertAndSend("/topic/reply", notification);
        notificationService.createNotif(notification);
    }

    public int countCarrotSpentForRewardItem (String id) {
        int total_spent = transactionRepository.findDetailFromByEmployeeId(new ObjectId(id)).stream()
                .filter(e -> e.getType() == Transaction.Type.BAZAAR)
                .mapToInt( a->a.getCarrot_amt()).sum();

        return total_spent;
    }

    public int countCarrotSpentForSharing (String id) {
        int total_spent = transactionRepository.findDetailFromByEmployeeId(new ObjectId(id)).stream()
                .filter(e -> e.getType() == Transaction.Type.SHARED)
                .mapToInt( a->a.getCarrot_amt()).sum();

        return total_spent;
    }

    public int countCarrotSpentForDonation (String id) {
        List<Transaction> donation_transaction = transactionRepository.findDetailFromByEmployeeId(new ObjectId(id));

        int total_spent = donation_transaction.stream()
                .filter(e -> e.getType() == Transaction.Type.DONATION)
                .mapToInt( a->a.getCarrot_amt()).sum();

        return total_spent;
    }
    public int countCarrotEarnedThisMonth(String id) {
        int year = Year.now().getValue();
        int month = LocalDate.now().getMonthValue();
        int day = 0;
        if (month == 9||month==4||month==6||month==11){ day = 30; }
        else if( month==2){ day = 28; }
        else { day=31; }
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0,0,0);
        LocalDateTime end = LocalDateTime.of(year, month, day, 0,0,0);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is("REWARD")
                        .andOperator(Criteria.where("transaction_date").gte(start)
                                .andOperator(Criteria.where("transaction_date").lte(end)))),
                Aggregation.match(Criteria.where("detail_to.employee.$id").is(new ObjectId(id))));

        AggregationResults<Transaction> rewardTransaction = this.mongoTemplate.aggregate(aggregation,
                Transaction.class, Transaction.class);
        List<Transaction> rewardTransactions = rewardTransaction.getMappedResults();
        return rewardTransactions.stream().mapToInt(Transaction::getCarrot_amt).sum();
    }

    public List<AchievementEachMonth> findAchievedAchievementInCurrentMonth (){
        int year = Year.now().getValue();
        List<AchievementEachMonth> result = new ArrayList<>();
        for (int i = 1; i < 13; i++){
            System.out.println("NEW ITERATION");
            LocalDateTime start = LocalDateTime.of(year, i, 1, 0,0,0 );
            System.out.println(start);
            int dayOfMonth = 0;
            if (i == 1 || i==3 || i ==5 || i==7 || i ==8 || i==10 ||i==12){
                dayOfMonth = 31;
            }
            else if (i == 2){
                dayOfMonth = 28;
            }
            else {
                dayOfMonth = 30;
            }
            LocalDateTime end = LocalDateTime.of(year, i, dayOfMonth, 23,59, 59);
            System.out.println(end);
            List<Transaction> currentMonthTransactions = transactionRepository.findTransactionByDate(start,end);
            System.out.println(currentMonthTransactions.size());
            if (!currentMonthTransactions.isEmpty()){
                Set<Achievement> achieved = new HashSet<>();
                currentMonthTransactions.forEach(t -> {
                    if (!achieved.contains(t.getAchievementClaimed()) && t.getAchievementClaimed() != null) {
                        achieved.add(t.getAchievementClaimed());
                    }
                });
                AchievementEachMonth currentMonth = new AchievementEachMonth();
                currentMonth.setAchievements(achieved);
                currentMonth.setYear(Year.now().toString());
                currentMonth.setMonth(Month.of(i).toString());
                result.add(currentMonth);
            }
        }

        return result;
    }

    public void sendBirthdayCarrot(int count, Employee emp){
        Award award = awardRepository.findById("5c943ae5b73f4133b45a1da8").get();
        if( award.isActive()) {
            Basket basket = basketRepository.findBasketByEmployeeId(new ObjectId(emp.getId()));

            System.out.println("running sendbirthday function");
            Transaction transaction = new Transaction();
            transaction.setAward(award);
            transaction.setDetail_to(basket);
            transaction.setType(Transaction.Type.REWARD);
            transaction.setCarrot_amt(award.getCarrot_amt());

            System.out.println("count =  " + count);
            for(int i = 0; i < count; i++){
                System.out.println("Iteration =  " + i);
                createTransaction(transaction);
                System.out.println("finished createing transaction iter = " + i);
            }
        }
    }
    @Scheduled(cron = "0 54 10 * * *")
    public void scheduledBirthdayCarrot() {
        System.out.println("Scheduler triggered");

        List<Employee> employeeHavingBirthday = employeeServiceUsingDB.findAllEmployeeHavingBirthdayToday();

        for (Employee emp: employeeHavingBirthday
             ) {
            int count = employeeServiceUsingDB.checkBirthdayCarrotEligibility(emp.getId());
            if (count > 0 ){
                System.out.println("count =  " + count);
                sendBirthdayCarrot(count, emp);
                System.out.println("finished sending birthday carrot");
            }
        }
    }

    public List<Hasil> findAllEmployeeSortedByCarrotSpentForDonation() {
        int year = Year.now().getValue();
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0,0,0);
        LocalDateTime end = LocalDateTime.now();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is("DONATION")
                        .andOperator(Criteria.where("transaction_date").gte(start)
                        .andOperator(Criteria.where("transaction_date").lte(end)))),
                Aggregation.project()
                        .andExpression("detail_from.id").as("foo")
                        .andExpression("carrot_amt").as("carrot_amt")
                        .andExpression("detail_from").as("kk"),
                Aggregation.group("foo").sum("carrot_amt").as("total")
                        .last("kk").as("kk"),
                Aggregation.project()
                        .andExpression("total").as("total")
                        .andExpression("foo").as("id")
                        .andExpression("kk").as("detail"));
        AggregationResults<Hasil> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Hasil.class);
        List<Hasil> temp = groupResults.getMappedResults();
/*        List<Hasil> result = temp.subList(0,temp.size()-1);
        result.forEach( e -> System.out.println(e.getDetail().getName()));
        return result.stream().sorted(Comparator.comparingLong(Hasil::getTotal).reversed()).collect(Collectors.toList());*/
        return temp;
/*        return this.mongoTemplate.aggregate(aggregation, Transaction.class, Transaction.class).getMappedResults();*/
    }

    public List<Hasil> findAllEmployeeSortedBySpentCarrotForRewards () {
        int year = Year.now().getValue();
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0,0,0);
        LocalDateTime end = LocalDateTime.now();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is("BAZAAR")
                        .andOperator(Criteria.where("transaction_date").gte(start)
                                .andOperator(Criteria.where("transaction_date").lte(end)))),
                Aggregation.project()
                        .andExpression("detail_from.id").as("foo")
                        .andExpression("carrot_amt").as("carrot_amt")
                        .andExpression("detail_from").as("kk"),
                Aggregation.group("foo").sum("carrot_amt").as("total")
                        .last("kk").as("kk"),
                Aggregation.project()
                        .andExpression("total").as("total")
                        .andExpression("foo").as("id")
                        .andExpression("kk").as("detail"));
        AggregationResults<Hasil> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Hasil.class);
        List<Hasil> temp = groupResults.getMappedResults();
        return temp;
        /*        return this.mongoTemplate.aggregate(aggregation, Transaction.class, Transaction.class).getMappedResults();*/

    }
    public List<Hasil> findAllEmployeeSortedBySpentCarrotForSharing () {
        int year = Year.now().getValue();
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.now();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is("SHARED")
                        .andOperator(Criteria.where("transaction_date").gte(start)
                                .andOperator(Criteria.where("transaction_date").lte(end)))),
                Aggregation.project()
                        .andExpression("detail_from.id").as("foo")
                        .andExpression("carrot_amt").as("carrot_amt")
                        .andExpression("detail_from").as("kk"),
                Aggregation.group("foo").sum("carrot_amt").as("total")
                        .last("kk").as("kk"),
                Aggregation.project()
                        .andExpression("total").as("total")
                        .andExpression("foo").as("id")
                        .andExpression("kk").as("detail"));
        AggregationResults<Hasil> groupResults = this.mongoTemplate.aggregate(aggregation, Transaction.class, Hasil.class);
        return groupResults.getMappedResults();
    }
}
