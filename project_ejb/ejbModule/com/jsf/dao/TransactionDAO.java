package com.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Transaction;


@Stateless
public class TransactionDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";
	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Transaction transaction) {
		em.persist(transaction);
	}

	public Transaction merge(Transaction transaction) {
		return em.merge(transaction);
	}

	public void remove(Transaction transaction) {
		em.remove(em.merge(transaction));
	}

	public Transaction find(Object id) {
		return em.find(Transaction.class, id);
	}

	public List<Transaction> getFullList() {
		List<Transaction> list = null;

		Query query = em.createQuery("select t from Transaction t");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	

}
