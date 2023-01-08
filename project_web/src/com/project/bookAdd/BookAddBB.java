package com.project.bookAdd;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.dao.BookDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Book;
import com.jsf.entities.User;

@Named
@RequestScoped
public class BookAddBB {
	private static final String PAGE_MAIN = "/pages/public/index?faces-redirect=true";
	private static final String PAGE_LOGIN = "/pages/public/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String title;
	private String author;
	private Date releaseDate;
	private int page;
	private String publisher;

	@Inject
	BookDAO bookDAO;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String bookAdd() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		try {
			Book book = new Book();
			book.setTitle(title);
			book.setAuthor(author);
			book.setReleaseDate(releaseDate);
			book.setPage(page);
			book.setPublisher(publisher);
			book.setAvailable("yes");
			bookDAO.create(book);
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodano książke", null));
			return PAGE_MAIN;
		} catch (Exception e) {
			ctx.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas dodawania książki", null));
			return PAGE_STAY_AT_THE_SAME;
		}
	}

}
