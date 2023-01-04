package com.project.register;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.dao.UserDAO;
import com.jsf.entities.User;

@Named
@RequestScoped
public class RegisterBB {
	private static final String PAGE_MAIN = "/pages/public/index?faces-redirect=true";
	private static final String PAGE_LOGIN = "/pages/public/login";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String login;
	private String password;
	private String email;
	private String name;
	private String surname;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Inject
	UserDAO userDAO;

	public String doRegister() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		if(userDAO.checkIfLoginOrEmailExists(login, email)) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Na podany email lub login jest juz zarejestrowane konto", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		
		User user = new User();
		user.setName(name);
		user.setSurname(surname);
		user.setE_mail(email);
		user.setLogin(login);
		user.setPassword(password);
		user.setActive("yes");
		user.setRoles(userDAO.getDefaultRoleList());
		userDAO.create(user);
		
		ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Poprawnie zarejestrowano do systemu, teraz możesz się zalogować", null));

		return PAGE_LOGIN;
	}
	

}
