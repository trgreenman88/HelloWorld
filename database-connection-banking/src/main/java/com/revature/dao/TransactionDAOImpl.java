package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.revature.util.ConnectionUtility;

import model.Account;
import model.Transactions;

public class TransactionDAOImpl implements TransactionDAO{
	@Override
	public double withdrawDB(Account account, int userid, Transactions transaction, double oldBalance) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			Connection connection = connectionInfo.createConnection();
			double newBalance = oldBalance - transaction.getAmount();
			if(newBalance < 0) {
				//Maybe throw an exception here
				System.out.println("This transaction results in a negative account balance.");
				return -1;
			} 
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+account.getAccountid()+" and userid = "+userid+";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public double depositDB(Account account, int userid, Transactions transaction, double oldBalance) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			Connection connection = connectionInfo.createConnection();
			double newBalance = oldBalance + transaction.getAmount();
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+account.getAccountid()+" and userid = "+userid+";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}
	}
	
	//Used for transfers
	@Override
	public double depositDB(Account account, Transactions transaction, double oldBalance) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			Connection connection = connectionInfo.createConnection();
			double newBalance = oldBalance + transaction.getAmount();
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+account.getAccountid()+";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}
	}
	
	@Override
	public double[] transferDB(Account account1, Account account2, int userid, Transactions transaction, double balance1, double balance2) {
		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			connectionInfo.registerDriver();
			Connection connection = connectionInfo.createConnection();
			double newBalance1 = balance1 - transaction.getAmount();
			double newBalance2 = balance2 + transaction.getAmount();
			if(newBalance1 < 0) {
				//Maybe throw an exception here
				System.out.println("This transaction results in a negative account balance.");
				return null;
			} 
			String sql1 = "UPDATE account SET balance = "+newBalance1+" WHERE accountid = "+account1.getAccountid()+" and userid = "+userid+";";
			PreparedStatement statement1 = connection.prepareStatement(sql1);
			statement1.execute();
			String sql2 = "UPDATE account SET balance = "+newBalance2+"WHERE accountid = "+account2.getAccountid()+";";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.execute();
			double[] result = {newBalance1, newBalance2};
			return result;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return null;
		}
	}
}
