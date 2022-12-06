package com.jsf.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the transaction database table.
 * 
 */
@Entity
@NamedQuery(name="Transaction.findAll", query="SELECT t FROM Transaction t")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTransaction;

	@Temporal(TemporalType.DATE)
	private Date borrowDate;

	@Temporal(TemporalType.DATE)
	private Date cancelReservationDate;

	@Temporal(TemporalType.DATE)
	private Date reservationDate;

	@Temporal(TemporalType.DATE)
	private Date returnDate;

	//bi-directional many-to-one association to Book
	@ManyToOne
	@JoinColumn(name="idBook")
	private Book book;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="idUser")
	private User user;

	public Transaction() {
	}

	public int getIdTransaction() {
		return this.idTransaction;
	}

	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
	}

	public Date getBorrowDate() {
		return this.borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getCancelReservationDate() {
		return this.cancelReservationDate;
	}

	public void setCancelReservationDate(Date cancelReservationDate) {
		this.cancelReservationDate = cancelReservationDate;
	}

	public Date getReservationDate() {
		return this.reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public Date getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}