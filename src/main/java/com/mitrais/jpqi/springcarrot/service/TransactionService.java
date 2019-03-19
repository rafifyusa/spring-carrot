package com.mitrais.jpqi.springcarrot.service;

import com.google.gson.Gson;
import com.mitrais.jpqi.springcarrot.model.*;
import com.mitrais.jpqi.springcarrot.repository.BasketRepository;
import com.mitrais.jpqi.springcarrot.repository.CarrotRepository;
import com.mitrais.jpqi.springcarrot.repository.FreezerRepository;
import com.mitrais.jpqi.springcarrot.repository.TransactionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
            //System.out.println("new from: " + newAmount);
            basketRepository.save(basket);

            //Edit the recipient carrot amount in basket
            Optional<Basket> b1 = basketRepository.findById(b_to.getId());
            Basket basket1 = b1.get();
            Double newAmount1 = basket1.getCarrot_amt() + transaction.getCarrot_amt();
            basket1.setCarrot_amt(newAmount1);
            //System.out.println("new to: " + newAmount1);
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

        if (transaction.getType() == Transaction.Type.BAZAAR) {
            //TODO Kurangi jumlah carrot di basket
            //TODO UBAH carrot jadi inactive
            //TODO Ganti jumlah Item
        }

        //set the transaction date
        if (transaction.getTransaction_date() == null) { transaction.setTransaction_date(LocalDateTime.now()); }

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
