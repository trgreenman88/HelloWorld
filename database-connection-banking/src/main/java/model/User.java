package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.util.*;

public class User {
	protected int userid;
	protected String username;
	protected String password;
	protected String status;
	
	
	//Constructors
	public User() {}
	
	public User(int userid, String username, String password, String status) {
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.status = status; //Either "Employee" or "Customer"
	}
	
	public User(String username, String password, String status) {
		this.username = username;
		this.password = password;
		this.status = status; //Either "Employee" or "Customer"
	}
	
	//Getters and Setters
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
		
	//Logging the creation of a user
	private static final Logger LOG = LogManager.getLogger(User.class);
	
	public void logUser() {
		LOG.info("User, "+username+" has been created");
	}
	

}

