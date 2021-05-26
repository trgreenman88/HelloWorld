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
	protected double amount;
	protected int userid;
	protected int payingAccountid;
	protected int recievingAccountid;
	
	
	//Constructors
	public Transactions() {}
	
	public Transactions(int transactionid, double amount) {
		this.transactionid = transactionid;
		this.amount = amount;
	}
	
	public Transactions(double amount) {
		this.amount = amount;
	}
	
	public Transactions(int transactionid, double amount, int userid, int payingAccountid, int recievingAccountid) {
		this.transactionid = transactionid;
		this.amount = amount;
		this.userid = userid;
		this.payingAccountid = payingAccountid;
		this.recievingAccountid = recievingAccountid;
	}

	
	//Getters and Setters
	public int getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(int transactionid) {
		this.transactionid = transactionid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getUserid() {
		return userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public int getPayingAccountid() {
		return payingAccountid;
	}
	
	public void setPayingAccountid(int payingAccountid) {
		this.payingAccountid = payingAccountid;
	}
	
	public int getRecievingAccountid() {
		return recievingAccountid;
	}
	
	public void setRecievingAccountid(int recievingAccountid) {
		this.recievingAccountid = recievingAccountid;
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
	
	//Logging a pending transfer
	public void logPendingTransfer(double amount, int accountid1, int accountid2, int userid) {
		LOG.info("User "+userid+" has posted a transfer of $"+amount+" from account "+accountid1+" to "+accountid2);
	}
	
	//Logging an accept/reject of pending transfer
	public void logRejectTransfer(int transactionid) {
		LOG.info("Transaction "+transactionid+" was rejected");
	}
	
	public void logAcceptTransfer(int transactionid) {
		LOG.info("Transaction "+transactionid+" was accepted");
	}
}
