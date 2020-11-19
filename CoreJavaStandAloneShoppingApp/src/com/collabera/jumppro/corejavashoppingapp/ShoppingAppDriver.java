package com.collabera.jumppro.corejavashoppingapp;

import java.util.Scanner;

public class ShoppingAppDriver {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int userChoice = 0;

		boolean isLoggedIn = false;

		while (userChoice != 5) {
			if (isLoggedIn == false) {
				printLoginMenu();
				userChoice = input.nextInt();

				switch (userChoice) {
				case 1:

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
}
