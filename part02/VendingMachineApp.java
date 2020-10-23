package part02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VendingMachineApp {
	static final int MAX = 5; // max value for stock array
	static int count = 0; // count to indicate number of objects in the array
	static final String options[] = { "List available items", "Enter a coin", "Purchase an item", "Exit" }; // basic
																											// options
																											// for
	// the USER
	static final String title = "QUB Vending Machine"; // title of menu
	static final int QUIT = options.length; // quit value
	static Menu qubMenu = new Menu(title, options); // menu instantiation
	static Scanner input = new Scanner(System.in); // scanner to take user input

	static VendingMachine qubMachine = new VendingMachine("QUB", 5); // vendingmachine object
	static VendItem stock[] = createItems(); // array to hold venditem objects 5 max size

	static final String adminOptions[] = { "View system information", "Add new item", "Restock item", "Reset system",
			"Switch system status", "Add change to the machine", "User Mode" }; // array holding the admin options, when user is in
																	// admin mode
	static Menu adminMenu = new Menu("Admin Configuration", adminOptions); // menu instantiation for admin mode

	public static void main(String[] args) {
		int choice = qubMenu.getChoice();
		while (choice != options.length) {
			processChoice(choice);
			choice = qubMenu.getChoice();
		}
		qubMachine.writeState();
		System.out.println("Goodbye!");
	}

	/**
	 * processing the choice the user selects
	 * 
	 * @param choice
	 */
	private static void processChoice(int choice) {
		switch (choice) {
		case 0:
			adminMode();
			break;
		case 1:
			listItems();
			break;
		case 2:
			insertCoin();
			break;
		case 3:
			purchaseItem();
			break;
		default:
			System.out.println("Error!");
		}

	}

	/**
	 * lists items for purchase and allows the user to select an item to decrease
	 * its quantity and their current 'balance'
	 */
	private static void purchaseItem() {
		Menu myVM = new Menu("\nPurchase Item", qubMachine.listItems());
		int choice = myVM.getChoice();
		System.out.println(qubMachine.purchaseItem(choice - 1)); // takes into account 0 based indexing when the user
																	// enters a value

	}

	/**
	 * user enters a coin value in pence to insert the respective amount into the
	 * machine increasing their stored balance and the machine's total balance
	 * declines if the user does not enter 5, 10, 20, 50, 100 or 200 (coin values)
	 */
	private static void insertCoin() {
		System.out.println("\nInsert Coin");
		System.out.println("+++++++++++");
		System.out.println("\nEnter the value in coin form, form the list below:");
		System.out.println("5: 5p");
		System.out.println("10: 10p");
		System.out.println("20: 20p");
		System.out.println("50: 50p");
		System.out.println("100: £1");
		System.out.println("200: £2");
		System.out.println("\n7. Back");

		System.out.println("\nEnter the coin value you want to insert: ");
		int choice = input.nextInt();

		if (qubMachine.insertCoin(choice) == true) {
			System.out.println("\nCoin accepted");
			System.out.println("You have entered: " + choice + ", you now have: "
					+ String.format("£%.2f", qubMachine.getUserMoney()) + "\n");
		} else {
			System.out.println("\nCoin declined");
			System.out.println("You have entered: " + choice + ", you now have: "
					+ String.format("£%.2f", qubMachine.getUserMoney()) + "\n");
		}

		if (choice != 7) {
			insertCoin();
		}

	}

	/**
	 * displays a list of all items in the vending machine
	 */
	private static void listItems() {
		int y = 0;
		for (int i = 0; i < qubMachine.listItems().length; i++) {
			y++;
			System.out.println(y + ". " + qubMachine.listItems()[i]);
		}
		System.out.println("5. Back");
		System.out.println("\nEnter choice: ");
		int data = input.nextInt();
		if (data != 5) {
			System.out.println("\nTo purchase this item go back and select 'Purchase Item'.\n");
			listItems();
		}

	}

	/**
	 * method to add all items to the vending machine
	 * 
	 * @return - return the result stock array
	 */
	private static VendItem[] createItems() {
		VendItem stock[] = new VendItem[qubMachine.getMaxItems()];
		VendItem item1 = new VendItem("Twix", 0.75, 1);
		VendItem item2 = new VendItem("Mars", 0.70, 5);
		VendItem item3 = new VendItem("Milky Way", 0.60, 5);
		VendItem item4 = new VendItem("Coca Cola", 1.60, 10);

		qubMachine.addNewItem(item1);
		qubMachine.addNewItem(item2);
		qubMachine.addNewItem(item3);
		qubMachine.addNewItem(item4);
		return stock;
	}

	/**
	 * hidden admin configuration when the user enters '0' moves to admin mode/admin
	 * menu when the password entered is correct
	 */

	private static void adminMode() {
		String password;

		System.out.println("\nEnter the admin password: ");
		System.out.println("++++++++++++++++++++++++");
		password = input.nextLine();

		while (password.equals("Password123")) { // THIS IS THE PASSWORD THAT MUST BE ENTERED TO PROCEED TO ADMIN MODE
			int adminChoice = adminMenu.getChoice();
			while (adminChoice != adminOptions.length) {
				processAdminChoice(adminChoice);
				adminChoice = qubMenu.getChoice();
			}
			System.out.println("Entering User Mode!");
			System.out.println("+++++++++++++++++++");
			break;
		}
	}

	/**
	 * processing the choice the user selects in admin mode
	 * 
	 * @param adminChoice - int value entered
	 */
	private static void processAdminChoice(int adminChoice) {
		switch (adminChoice) {
		case 1:
			viewSystemInformation();
			break;
		case 2:
			addNewItem();
			break;
		case 3:
			restockItem();
			break;
		case 4:
			resetMachine();
			break;
		case 5:
			switchStatus();
			break;
		case 6:
			addChange();
			break;
		default:
			System.out.println("Error!");
		}

	}

	private static void addChange() {
		System.out.println("1. 5p");
		System.out.println("2. 10p");
		System.out.println("3. 20p");
		System.out.println("4. 50p");
		System.out.println("5. £1");
		System.out.println("6. £2");

		System.out.println("Which coin are you adding?");
		int coin = input.nextInt();

		System.out.println("How many coins do you want to add");
		int value = input.nextInt();

		int[] data = qubMachine.getCoinsQty();
		data[coin - 1] += value;
		qubMachine.setCoinsQty(data);

		// increasing totalMoney
		double amount = qubMachine.getTotalMoney();
		amount += qubMachine.getCoinsQty()[0] * 0.05 + qubMachine.getCoinsQty()[1] * 0.10
				+ qubMachine.getCoinsQty()[2] * 0.20 + qubMachine.getCoinsQty()[3] * 0.50
				+ qubMachine.getCoinsQty()[4] * 1.00 + qubMachine.getCoinsQty()[5] * 2.00;
		qubMachine.setTotalMoney(amount);

		/*
		 * prints the value of the coin and the amount held in the machine
		 */
		System.out.println("\nNumber of Coins");
		System.out.println("+++++++++++++++");
		System.out.print("5p: " + qubMachine.getCoinsQty()[0] + ", ");
		System.out.print("10p: " + qubMachine.getCoinsQty()[1] + ", ");
		System.out.print("20p: " + qubMachine.getCoinsQty()[2] + ", ");
		System.out.print("50p: " + qubMachine.getCoinsQty()[3] + ", ");
		System.out.print("£1: " + qubMachine.getCoinsQty()[4] + ", ");
		System.out.print("£2: " + qubMachine.getCoinsQty()[5]);

		System.out.println("\nTotal amount in machine: " + String.format("£%.2f", qubMachine.getTotalMoney()));

		System.out.println();
		System.out.println();
	}

	/**
	 * displays the two modes which the user can select to set the status of the
	 * machine
	 */
	private static void switchStatus() {
		System.out.println("\nSwitch Status");
		System.out.println("+++++++++++++");
		System.out.println("\n1: Vending Mode");
		System.out.println("2: Service Mode");

		System.out.println("Enter choice: ");
		int choice = input.nextInt();

		switch (choice) {
		case 1:
			qubMachine.setStatus(Status.VENDING_MODE);
			System.out.println(qubMachine.getStatus());
			break;
		case 2:
			qubMachine.setStatus(Status.SERVICE_MODE);
			System.out.println(qubMachine.getStatus());
			break;
		default:
			System.out.println("Error");
		}
	}

	/**
	 * resets the machine to default values null or 0 where appropriate
	 */
	private static void resetMachine() {
		System.out.println("\nReset Machine");
		System.out.println("+++++++++++++");
		qubMachine.reset();
	}

	/**
	 * user enters the value to restock and the index at which it is held
	 */
	private static void restockItem() {
		System.out.println("Enter the amount you wish to restock: ");
		int amount = input.nextInt();

		System.out.println("Enter the position of the item you wish to restock: ");
		int i = input.nextInt();
		VendItem item = qubMachine.getStock()[i];

		System.out.println(item.getName() + "'s quantity before restock: " + item.getQty());
		item.restock(amount);
		System.out.println(item.getName() + "'s quantity after restock: " + item.getQty());

	}

	private static void addNewItem() {
		System.out.println("\nAdd Item");
		System.out.println("++++++++");

		Scanner additem = new Scanner(System.in);

		String name;
		double price;
		int quantity;

		System.out.print("Enter Name: ");
		name = additem.nextLine();
		System.out.print("Enter Price: ");
		price = additem.nextDouble();
		System.out.print("Enter Quantity: ");
		quantity = additem.nextInt();

		VendItem item = new VendItem(name, price, quantity);
		if (qubMachine.addNewItem(item)) {
			System.out.println("Item added");
		} else {
			System.out.println("Error: Item was not added");
		}

	}

	private static void viewSystemInformation() {
		System.out.println("\nSystem Information");
		System.out.println("++++++++++++++++++");
		System.out.println(qubMachine.getSystemInfo());
		return;
	}
}
