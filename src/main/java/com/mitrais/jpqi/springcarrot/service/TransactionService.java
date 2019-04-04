package com.mitrais.jpqi.springcarrot.service;

import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.repository.*;
import com.mitrais.jpqi.springcarrot.responses.TransactionResponse;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    MongoTemplate mongoTemplate;

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
        TransactionResponse res = new TransactionResponse();
        if (transaction.getType() == Transaction.Type.REWARD) {
            System.out.println(new Gson().toJson(transaction.getAchievementClaimed()));
            System.out.println(new Gson().toJson(transaction));
            //Codes if the transaction is a reward (from manager freezer to employee's basket)
            if (transaction.getFreezer_from() != null) {
                Freezer f_from = transaction.getFreezer_from();
                Basket b_to = transaction.getDetail_to();

                transaction.setFrom(f_from.getName());
                transaction.setTo(b_to.getName());
            }
            else {
                //code if the reward is from system (not from manager's freezer )
            }
        }

        if (transaction.getType() == Transaction.Type.SHARED) {

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
        }

        if (transaction.getType() == Transaction.Type.BAZAAR
                || transaction.getType() == Transaction.Type.DONATION) {
            Basket b_from = transaction.getDetail_from();
            Optional<Basket> b = basketRepository.findById(b_from.getId());
            Basket basket = b.get();
            if (basket.getCarrot_amt() >= transaction.getCarrot_amt()) {
                Double newAmount = basket.getCarrot_amt() - transaction.getCarrot_amt();
                basket.setCarrot_amt(newAmount);
                basketRepository.save(basket);

                //update the carrots in sender's basket into recipient's basket
                List<Carrot> carrots = carrotRepository.findByBasketId(new ObjectId(b_from.getId()));
                int count = transaction.getCarrot_amt();
                for (int i = 0 ; i < count ; i++) {
                    Carrot c = carrots.get(i);
                    c.setUsable(false);
                    carrotRepository.save(c);
                }

                if (transaction.getType() == Transaction.Type.BAZAAR) {
                    Item requested_Item = transaction.getRequested_item();
                    Optional<Item> i = itemRepository.findById(requested_Item.getId());
                    Item item = i.get();
                    item.setItemSold((item.getItemSold() +1));
                    item.setTotalItem((item.getTotalItem() -1));
                    itemRepository.save(item);

                    transaction.setFrom(b_from.getName());
                    transaction.setTo(item.getBazaar().getBazaarName());
                }
                else {
                    SocialFoundation socialFoundation = transaction.getSocialFoundation();
                    if (socialFoundationRepository.findById(socialFoundation.getId()).isPresent()) {
                        SocialFoundation sf = socialFoundationRepository.findById(socialFoundation.getId()).get();

                        double newFoundationAmount = sf.getTotal_carrot() + transaction.getCarrot_amt();
                        sf.setTotal_carrot(newFoundationAmount);
                        socialFoundationRepository.save(sf);

                        transaction.setFrom(b_from.getName());
                        transaction.setTo(socialFoundation.getName());
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

        if(transaction.getType() == Transaction.Type.FUNNEL){
            System.out.println(transaction.toString());
            funnelTransaction(transaction);
        }
        //set the transaction date
        if (transaction.getTransaction_date() == null) { transaction.setTransaction_date(LocalDateTime.now()); }
        transaction.setStatus(Transaction.Status.PENDING);

        try {
            transactionRepository.save(transaction);
            res.setStatus(true);
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
            carrots.forEach(carrot -> {
                carrot.setFreezer(f_to);
                carrot.setUpdated_at(LocalDateTime.now());
                carrotRepository.save(carrot);
            });
        }
        //Funnel for from Barn to SM
        else if (transaction.getFreezer_from() == null) {
            Freezer f_to = freezerRepository.findByOwner(new ObjectId(transaction.getFreezer_to().getId()));
            Barn barn = barnService.findBarnById(transaction.getBarn().getId()).getBarn();

            //Update SM freezer amount
            double newCarrotAmount = f_to.getCarrot_amt() + transaction.getCarrot_amt();
            f_to.setCarrot_amt(newCarrotAmount);
            freezerRepository.save(f_to);

            //Update Barn carrot left
            long newCarrotLeft = barn.getCarrotLeft() - transaction.getCarrot_amt();
            barn.setCarrotLeft(newCarrotLeft);
            barnService.createBarn(barn);

            //Update Carrot ownership
            List<Carrot> carrots = carrotRepository.findCarrotByBarnIdAndType(new ObjectId(barn.getId()), "FRESH");
            carrots.forEach(carrot -> {
                carrot.setFreezer(f_to);
                carrot.setUpdated_at(LocalDateTime.now());
                carrot.setType(Carrot.Type.FROZEN);
                carrotRepository.save(carrot);
            });
        }
    }

    public TransactionResponse approveTransaction(String id) {
        TransactionResponse res = new TransactionResponse();

        if (transactionRepository.findById(id).isPresent()) {
            Transaction transaction = transactionRepository.findById(id).get();
            if (transaction.getType() == Transaction.Type.BAZAAR) {
                makeApprovedTransaction(transaction);
            }
            if(transaction.getType() == Transaction.Type.DONATION){
                if (transaction.getSocialFoundation().getTotal_carrot()
                        < transaction.getSocialFoundation().getMin_carrot()){
                    // gagal
                    res.setStatus(false);
                    res.setMessage("total carrot less than minimum carrot exchange");
                    return res;
                }
                else{
                    makeApprovedTransaction(transaction);
                }
            }

            if (transaction.getType() == Transaction.Type.REWARD) {
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

    //used by another method
    public void makeApprovedTransaction (Transaction transaction) {
        Basket requester = transaction.getDetail_from();
        List<Carrot> pendingCarrots = carrotRepository.findByBasketId(new ObjectId(requester.getId()))
                .stream().filter(c -> !c.isUsable()).collect(Collectors.toList());

        int count = transaction.getCarrot_amt();
        requester.setCarrot_amt(requester.getCarrot_amt()-count);
        basketRepository.save(requester);
        for (int i = 0; i<count;i++) {
            Carrot c = pendingCarrots.get(i);
            c.setType(Carrot.Type.INACTIVE);
            c.setBasket(null);
            carrotRepository.save(c);
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
        List<Transaction> temp = transactionRepository.findDetailFromByEmployeeId(new ObjectId(id));
        List<Transaction> temp1 = transactionRepository.findFreezerFromByEmployeeId(new ObjectId(id));
        List<Transaction> result = transactionRepository.findDetailToByEmployeeId(new ObjectId(id));

        result.addAll(temp);
        result.addAll(temp1);
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
        return groupResults.getMappedResults();
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
        return groupResults.getMappedResults();
    }
/*    //TODO sortbyspentcarrots
    public List<Employee> findAllEmployeeSortedBySpentCarrotForRewards () {

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is(Transaction.Type.BAZAAR)),
                Aggregation.unwind("basket")
        )
        return null;
    }*/


    /*public List<CarrotCount> findAllEmployeeSortedByCarrotEarned (){
        MatchOperation getMatchOperation()

        Aggregation agg = newAggregation(
                group("detail_to").sum("carrot_amt").as("carrotEarned"),
                sort(ASC, previousOperation(),"carrotEarned")
        );
        AggregationResults<CarrotCount> results = mongoTemplate.aggregate(agg, Transaction.class, CarrotCount.class);
        List<CarrotCount> carrotCount = results.getMappedResults();

        return carrotCount;
    }*/
}
