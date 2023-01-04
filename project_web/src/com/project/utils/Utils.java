package com.project.utils;

import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.dao.RoleDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Role;

@Named
@RequestScoped
public class Utils {
	
	@Inject
	RoleDAO roleDAO;
	public List<Role> getRoleList(){
		return roleDAO.getFullList();
	}
	public HashSet<String> getRoleListAsHashSet(){
		return roleDAO.getFullListAsHashSet();
	}
	
	
	

}
