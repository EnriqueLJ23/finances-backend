package com.jasso.finance.finance.dao;

import com.jasso.finance.finance.entity.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDAOImpl implements TransactionDAO{
    //create the field for the entity manager
    private EntityManager entityManager;

    //inject the entity manager with the constructor
    @Autowired
    public TransactionDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<Transaction> findAll() {
        //make the query to retrieve all from DB
        TypedQuery<Transaction> query = entityManager.createQuery("FROM Transaction", Transaction.class);
        //return the results in a list
        return query.getResultList();
    }

    @Override
    public Transaction findById(Integer id) {
        return entityManager.find(Transaction.class,id);
    }

    @Override
    public void save(Transaction theTransaction) {
        entityManager.persist(theTransaction);
    }

    @Override
    public void remove(Integer id) {
        //find transaction by id
        Transaction theTransaction = entityManager.find(Transaction.class,id);

        entityManager.remove(theTransaction);
    }
}
