package com.jasso.finance.finance.rest;

import com.jasso.finance.finance.entity.Transaction;
import com.jasso.finance.finance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return transactionService.findAll();
    }
}
