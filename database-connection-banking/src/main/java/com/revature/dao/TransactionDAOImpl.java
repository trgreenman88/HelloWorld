package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.util.ConnectionUtility;

import model.Account;
import model.Transactions;

public class TransactionDAOImpl implements TransactionDAO{
	private PreparedStatement statement;
	private Connection connection;
	private PreparedStatement statement1;
	private PreparedStatement statement2;
	
	@Override
	public double withdrawDB(Account account, int userid, Transactions transaction, double oldBalance) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			double newBalance = oldBalance - transaction.getAmount();
			if(newBalance < 0) {
				//Maybe throw an exception here
				System.out.println("This transaction results in a negative account balance.");
				return -1;
			} 
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+account.getAccountid()+" and userid = "+userid+";";
			statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
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
	public double withdrawDB(Account account, Transactions transaction, double oldBalance) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			double newBalance = oldBalance - transaction.getAmount();
			if(newBalance < 0) {
				//Maybe throw an exception here
				System.out.println("This transaction results in a negative account balance.");
				return -1;
			} 
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+account.getAccountid()+";";
			statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}finally {
			closeResources(); 
		}
	}
	
	@Override
	public double depositDB(Account account, int userid, Transactions transaction, double oldBalance) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			double newBalance = oldBalance + transaction.getAmount();
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+account.getAccountid()+" and userid = "+userid+";";
			statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
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
	public double depositDB(Account account, Transactions transaction, double oldBalance) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			double newBalance = oldBalance + transaction.getAmount();
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+account.getAccountid()+";";
			statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}finally {
			closeResources(); 
		}
	}
	
	@Override
	public double[] transferDB(Account account1, Account account2, int userid, Transactions transaction, double balance1, double balance2) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			double newBalance1 = balance1 - transaction.getAmount();
			double newBalance2 = balance2 + transaction.getAmount();
			if(newBalance1 < 0) {
				//Maybe throw an exception here
				System.out.println("This transaction results in a negative account balance.");
				return null;
			} 
			String sql1 = "UPDATE account SET balance = "+newBalance1+" WHERE accountid = "+account1.getAccountid()+" and userid = "+userid+";";
			statement1 = connection.prepareStatement(sql1);
			statement1.execute();
			String sql2 = "UPDATE account SET balance = "+newBalance2+"WHERE accountid = "+account2.getAccountid()+";";
			statement2 = connection.prepareStatement(sql2);
			statement2.execute();
			double[] result = {newBalance1, newBalance2};
			return result;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return null;
		}finally {
			closeResources2(); 
		}
	}
	
	public boolean pendingTransferDB(Account account1, Account account2, int userid, Transactions transaction) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "insert into transactions (userid, amount, paying_accountid, recieving_accountid) values "
					+ "("+userid+", "+transaction.getAmount()+", "+account1.getAccountid()+", "+account2.getAccountid()+");";
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
	
	@Override
	public Transactions getTransactionDB(int transactionid) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "SELECT * FROM transactions WHERE transactionid = "+transactionid+" limit 1;";
			statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			if(!set.next()) {
				System.out.println("This is not a valid transaction ID.");
				return null;
			}
			double amount = set.getDouble("amount");
			int userid = set.getInt("userid");
			int payingAccountid = set.getInt("paying_accountid");
			int recievingAccountid = set.getInt("recieving_accountid");
			Transactions transaction = new Transactions(transactionid, amount, userid, payingAccountid, recievingAccountid);
			return transaction;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return null;
		}finally {
			closeResources(); 
		}
	}

	@Override
	public boolean deletePendingTransaction(Transactions transaction) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			connection = connectionInfo.createConnection();
			String sql = "DELETE FROM transactions WHERE transactionid = "+transaction.getTransactionid()+";";
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
	
	private void closeResources2() {
		try {
			if (statement1 != null && !statement1.isClosed()) {
				statement.close();
			}
			if (statement2 != null && !statement2.isClosed()) {
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
