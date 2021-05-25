package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.util.ConnectionUtility;

public class Account {
	protected int accountid;
	protected double balance = 0;
	protected boolean approved = false;
	
	
	//Constructors
	public Account() {}
	
	public Account(double balance) {
		this.balance = balance;
	}
	
	public Account(int accountid) {
		this.accountid = accountid;
	}

	
	//Getters and Setters
	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean getApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	//Adding new user to database
	public boolean addPendingAccountDB(int userid) {
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
			String sql = "INSERT INTO account (balance, userid, approved) VALUES ('"+balance+"', '"+userid+"', false);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			System.out.println("New pending account has been successfully created.");
			return true;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}
	}
	
	//Retrieving balance from database
	public double getBalanceDB(int accountid, int userid) {
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
			String sql = "select balance from account where accountid = '"+accountid+"' and userid = '"+userid+"' limit 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
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
		}
	}
	
	//Used for transfers
	public double getBalanceDB(int accountid) {
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
			String sql = "select balance from account where accountid = '"+accountid+"' limit 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
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
		}
	}
	
	//Approve pending account
	public boolean approveAccountDB(int accountid) {
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
			String sql = "UPDATE account SET approved = true WHERE accountid = "+accountid+";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			System.out.println("Account "+accountid+" has been approved.");
			return true;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}
	}
	
	//Reject (Delete) pending account
	public boolean rejectAccountDB(int accountid) {
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
			String sql = "DELETE FROM account WHERE accountid = "+accountid+";";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			System.out.println("Account "+accountid+" has been rejected.");
			return true;
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}
	}
	
	public void getCustomerAccountsDB(int userid) {
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
			String sql = "select * from account where userid = '"+userid+"';";
			PreparedStatement statement = connection.prepareStatement(sql);
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
		}
	}
	
	public int getNewestAccountID() {
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
			String sql = "select max(accountid) from account;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			set.next();
			return set.getInt("max");
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return -1;
		}
	}
	
	public boolean checkApproved(int accountid) {
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
			String sql = "SELECT approved FROM account WHERE accountid = "+accountid+" limit 1;";
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			set.next();
			return set.getBoolean("approved");
		}catch(SQLException ex) {
			System.out.println("Failure");
			ex.printStackTrace();
			return false;
		}
	}
	
	//Logging the creation of an account
	private static final Logger LOG = LogManager.getLogger(User.class);
	
	public void logAccount(int accountid, double balance) {
		LOG.info("Account "+accountid+" has been created with starting balance "+balance);
	}
	
	public void logApprove(int accountid) {
		LOG.info("Account "+accountid+" has been approved.");
	}
	
	public void logReject(int accountid) {
		LOG.info("Account "+accountid+" has been rejected.");
	}
}
