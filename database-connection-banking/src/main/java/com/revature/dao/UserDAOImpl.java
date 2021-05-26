package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.util.ConnectionUtility;

import model.User;

public class UserDAOImpl implements UserDAO{
	private PreparedStatement statement;
	private Connection connection;
	
	//Adding new user to database
	@Override
	public boolean addUserDB(User user) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "INSERT INTO users (username, password, status) VALUES ('"+user.getUsername()+"',"
					+ " '"+user.getPassword()+"', '"+user.getStatus()+"');";
			statement = connection.prepareStatement(sql);
			statement.execute();
			return true;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}finally {
			closeResources(); 
		}
		
	}
	
	private void closeResources() {
		try {
			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
			if(connection != null && !connection.isClosed()) {
				connection.close(); 
			}
		}catch(SQLException ex ) {
			ex.printStackTrace();
		}
	}

}
