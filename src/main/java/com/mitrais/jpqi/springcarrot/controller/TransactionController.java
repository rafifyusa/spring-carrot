package com.mitrais.jpqi.springcarrot.controller;

import com.mitrais.jpqi.springcarrot.model.CarrotCount;
import com.mitrais.jpqi.springcarrot.model.HostingCount;
import com.mitrais.jpqi.springcarrot.model.Transaction;
import com.mitrais.jpqi.springcarrot.service.TransactionService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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

    @GetMapping("sort-by-most-earn")
    public List<HostingCount> sortByMostEarn() {
        Aggregation agg = newAggregation(
                group("detail_from.$id").sum("carrot_amt").as("total")
                        .push("detail_from.id").as("name")
        );

        AggregationResults<HostingCount> groupResults
                = mongoTemplate.aggregate(agg, Transaction.class, HostingCount.class);
        List<HostingCount> result = groupResults.getMappedResults();
        return result;
//        return transactionService.sortByMostEarn();
//        return null;
    }

}
