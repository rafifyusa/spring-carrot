package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Transaction;
import com.mitrais.jpqi.springcarrot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping
    List<Transaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @GetMapping("{id}")
    List<Transaction> findTransactionsByEmployee(@PathVariable String id) {
        return transactionService.findTransactionByEmployee(id);
    }

    @PostMapping
    public void createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
    }


}
