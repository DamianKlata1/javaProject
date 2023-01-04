package com.jsf.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRole;

	private byte active;

	private String nameOfRole;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="roles")
	private List<User> users;

	public Role() {
	}

	public int getIdRole() {
		return this.idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public String getNameOfRole() {
		return this.nameOfRole;
	}

	public void setNameOfRole(String nameOfRole) {
		this.nameOfRole = nameOfRole;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return nameOfRole ;
	}


	@Override
	public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Role)) return false;
        Role otherEntity = (Role) other;
        return Objects.equals(this.getIdRole(), otherEntity.getIdRole());
    }
	
	

}