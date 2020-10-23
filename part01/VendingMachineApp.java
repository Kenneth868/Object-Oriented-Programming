package part01;

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

	public static void main(String[] args) {
		int choice = qubMenu.getChoice();
//		qubMachine.setStatus(Status.SERVICE_MODE);
		while (choice != options.length) {
			processChoice(choice);
			choice = qubMenu.getChoice();
		}
		System.out.println("Goodbye!");
	}

	/**
	 * processing the choice the user selects
	 * 
	 * @param choice
	 */
	private static void processChoice(int choice) {
		switch (choice) {
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

	private static void purchaseItem() {
		Menu myVM = new Menu("\nPurchase Item", qubMachine.listItems());
		int choice = myVM.getChoice();
		System.out.println(qubMachine.purchaseItem(choice - 1) + "\n");

	}

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

	private static void listItems() {
		int y = 0;
		for (int i = 0; i < qubMachine.listItems().length; i++) {
			y++;
			System.out.println(y + ". " + qubMachine.listItems()[i]);
		}
		System.out.println(qubMachine.listItems().length + 1 + ". " + "Back");
		System.out.println("\nEnter choice: ");
		int data = input.nextInt();
		if (data != qubMachine.listItems().length + 1) {
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
		VendItem item3 = new VendItem("Milky Way", 0.60, 6);
		VendItem item4 = new VendItem("Coca Cola", 1.60, 5);
		VendItem item5 = new VendItem("Fanta", 1.60, 0);

		qubMachine.addNewItem(item1);
		qubMachine.addNewItem(item2);
		qubMachine.addNewItem(item3);
		qubMachine.addNewItem(item4);
		qubMachine.addNewItem(item5);
		return stock;
	}
}
