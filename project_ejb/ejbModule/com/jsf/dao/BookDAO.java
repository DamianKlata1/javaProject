package com.jsf.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.jsf.entities.Book;

@Stateless
public class BookDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Book book) {
		em.persist(book);
	}

	public Book merge(Book book) {
		return em.merge(book);
	}

	public void remove(Book book) {
		em.remove(em.merge(book));
	}

	public Book find(Object id) {
		return em.find(Book.class, id);
	}

	public List<Book> getFullList() {
		List<Book> list = null;

		Query query = em.createQuery("select b from Book b");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Book> getListByTitleOrAuthor(String searchParameter) {
		List<Book> list = null;

		Query query = em.createQuery("select b from Book b where b.title like :searchParameter "
				+ "or b.author like :searchParameter");
		query.setParameter("searchParameter", searchParameter+"%");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

}
