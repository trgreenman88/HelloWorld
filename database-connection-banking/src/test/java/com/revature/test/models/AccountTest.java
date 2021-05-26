package com.revature.test.models;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.AccountDAOImpl;

import model.Account;

public class AccountTest {
	public int userid;
	public AccountDAOImpl accountDAO;
	public Account account;
	public Account account1;
	
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
		userid = 5;
		account = new Account(13, 3);
		account1 = new Account(1, 100);
		accountDAO = new AccountDAOImpl();
	}

	@After
	public void tearDown() {
		System.out.println("After test");
	}

	@Test
	public void testAddPendingAccountDB() {
		System.out.println("Testing addPendingAccountDB");
		boolean result = accountDAO.addPendingAccountDB(account, userid);
		assertTrue(result == true);
	}
	
	@Test
	public void testGetBalanceDB() {
		System.out.println("Testing getBalanceDB");
		double result = accountDAO.getBalanceDB(account);
		assertTrue(result == 3);
	}
	
	@Test
	public void testApproveAccountDB() {
		System.out.println("Testing approveAccountDB");
		boolean result = accountDAO.approveAccountDB(account);
		assertTrue(result == true);
	}
	
	@Test
	public void testCheckApproved() {
		System.out.println("Testing checkApproved");
		boolean result = accountDAO.checkApproved(account);
		assertTrue(result == true);
	}
	
	@Test
	public void testCheckValid() {
		System.out.println("Testing checkValidID");
		boolean result = accountDAO.checkValidID(account1);
		assertTrue(result == false);
	}
}
