package com.mitrais.jpqi.springcarrot.service;

import com.mitrais.jpqi.springcarrot.model.Transaction;
import com.mitrais.jpqi.springcarrot.repository.TransactionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> findAllTransactions() {return transactionRepository.findAll();}

    public void createTransaction(Transaction transaction) {transactionRepository.save(transaction);}

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
