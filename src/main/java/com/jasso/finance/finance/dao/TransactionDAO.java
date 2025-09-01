package com.jasso.finance.finance.dao;

import com.jasso.finance.finance.entity.Goal;
import com.jasso.finance.finance.entity.Transaction;

import java.util.List;

public interface TransactionDAO {
    List<Transaction> findAll();
    
    List<Transaction> findByUserId(Integer userId);

    Transaction findById(Integer id);

    void save(Transaction theTransaction);

    void remove(Integer id);
}
