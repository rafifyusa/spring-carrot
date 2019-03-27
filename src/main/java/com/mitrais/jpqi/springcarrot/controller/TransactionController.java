package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Hasil;
import com.mitrais.jpqi.springcarrot.model.Transaction;
import com.mitrais.jpqi.springcarrot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping
    List<Transaction> findAllTransactions() {
        return transactionService.findAllTransactions();
    }

    @GetMapping("pending")
    List<Transaction> findAllPendingTransactions() {
        return transactionService.findAllPendingTransaction();
    }

    @GetMapping("{id}")
    List<Transaction> findTransactionsByEmployee(@PathVariable String id) {
        return transactionService.findTransactionByEmployee(id);
    }

    @PostMapping
    public void createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
    }

    @PatchMapping("approve")
    public void approveTransaction(@RequestParam String id) {
        transactionService.approveTransaction(id);
    }

    @PatchMapping("decline")
    public void declineTransaction(@RequestParam String id) {
        transactionService.declineTransaction(id);
    }

    @GetMapping("{id}/spent_for_reward")
    public int getTotalSpentForReward(@PathVariable String id) {
        return transactionService.countCarrotSpentForRewardItem(id);
    }

    @GetMapping("{id}/spent_for_sharing")
    public int getTotalSpentForSharing(@PathVariable String id) {
        return transactionService.countCarrotSpentForSharing(id);
    }

    @GetMapping("by-bazaar/{id}")
    public List<Transaction> getAllTransactionByBazaarId(@PathVariable String id) {
        return transactionService.findTransactionByBazaarId(id);
    }

    /*@GetMapping("mostearned")
    public List<CarrotCount> getEmployeeByCarrotEarned (){
        return transactionService.findAllEmployeeSortedByCarrotEarned();
    }*/

    @GetMapping("mostearned")
    public  List<Hasil> getEmployeeByCarrotEarned() {
        return transactionService.sortByMostEarn();
    }

}
