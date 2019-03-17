package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.repository.BasketRepository;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
import com.mitrais.jpqi.springcarrot.repository.FreezerRepository;
import com.mitrais.jpqi.springcarrot.repository.TransactionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private CarrotRepository carrotRepository;
    @Autowired
    private FreezerRepository freezerRepository;


    public List<Transaction> findAllTransactions() {return transactionRepository.findAll();}

    public void createTransaction(Transaction transaction) {

        if (transaction.getType() == Transaction.Type.REWARD) {
            //Codes if the transaction is a reward (from manager freezer to employee's basket)
            if (transaction.getFreezer_from() != null) {
                //Change the carrot amount in manager's freezer
                Optional<Freezer> f = freezerRepository.findById(transaction.getFreezer_from().getId());
                Freezer freezer= f.get();
                freezer.setCarrot_amt((freezer.getCarrot_amt() - transaction.getCarrot_amt()));

                //set the created/updated
                if (transaction.getTransaction_date() == null) { transaction.setTransaction_date(LocalDateTime.now()); }

                freezerRepository.save(freezer);


                Freezer f_from = transaction.getFreezer_from();
                Basket b_to = transaction.getDetail_to();

                //Edit the recipient carrot amount in basket
                b_to.setCarrot_amt((b_to.getCarrot_amt() + transaction.getCarrot_amt()));
                basketRepository.save(b_to);

                //update the carrots in manager's freezer into employee's basket
                //TODO fix the bug because the carrot cant set the basket id
                List<Carrot> carrots = carrotRepository.findByFreezerId(new ObjectId(f_from.getId()));
                int count = transaction.getCarrot_amt();
                for (int i = 0 ; i <count ; i++) {
                    Carrot c = carrots.get(i);
                    c.setBasket( new Basket());
                    c.getBasket().setId(b_to.getId());
                    c.getFreezer().setId(null);
                    c.setType(Carrot.Type.NORMAL);
                    carrotRepository.save(c);
                }
            }
            else {
                //code if the reward is from system (not from manager's freezer )
            }
        }

        if (transaction.getType() == Transaction.Type.SHARED) {
            //Edit the sender carrot amount in basket
            Basket b_from = transaction.getDetail_from();
            b_from.setCarrot_amt((b_from.getCarrot_amt() - transaction.getCarrot_amt()));
            basketRepository.save(b_from);

            //Edit the recipient carrot amount in basket
            Basket b_to = transaction.getDetail_to();
            b_to.setCarrot_amt((b_to.getCarrot_amt() + transaction.getCarrot_amt()));
            basketRepository.save(b_to);

            //update the carrots in sender's basket into recipient's basket
            List<Carrot> carrots = carrotRepository.findByBasketId(new ObjectId(b_from.getId()));
            int count = transaction.getCarrot_amt();
            for (int i = 0 ; i <count ; i++) {
                Carrot c = carrots.get(i);
                c.getBasket().setId(b_to.getId());
                carrotRepository.save(c);
            }
        }
        transactionRepository.save(transaction);
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
}
