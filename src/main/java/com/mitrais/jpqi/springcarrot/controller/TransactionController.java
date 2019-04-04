package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.Hasil;
import com.mitrais.jpqi.springcarrot.model.Transaction;
import com.mitrais.jpqi.springcarrot.responses.TransactionResponse;
import com.mitrais.jpqi.springcarrot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

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
    public TransactionResponse createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
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

    @GetMapping("by-status")
    public List<Transaction> getAllTransactionByStatus(@RequestParam String type) {
        return transactionService.findTransactionByType(type);
    }

    @GetMapping("by-date-status")
    public List<Transaction> getAllTransactionByStatusAndDate(@RequestParam String type,
                                                              @RequestParam Long startDate,
                                                              @RequestParam Long endDate) {

        String[] types = new String[4];
        switch (type) {
            case "ALL":
                types[0] = "SHARED";
                types[1] = "DONATION";
                types[2] = "BAZAAR";
                types[3] = "REWARD";
                break;
            default:
                types[0] = type;
                break;
        }
        //convert the timestamp to dates
        LocalDateTime startDateC =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(startDate),
                        TimeZone.getDefault().toZoneId());

        LocalDateTime endDateC =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(endDate),
                        TimeZone.getDefault().toZoneId());
        System.out.println(startDateC);
        System.out.println(endDateC);
        return transactionService.findTransactionByTypeAndDate(types, startDateC, endDateC);
    }

    /*@GetMapping("mostearned")
    public List<CarrotCount> getEmployeeByCarrotEarned (){
        return transactionService.findAllEmployeeSortedByCarrotEarned();
    }*/

    @GetMapping("mostearned")
    public  List<Hasil> getEmployeeByCarrotEarned() {
        return transactionService.sortByMostEarn();
    }

    @GetMapping("total-earned/{id}")
    public  List<Hasil> getEmployeeTotalEarnedCarrot(@PathVariable String id) {
        return transactionService.getTotalEarnedAmt(id);
    }

}
