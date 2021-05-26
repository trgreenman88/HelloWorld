package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.util.ConnectionUtility;

import model.User;

public class UserDAOImpl implements UserDAO{
	//Adding new user to database
	@Override
	public boolean addUserDB(User user) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			Connection connection = connectionInfo.createConnection();
			String sql = "INSERT INTO users (username, password, status) VALUES ('"+user.getUsername()+"',"
					+ " '"+user.getPassword()+"', '"+user.getStatus()+"');";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			return true;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}
		
	}

}
