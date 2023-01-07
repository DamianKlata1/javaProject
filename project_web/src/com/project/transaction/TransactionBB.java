package com.project.transaction;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.dao.BookDAO;
import com.jsf.dao.TransactionDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Transaction;
import com.jsf.entities.Book;

@Named
@RequestScoped
public class TransactionBB {
	FacesContext ctx = FacesContext.getCurrentInstance();

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private String searchBar;
	private List<Transaction> transactionList;
	private List<Transaction> userTransactionList;
	@Inject
	TransactionDAO transactionDAO;
	@Inject
	UserDAO userDAO;
	@Inject
	BookDAO bookDAO;

	public String getSearchBar() {
		return searchBar;
	}

	public void setSearchBar(String searchBar) {
		this.searchBar = searchBar;
	}

	public List<Transaction> getTransactionList() {
		if (searchBar != null) {
			transactionList = transactionDAO.getTransactionListBySearchBar(searchBar);
		} else {
			transactionList = transactionDAO.getFullList();
		}
		return transactionList;
	}

	public List<Transaction> getUserTransactionList(Integer id) {

		userTransactionList = transactionDAO.getUserTransactionListById(id);
		return userTransactionList;
	}

	public String cancel(Transaction transaction) {
		Book book = bookDAO.find(transaction.getBook().getIdBook());
		book.setAvailable("yes");
		bookDAO.merge(book);

		transaction.setCancelReservationDate(new Date());
		transactionDAO.merge(transaction);
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie anulowano rezerwacje", null));
		return PAGE_STAY_AT_THE_SAME;
	}

	public String borrow(Transaction transaction) {

		transaction.setBorrowDate(new Date());
		transactionDAO.merge(transaction);
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie wypożyczono książke", null));
		return PAGE_STAY_AT_THE_SAME;
	}

	public String returnBook(Transaction transaction) {
		Book book = bookDAO.find(transaction.getBook().getIdBook());
		book.setAvailable("yes");
		bookDAO.merge(book);

		transaction.setReturnDate(new Date());
		transactionDAO.merge(transaction);
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pomyślnie zwrócono książke", null));
		return PAGE_STAY_AT_THE_SAME;

	}

}
