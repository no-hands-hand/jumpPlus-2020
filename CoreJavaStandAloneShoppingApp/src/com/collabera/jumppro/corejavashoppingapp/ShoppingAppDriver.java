package com.collabera.jumppro.corejavashoppingapp;

import java.util.Scanner;
import java.sql.*;

public class ShoppingAppDriver {
	
	private static String myConnString = "jdbc:mysql://localhost:3306/ecommerce_java";
	private static String userName = "jdbc";
	private static String password = "password";
	//private Connection myConn = null;
//	private Statement myStat = null;
//	private ResultSet rs = null;
	
	public static void main(String[] args) throws SQLException{
		
		
		
		Scanner input = new Scanner(System.in);
		int userChoice = 0;

		boolean isLoggedIn = false;

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

					break;
				case 3:

					break;
				case 4:

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

					break;

				default:
					break;
				}
			}
		}

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
		
	}
	
	public static int registerUser() {
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
		String password = input.nextLine();
		System.out.print("\nPlease enter your starting credit in xx.xx format: ");
		double credit = input.nextDouble();
		
		int status = 0;
		try {
			myConn = DriverManager.getConnection(myConnString, userName, password);
			
			ps = myConn.prepareStatement("insert into users(f_name, l_name, email, password, credit) values(?, ?, ?, ?, ?)");
			ps.setString(1, fName);
			ps.setString(2, lName);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setDouble(5, credit);
			
			status = ps.executeUpdate();
			
			System.out.println("Rows updated (if 0, error occurred): " + status);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		
		
		return status;
	}
}
