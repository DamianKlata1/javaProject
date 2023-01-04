package com.project.systemManagement;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import com.jsf.dao.RoleDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Role;
import com.jsf.entities.User;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named
@ViewScoped
public class userEditBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_USER_LIST = "/pages/systemAdmin/userList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	@Inject
	UserDAO userDAO;
	@Inject
	RoleDAO roleDAO;

	private User user = new User();
	private User loaded = null;

	private List<Role> selectedRoles;
	private List<Role> availableRoles;

	public User getUser() {
		return user;
	}

	public void onLoad() throws IOException {
		// 1. load person passed through session
		// HttpSession session = (HttpSession)
		// context.getExternalContext().getSession(true);
		// loaded = (Person) session.getAttribute("person");

		// 2. load person passed through flash
		loaded = (User) flash.get("user");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			user = loaded;
			availableRoles = roleDAO.getFullList();
			selectedRoles = userDAO.getUserRolesFromDatabase(loaded);

			// session.removeAttribute("user");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("personList.xhtml");
			// context.responseComplete();
			// }
		}

	}

	public List<Role> getSelectedRoles() {
		return selectedRoles;
	}

	public void setSelectedRoles(List<Role> selectedRoles) {
		this.selectedRoles = selectedRoles;
	}

	public List<Role> getAvailableRoles() {
		return availableRoles;
	}

	public void setAvailableRoles(List<Role> availableRoles) {
		this.availableRoles = availableRoles;
	}

	public String saveData() {
		// no User object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (user.getIdUser() == null) {
				if (userDAO.checkIfLoginOrEmailExists(user.getLogin(), user.getE_mail())) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Na podany login lub e-mail juz istnieje konto", null));
					return PAGE_STAY_AT_THE_SAME;
				} else {
					// new record
					user.setRoles(selectedRoles);
					userDAO.create(user);
				}
			}

			else {
				// existing record
				if (!userDAO.getUserIdByLogin(user.getLogin()).equals(user.getIdUser())) {
					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Na podany login juz istnieje konto", null));
					return PAGE_STAY_AT_THE_SAME;
				} else if (!userDAO.getUserIdByEmail(user.getE_mail()).equals(user.getIdUser())) {
					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Na podany e-mail juz istnieje konto", null));
					return PAGE_STAY_AT_THE_SAME;
				} else {
					user.setRoles(selectedRoles);
					userDAO.merge(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_USER_LIST;
	}

}
