package com.revature.test.models;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.TransactionDAOImpl;

import model.Account;
import model.Transactions;

public class TransactionTest {
	public Account account;
	public Account account1;
	public Transactions transaction;
	public Transactions transaction1;
	public Transactions transaction2;
	public double amount;
	public int userid;
	public double oldBalance;
	public double oldBalance1;
	public TransactionDAOImpl transactionDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		System.out.println("Before Class");
	}

	@AfterClass
	public static void tearDownAfterClass() {
		System.out.println("After Class");
	}

	@Before
	public void setUp() {
		System.out.println("Before test");
		account = new Account();
		account1 = new Account();
		transaction = new Transactions(10000000);
		double amount = 2000;
		transaction1 = new Transactions(amount);
		transaction2 = new Transactions(100000);
		transactionDAO = new TransactionDAOImpl();
		userid = 2;
		oldBalance = 100;
		oldBalance1 = 50000;
	}

	@After
	public void tearDown() {
		System.out.println("After test");
	}

	@Test
	public void testWithdrawDB() {
		System.out.println("Testing withdrawDB");
		double result = transactionDAO.withdrawDB(account, userid, transaction, oldBalance);
		assertTrue(result == -1);
	}
	
	@Test
	public void testDepositDB() {
		System.out.println("Testing depositDB");
		double result = transactionDAO.depositDB(account, transaction, oldBalance);
		assertTrue(result == 10000100);
	}
	
	@Test
	public void testTransferDB() {
		System.out.println("Tesing transferDB");
		double[] result = transactionDAO.transferDB(account1, account, userid, transaction1, oldBalance1, oldBalance);
		double[] answer = {48000, 2100};
		assertTrue(Arrays.equals(result, answer));
	}
	
	@Test
	public void testTransferDB1() {
		System.out.println("Tesing transferDB1");
		double[] result = transactionDAO.transferDB(account1, account, userid, transaction2, oldBalance1, oldBalance);
		double[] answer = null;
		assertTrue(result == answer);
	}
}
