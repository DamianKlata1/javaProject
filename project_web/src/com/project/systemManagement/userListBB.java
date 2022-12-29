package src.com.project.systemManagement;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.jsf.dao.UserDAO;
import com.jsf.entities.Role;
import com.jsf.entities.User;

@Named
@RequestScoped
public class userListBB {
	private String searchBar;
	private List<User> userList;
	
	public String getSearchBar() {
		return searchBar;
	}
	public void setSearchBar(String searchBar) {
		this.searchBar = searchBar;
	}
	
	@Inject
	UserDAO userDAO;
	public List<User> getUserList(){
		userList=userDAO.getListByLoginOrEmail(searchBar);
		return userList;
	}
	public List<Role> getRoleListOfUser(User user){
		List<Role> roleList=null;
		roleList=userDAO.getUserRolesFromDatabase(user);
		return roleList;
	}

}
