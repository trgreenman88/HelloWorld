package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.util.ConnectionUtility;

public class Transactions {
	protected int transactionid;
	protected String date;
	protected String description;
	protected double amount;
	
	
	//Constructors
	public Transactions() {}
	
	public Transactions(int transactionid, String date, String description, double amount) {
		this.transactionid = transactionid;
		this.date = date;
		this.description = description;
		this.amount = amount;
	}

	
	//Getters and Setters
	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double withdrawDB(int accountid, int userid, double amount, double oldBalance) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//If the class is not found the driver could not be registered. 
			System.out.println("Could not register driver!");
			e.printStackTrace();
		}

		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			Connection connection = DriverManager.getConnection(connectionInfo.getUrl(), 
					connectionInfo.getUsername(), connectionInfo.getPassword());
			double newBalance = oldBalance - amount;
			if(newBalance < 0) {
				//Maybe throw an exception here
				System.out.println("This transaction results in a negative account balance.");
				return -1;
			} 
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+accountid+" and userid = "+userid+";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}
	}
	
	
	public double depositDB(int accountid, int userid, double amount, double oldBalance) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//If the class is not found the driver could not be registered. 
			System.out.println("Could not register driver!");
			e.printStackTrace();
		}

		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			Connection connection = DriverManager.getConnection(connectionInfo.getUrl(), 
					connectionInfo.getUsername(), connectionInfo.getPassword());
			double newBalance = oldBalance + amount;
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+accountid+" and userid = "+userid+";";
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
	public double depositDB(int accountid, double amount, double oldBalance) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//If the class is not found the driver could not be registered. 
			System.out.println("Could not register driver!");
			e.printStackTrace();
		}

		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			Connection connection = DriverManager.getConnection(connectionInfo.getUrl(), 
					connectionInfo.getUsername(), connectionInfo.getPassword());
			double newBalance = oldBalance + amount;
			String sql = "UPDATE account SET balance = "+newBalance+" WHERE accountid = "+accountid+";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			return newBalance;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}
	}
	
	public double[] transferDB(int accountid1, int accountid2, int userid, double amount, double balance1, double balance2) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//If the class is not found the driver could not be registered. 
			System.out.println("Could not register driver!");
			e.printStackTrace();
		}

		try {
			ConnectionUtility connectionInfo = new ConnectionUtility();
			Connection connection = DriverManager.getConnection(connectionInfo.getUrl(), 
					connectionInfo.getUsername(), connectionInfo.getPassword());
			double newBalance1 = balance1 - amount;
			double newBalance2 = balance2 + amount;
			if(newBalance1 < 0) {
				//Maybe throw an exception here
				System.out.println("This transaction results in a negative account balance.");
				return null;
			} 
			String sql1 = "UPDATE account SET balance = "+newBalance1+" WHERE accountid = "+accountid1+" and userid = "+userid+";";
			PreparedStatement statement1 = connection.prepareStatement(sql1);
			statement1.execute();
			String sql2 = "UPDATE account SET balance = "+newBalance2+"WHERE accountid = "+accountid2+";";
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
	
	//Logging a withdrawal and deposit
	private static final Logger LOG = LogManager.getLogger(User.class);
	
	public void logWithdrawl(double amount, int accountid) {
		LOG.info("$"+amount+" has been withdrawn from account "+accountid);
	}
	
	public void logDeposit(double amount, int accountid) {
		LOG.info("$"+amount+" has been deposited into account "+accountid);
	}
	
	//Logging a transfer
	public void logTransfer(double amount, int accountid1, int accountid2) {
		LOG.info("$"+amount+" transfered from account "+accountid1+" to account "+accountid2);
	}
}
