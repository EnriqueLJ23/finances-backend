package com.jasso.finance.finance.rest;

import com.jasso.finance.finance.entity.Transaction;
import com.jasso.finance.finance.service.TransactionService;
import com.jasso.finance.finance.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionRestController {
    //create field for Transaction service
    private TransactionService transactionService;

    //inject the service using the constructor
    @Autowired
    public TransactionRestController(TransactionService theTransactionService) {
        transactionService = theTransactionService;
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        return transactionService.findByUserId(authenticatedUserId);
    }

    @GetMapping("/transactions/{transactionId}")
    public Transaction getTransaction(@PathVariable int transactionId) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        Transaction theTransaction = transactionService.findById(transactionId);
        if (theTransaction == null) {
            throw new RuntimeException("Transaction id not found - " + transactionId);
        }
        
        if (!theTransaction.getUser_id().equals(authenticatedUserId)) {
            throw new SecurityException("Unauthorized access to transaction");
        }
        
        return theTransaction;
    }

    @PostMapping("/transactions")
    public Transaction addTransaction(@RequestBody Transaction theTransaction) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        // Force ID to 0 in case it's passed, this will ensure creation of new transaction
        theTransaction.setId(0);
        theTransaction.setUser_id(authenticatedUserId);
        transactionService.save(theTransaction);
        return theTransaction;
    }

    @PutMapping("/transactions/{transactionId}")
    public Transaction updateTransaction(@PathVariable int transactionId, @RequestBody Transaction theTransaction) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        Transaction existingTransaction = transactionService.findById(transactionId);
        if (existingTransaction == null) {
            throw new RuntimeException("Transaction id not found - " + transactionId);
        }
        
        if (!existingTransaction.getUser_id().equals(authenticatedUserId)) {
            throw new SecurityException("Unauthorized access to transaction");
        }
        
        // Set the ID from path variable to ensure we're updating the correct transaction
        theTransaction.setId(transactionId);
        theTransaction.setUser_id(authenticatedUserId);
        transactionService.save(theTransaction);
        return theTransaction;
    }

    @DeleteMapping("/transactions/{transactionId}")
    public Map<String, String> deleteTransaction(@PathVariable int transactionId) {
        Long authenticatedUserId = SecurityUtil.getAuthenticatedUserId();
        
        Transaction tempTransaction = transactionService.findById(transactionId);
        if (tempTransaction == null) {
            throw new RuntimeException("Transaction id not found - " + transactionId);
        }
        
        if (!tempTransaction.getUser_id().equals(authenticatedUserId)) {
            throw new SecurityException("Unauthorized access to transaction");
        }
        
        transactionService.remove(transactionId);
        return Map.of("message", "Transaction deleted successfully");
    }
}
