package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class AuthenticationService {
	public boolean loginUserDB(String user, String password) {
		try {	
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			Connection connection = connectionInfo.createConnection();
			String sql = "select * from users where username = '"+user+"' and password = '"+password+"' limit 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			if (set.next() == true) {
				return true;
			}else {
				System.out.println("That is not a valid username or password.");
				return false;
			}
		}catch(SQLException ex){
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}
			
	}
	
	public User getUserInfo(String user, String password) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			Connection connection = connectionInfo.createConnection();
			String sql = "select * from users where username = '"+user+"' and password = '"+password+"' limit 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			set.next();
			//String[] result = {set.getString(1), set.getString(2), set.getString(3), set.getString(4)};
			User result = new User(Integer.parseInt(set.getString(1)), set.getString(2), set.getString(3), set.getString(4));
			return result;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
		}
		return null;

	}
	
}
