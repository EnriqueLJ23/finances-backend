package com.jasso.finance.finance.service;

import com.jasso.finance.finance.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();
    
    List<Transaction> findByUserId(Integer userId);

    Transaction findById(Integer id);

    void save(Transaction theTransaction);

    void remove(Integer id);
}
