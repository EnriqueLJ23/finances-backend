package com.jasso.finance.finance.service;

import com.jasso.finance.finance.dao.TransactionDAO;
import com.jasso.finance.finance.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    //create field for Transaction DAO
    private TransactionDAO transactionDAO;

    //inject it with the constructor
    @Autowired
    public TransactionServiceImpl(TransactionDAO theTransactionDAO) {
        transactionDAO = theTransactionDAO;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionDAO.findAll();
    }

    @Override
    public Transaction findById(Integer id) {
        return transactionDAO.findById(id);
    }

    @Override
    public void save(Transaction theTransaction) {
        transactionDAO.save(theTransaction);
    }

    @Override
    public void remove(Integer id) {
        transactionDAO.remove(id);
    }
}
