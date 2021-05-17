package com.revature.test.models;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.User;

public class UserTest {
	public User user;
	
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
		user = new User("MrBigglesworth", "Meow", "Customer");
	}

	@After
	public void tearDown() {
		System.out.println("After test");
	}

	//Ask about how tests can be used when the return type is void
	@Test
	public void testAdd_1() {
		System.out.println("Testing addUserDB");
		boolean result = user.addUserDB();
		assertTrue(result == true);
	}
}
