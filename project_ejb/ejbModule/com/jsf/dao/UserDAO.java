package com.jsf.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jsf.entities.User;
import com.jsf.entities.Book;
import com.jsf.entities.Role;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class UserDAO {
	private final static String UNIT_NAME = "jsfcourse-simplePU";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(User user) {
		em.persist(user);
	}

	public User merge(User user) {
		return em.merge(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}

	public User find(Object id) {
		return em.find(User.class, id);
	}

	public List<User> getFullList() {
		List<User> list = null;

		Query query = em.createQuery("select u from User u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<User> getList(Map<String, Object> searchParams) {
		List<User> list = null;

		// 1. Build query string with parameters
		String select = "select u ";
		String from = "from User u ";
		String where = "";
		String orderby = "order by u.surname asc, u.name";

		// search for surname
		String surname = (String) searchParams.get("surname");
		if (surname != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.surname like :surname ";
		}

		// ... other parameters ...

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (surname != null) {
			query.setParameter("surname", surname + "%");
		}

		// ... other parameters ...

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public User getUserFromDatabase(String login, String pass) {
		// TODO Auto-generated method stub

		try {
			return (User) em.createQuery("SELECT u FROM User u  where u.login = :value1 AND u.password = :value2")
					.setParameter("value1", login).setParameter("value2", pass).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public List<Role> getUserRolesFromDatabase(User user) {
		// TODO Auto-generated method stub
		Query q = em.createQuery("SELECT r FROM Role r INNER JOIN FETCH r.users u WHERE u.login = :login");
		q.setParameter("login", user.getLogin());
		List<Role> roles = q.getResultList();

		return roles;
	}
	public List<String> getUserRolesFromDatabaseAsStrings(User user) {
		// TODO Auto-generated method stub
		List<Role> roles = getUserRolesFromDatabase(user);
		ArrayList<String> list= new ArrayList<String>();
		for (Role role : roles) {
			list.add(role.getNameOfRole());
		}

		return list;
	}

	public List<Role> getDefaultRoleList() {
		List<Role> list = null;
		Query q = em.createQuery("select r FROM Role r where r.nameOfRole like :user");
		q.setParameter("user", "user");
		list = q.getResultList();
		return list;
	}

	public boolean checkIfLoginOrEmailExists(String login,String email) {
		Query q=em.createQuery("select u FROM User u where u.login like :login OR u.e_mail like :email");
		q.setParameter("login", login);
		q.setParameter("email", email);
		if(q.getResultList().size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	public List<User> getListByLoginOrEmail(String searchParameter) {
		List<User> list = null;

		Query query = em.createQuery("select u from User u where u.login like :searchParameter "
				+ "or u.e_mail like :searchParameter");
		query.setParameter("searchParameter", searchParameter+"%");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	public void setRolesToAnUser(User user,List<Role> roles) {
		user.setRoles(roles);
	}
	public Integer getUserIdByLogin(String login) {
		TypedQuery<Integer> query = em.createQuery("select u.idUser from User u where u.login like :login ",Integer.class);
		query.setParameter("login", login);
		return query.getSingleResult();
		
	}
	public Integer getUserIdByEmail(String email) {
		TypedQuery<Integer> query = em.createQuery("select u.idUser from User u where u.e_mail like :email ",Integer.class);
		query.setParameter("email", email);
		return query.getSingleResult();
		
	}
	

}
