package com.project.systemManagement;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.dao.UserDAO;

import com.jsf.entities.Role;
import com.jsf.entities.User;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

@Named
@RequestScoped
public class userListBB {
	private static final String PAGE_USER_EDIT = "/pages/systemAdmin/userEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_MAIN = "/pages/public/index";

	private String searchBar;
	private List<User> userList;

	@Inject
	Flash flash;

	@Inject
	ExternalContext extcontext;

	public String getSearchBar() {
		return searchBar;
	}

	public void setSearchBar(String searchBar) {
		this.searchBar = searchBar;
	}

	@Inject
	UserDAO userDAO;

	public List<User> getUserList(){
		if(searchBar!=null) {
			userList=userDAO.getListByLoginOrEmail(searchBar);
		}
		else {
			userList=userDAO.getFullList();
		}
		
		
		return userList;
	}

	public List<Role> getRoleListOfUser(User user) {
		List<Role> roleList = null;
		roleList = userDAO.getUserRolesFromDatabase(user);
		return roleList;
	}

	public String newUser() {
		User user = new User();

		// 1. Pass object through session
		// HttpSession session = (HttpSession) extcontext.getSession(true);
		// session.setAttribute("person", person);

		// 2. Pass object through flash
		flash.put("user", user);

		return PAGE_USER_EDIT;
	}

	public String editUser(User user) {
		// 1. Pass object through session
		// HttpSession session = (HttpSession) extcontext.getSession(true);
		// session.setAttribute("person", person);

		// 2. Pass object through flash
		flash.put("user", user);

		return PAGE_USER_EDIT;
	}

}
