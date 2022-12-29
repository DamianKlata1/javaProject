package com.project.utils;

import java.util.HashSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.dao.RoleDAO;
import com.jsf.dao.UserDAO;

@Named
@RequestScoped
public class Utils {
	
	@Inject
	RoleDAO roleDAO;
	public HashSet<String> getRoleListAsHashSet(){
		return roleDAO.getFullListAsHashSet();
	}
	

}
