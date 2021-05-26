package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionUtility;

import model.Account;

public class AccountDAOImpl implements AccountDAO {
	private PreparedStatement statement;
	private Connection connection;
	
	//Adding new pending account to database
	@Override
	public boolean addPendingAccountDB(Account account, int userid) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "INSERT INTO account (balance, userid, approved) VALUES ('"+account.getBalance()+"', '"+userid+"', false);";
			statement = connection.prepareStatement(sql);
			statement.execute();
			System.out.println("New pending account has been successfully created.");
			return true;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}finally {
			closeResources(); 
		}
	}
	
	//Retrieving balance from database
	@Override
	public double getBalanceDB(Account account, int userid) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "select balance from account where accountid = '"+account.getAccountid()+"' and userid = '"+userid+"' limit 1;";
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			if (set.next() == true) {
				return set.getDouble("balance");
			}else {
				System.out.println("That is not a valid Account ID for your user.");
				return -1;
			}
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}finally {
			closeResources(); 
		}
	}
	
	//Used for transfers
	@Override
	public double getBalanceDB(Account account) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "select balance from account where accountid = '"+account.getAccountid()+"' limit 1;";
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			if (set.next() == true) {
				return set.getDouble("balance");
			}else {
				System.out.println("That is not a valid Account ID for your user.");
				return -1;
			}
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}finally {
			closeResources(); 
		}
	}
	
	//Approve pending account
	@Override
	public boolean approveAccountDB(Account account) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "UPDATE account SET approved = true WHERE accountid = "+account.getAccountid()+";";
			statement = connection.prepareStatement(sql);
			statement.execute();
			System.out.println("Account "+account.getAccountid()+" has been approved.");
			return true;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}finally {
			closeResources(); 
		}
	}
	
	//Reject (Delete) pending account
	@Override
	public boolean rejectAccountDB(Account account) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "DELETE FROM account WHERE accountid = "+account.getAccountid()+";";
			statement = connection.prepareStatement(sql);
			statement.execute();
			System.out.println("Account "+account.getAccountid()+" has been rejected.");
			return true;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}finally {
			closeResources(); 
		}
	}
	
	@Override
	public void getCustomerAccountsDB(int userid) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "select * from account where userid = '"+userid+"';";
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			if (set.next() != true) {
				System.out.println("There are no accounts which belong to that user.");
			} else {
				System.out.println("AccountID: "+set.getInt("accountid")+", Balance: "
						+set.getDouble("balance")+", Approved: "+set.getBoolean("approved")
						+", UserID: "+set.getInt("userid"));
				while(set.next() == true) {
					System.out.println("AccountID: "+set.getInt("accountid")+", Balance: "
							+set.getDouble("balance")+", Approved: "+set.getBoolean("approved")
							+", UserID: "+set.getInt("userid"));
				}
			}
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
		}finally {
			closeResources(); 
		}
	}
	
	@Override
	public int getNewestAccountID() {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "select max(accountid) from account;";
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			set.next();
			return set.getInt("max");
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}finally {
			closeResources(); 
		}
	}
	
	@Override
	public boolean checkApproved(Account account) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "SELECT approved FROM account WHERE accountid = "+account.getAccountid()+" limit 1;";
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			set.next();
			//System.out.println("That is not a valid account ID");
			return set.getBoolean("approved");
			
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}finally {
			closeResources(); 
		}
	}
	
	@Override
	public boolean checkValidID(Account account) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "SELECT approved FROM account WHERE accountid = "+account.getAccountid()+" limit 1;";
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			if(set.next()) {
				return true;
			}else {
				System.out.println("That account ID does not exist.");
				return false;
			}
			
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}finally {
			closeResources(); 
		}
	}
	
	@Override
	public boolean checkOwnership(Account account, int userid) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "SELECT * FROM account WHERE accountid = "+account.getAccountid()+" AND userid = "+userid+" limit 1;";
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			if(set.next()) {
				return true;
			}else {
				System.out.println("That account ID is not valid for your user.");
				return false;
			}
			
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
