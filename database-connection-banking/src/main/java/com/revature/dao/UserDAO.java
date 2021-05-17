package com.revature.dao;

import model.User;
//Implement this
public interface UserDAO {
	/*
	 * createUser
	 * updateUsername
	 * updatePassword
	 * updateStatus
	 * getUsername
	 * getPassword
	 * getStatus
	 * deleteUser*/
	
	public boolean createUser(User user);
	public boolean updateUsername(String oldUsername, String newUsername);
	public boolean updatePassword(String oldPassword, String newPassword);
	public boolean updateStatus(String oldStatus, String newStatus);
	public String getUsername(User user);
	public String getPassword(User user);
	public String getStatus(User user);
	public boolean deleteUser(User user);
}
