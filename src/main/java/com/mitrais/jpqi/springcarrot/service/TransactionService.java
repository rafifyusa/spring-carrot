package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.repository.*;
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
    MongoTemplate mongoTemplate;


    public List<Transaction> findAllTransactions() {return transactionRepository.findAll();}

    public void createTransaction(Transaction transaction) {

        if (transaction.getType() == Transaction.Type.REWARD) {
            //Codes if the transaction is a reward (from manager freezer to employee's basket)
            if (transaction.getFreezer_from() != null) {
                Freezer f_from = transaction.getFreezer_from();
                Basket b_to = transaction.getDetail_to();

                //Change the carrot amount in manager's freezer
                Optional<Freezer> f = freezerRepository.findById(f_from.getId());
                Freezer freezer= f.get();
                freezer.setCarrot_amt((freezer.getCarrot_amt() - transaction.getCarrot_amt()));
                freezerRepository.save(freezer);

                //Edit the recipient carrot amount in basket
                Optional<Basket> b = basketRepository.findByEmployee(new ObjectId(b_to.getId()));
                Basket basket = b.get();
                basket.setCarrot_amt((basket.getCarrot_amt() + transaction.getCarrot_amt()));
                basketRepository.save(basket);

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
            }

        }

        //set the transaction date
        if (transaction.getTransaction_date() == null) { transaction.setTransaction_date(LocalDateTime.now()); }
        transaction.setStatus(Transaction.Status.PENDING);
        transactionRepository.save(transaction);
    }

    public void approveTransaction(String id) {
        if (transactionRepository.findById(id).isPresent()) {
            Transaction transaction = transactionRepository.findById(id).get();
            if (transaction.getType() == Transaction.Type.BAZAAR
                    ||(transaction.getSocialFoundation().getTotal_carrot()
                    >= transaction.getSocialFoundation().getMin_carrot())) {
                Basket requester = transaction.getDetail_from();
                List<Carrot> pendingCarrots = carrotRepository.findByBasketId(new ObjectId(requester.getId()))
                        .stream().filter(c -> !c.isUsable()).collect(Collectors.toList());

                int count = transaction.getCarrot_amt();
                for (int i = 0; i<count;i++) {
                    Carrot c = pendingCarrots.get(i);
                    c.setType(Carrot.Type.INACTIVE);
                    c.setBasket(null);
                    carrotRepository.save(c);
                }
            }
            transaction.setStatus(Transaction.Status.APPROVED);
            transactionRepository.save(transaction);
        }
    }

    public void declineTransaction(String id) {
        if (transactionRepository.findById(id).isPresent()) {
            Transaction transaction = transactionRepository.findById(id).get();
            if (transaction.getType() == Transaction.Type.BAZAAR
                    ||(transaction.getSocialFoundation().getTotal_carrot()
                    < transaction.getSocialFoundation().getMin_carrot())) {
                Basket requester = transaction.getDetail_from();
                List<Carrot> pendingCarrots = carrotRepository.findByBasketId(new ObjectId(requester.getId()))
                        .stream().filter(c -> !c.isUsable()).collect(Collectors.toList());

                int count = transaction.getCarrot_amt();
                for (int i = 0; i<count;i++) {
                    Carrot c = pendingCarrots.get(i);
                    c.setUsable(true);
                    carrotRepository.save(c);
                }
            }
            transaction.setStatus(Transaction.Status.DECLINED);
            transactionRepository.save(transaction);
        }
    }

    public void updateTransaction(String id, Transaction transaction) {
        transaction.setId(id);
        transactionRepository.save(transaction);
    }

    public List<Transaction> findTransactionByEmployee (String id) {
        List<Transaction> temp = transactionRepository.findDetailFromByEmployeeId(new ObjectId(id));
        List<Transaction> result = transactionRepository.findDetailToByEmployeeId(new ObjectId(id));

        temp.forEach( t -> result.add(t));
        return result;
    }

    public int countCarrotSpentForRewardItem (String id) {
        List<Transaction> reward_transaction = transactionRepository.findDetailFromByEmployeeId(new ObjectId(id));

        int total_spent = reward_transaction.stream()
                .filter(e -> e.getType() == Transaction.Type.BAZAAR)
                .mapToInt( a->a.getCarrot_amt()).sum();

        return total_spent;
    }

    public int countCarrotSpentForSharing (String id) {
        List<Transaction> reward_transaction = transactionRepository.findDetailFromByEmployeeId(new ObjectId(id));

        int total_spent = reward_transaction.stream()
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

    public List<Transaction> findAllPendingTransaction () {
        Query query = new Query();
        query.addCriteria(Criteria.where("status").is("PENDING"));
        List <Transaction> pendingTransactions = mongoTemplate.find(query, Transaction.class);
        return  pendingTransactions;
    }
/*    //TODO sortbyspentcarrots
    public List<Employee> findAllEmployeeSortedBySpentCarrotForRewards () {

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is(Transaction.Type.BAZAAR)),
                Aggregation.unwind("basket")
        )
        return null;
    }*/


 /*   public List<CarrotCount> findAllEmployeeSortedByCarrotEarned (){
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
