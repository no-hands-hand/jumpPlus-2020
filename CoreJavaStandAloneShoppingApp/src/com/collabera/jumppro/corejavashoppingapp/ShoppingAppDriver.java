package com.collabera.jumppro.corejavashoppingapp;

import java.util.Scanner;
import java.sql.*;

public class ShoppingAppDriver {
	
	private static String myConnString = "jdbc:mysql://127.0.0.1:3306/ecommerce_java";
	private static String userName = "root";
	private static String password = "root";
	//private Connection myConn = null;
//	private Statement myStat = null;
//	private ResultSet rs = null;
	
	public static void main(String[] args) throws SQLException{
		
		
		
		Scanner input = new Scanner(System.in);
		int userChoice = 0;

		boolean isLoggedIn = false;
		User loggedInUser = null;
		Store store = null;
		//Store store = new Store();

		while (userChoice != 5) {
			if (isLoggedIn == false) {
				printLoginMenu();
				userChoice = input.nextInt();

				switch (userChoice) {
				case 1:
					int status = registerUser();
					if (status < 1) {
						System.out.println("Some error occurred, user was not registered.");
					}
					break;
				case 2:
					store = new Store();
					loggedInUser = login(store);
					if (loggedInUser != null) {
						isLoggedIn = true;
					}
					break;
				case 3:
					System.out.println("Please login to buy an item!");
					break;
				case 4:
					System.out.println("Please login to return an item!");
					break;
				case 5:
					break;
				default:
					break;
				}
			}

			else if (isLoggedIn == true) {
				
				printShoppingMenu();
				userChoice = input.nextInt();

				switch (userChoice) {
				case 1:
					Store.printItemList(store);
					break;
				case 2:
					System.out.println("Your credit: " + loggedInUser.getCredit());
					break;
				case 3:
					
					break;
				case 4:
					
					break; 
				default:
					break;
				}
			}
			
			
		}
		System.out.println("Goodbye!");
		input.close();
	}

	public static void printLoginMenu() {
		System.out.println("\tStandalone eCommerce App");
		System.out.println("+========================================+");
		System.out.println("|\t1. Register\t\t|");
		System.out.println("|\t2. Login\t\t|");
		System.out.println("|\t3. Buy an Item\t\t|");
		System.out.println("|\t4. Replace an Item\t\t|");
		System.out.println("|\t5. Exit\t\t|");
		System.out.println("+========================================+");
	}

	public static void printShoppingMenu() {
		System.out.println("\tStandalone eCommerce App");
		System.out.println("+========================================+");
		System.out.println("|\t1. View Item List\t\t|");
		System.out.println("|\t2. Check your balance!\t\t|");
		System.out.println("|\t3. Buy an Item\t\t|");
		System.out.println("|\t4. Replace an Item\t\t|");
		System.out.println("|\t5. Exit\t\t|");
		System.out.println("+========================================+");
	}
	
	public static int registerUser() throws SQLException {
		Scanner input = new Scanner(System.in);
		Connection myConn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		System.out.print("\nPlease enter your first name: ");
		String fName = input.nextLine();
		System.out.print("\nPlease enter your last name: ");
		String lName = input.nextLine();
		System.out.print("\nPlease enter your email: ");
		String email = input.nextLine();
		System.out.print("\nPlease enter your password: ");
		String userPass = input.nextLine();
		System.out.print("\nPlease enter your starting credit in xx.xx format: ");
		double credit = input.nextDouble();
		
		int status = 0;
		try {
			myConn = DriverManager.getConnection(myConnString, userName, password);
			
			ps = myConn.prepareStatement("insert into users(f_name, l_name, email, password, credit) values(?, ?, ?, ?, ?)");
			ps.setString(1, fName);
			ps.setString(2, lName);
			ps.setString(3, email);
			ps.setString(4, userPass);
			ps.setDouble(5, credit);
			
			status = ps.executeUpdate();
			
			System.out.println("Rows updated (if 0, error occurred): " + status);
		} catch (Exception e){
			System.out.println(e.getMessage());
		} finally {
			if(myConn != null) {
				myConn.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
		
		
		
		return status;
	}
	
	public static User login(Store store) {
		boolean isValidUser = false;
		User foundUser = null;
		Scanner input = new Scanner(System.in);
		System.out.println("--Please log in--");
		System.out.print("\nEmail: ");
		String email = input.nextLine();
		System.out.print("\nPassword: ");
		String pass = input.nextLine();
		
		for (User u : store.getUsers()) {
			if ((email.equalsIgnoreCase(u.getEmail())) && (pass.equalsIgnoreCase(u.getPassword()))) {
				isValidUser = true;
				foundUser = u;
				System.out.println("Login Success! Welcome " + u.getFirstName());
				break;
			}
		}
		
		if (isValidUser == false) {
			System.out.println("No user matching that email and password combination, please try again or register to create an account!");
		}
		return foundUser;
	}
}
