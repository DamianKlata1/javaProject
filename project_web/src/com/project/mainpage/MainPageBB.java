package com.project.mainpage;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import com.jsf.dao.BookDAO;
import com.jsf.dao.TransactionDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Book;
import com.jsf.entities.Transaction;
import com.jsf.entities.User;



@Named
@RequestScoped
public class MainPageBB {
	private String searchBar;
	private List<Book> bookList;
	FacesContext ctx = FacesContext.getCurrentInstance();
	
	private static final String PAGE_STAY_AT_THE_SAME = null;
	public String getSearchBar() {
		return searchBar;
	}

	public void setSearchBar(String searchBar) {
		this.searchBar = searchBar;
	}
	@Inject
	UserDAO userDAO;
	@Inject
	TransactionDAO transactionDAO;
	
	@Inject
	BookDAO bookDAO;
	public List<Book> getBookList(){
		if(searchBar!=null) {
			bookList=bookDAO.getListByTitleOrAuthor(searchBar);
		}
		else {
			bookList=bookDAO.getFullList();
		}
		Iterator<Book> itr = bookList.iterator();
		while (itr.hasNext()) {
			  
            // Remove elements smaller than 10 using
            // Iterator.remove()
            Book b=(Book)itr.next();
            if (b.getAvailable().equals("no"))
                itr.remove();
        }
		return bookList;
	}
	public String reserveBook(Book book,Integer id) {
		Transaction transaction=new Transaction(new Date(),null,null,null,book,userDAO.find(id));
		book.setAvailable("no");
		transactionDAO.create(transaction);
		bookDAO.merge(book);
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Książka pomyślnie zarezerwowana", null));
		return PAGE_STAY_AT_THE_SAME;
	}
	
	
	
}
