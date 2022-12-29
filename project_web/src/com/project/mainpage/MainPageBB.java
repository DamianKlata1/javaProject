package com.project.mainpage;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.dao.BookDAO;
import com.jsf.entities.Book;



@Named
@RequestScoped
public class MainPageBB {
	private String searchBar;
	private List<Book> bookList;
	

	public String getSearchBar() {
		return searchBar;
	}

	public void setSearchBar(String searchBar) {
		this.searchBar = searchBar;
	}
	
	@Inject
	BookDAO bookDAO;
	public List<Book> getBookList(){
		bookList=bookDAO.getListByTitleOrAuthor(searchBar);
		return bookList;
	}
	
	
}
