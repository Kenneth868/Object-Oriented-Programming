package part02;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;

public class VendingMachine {
	private String owner;
	private int maxItems;
	private int itemCount; // defines the number of venditems for sale
	// increments when an item is added to the machine
	private VendItem[] stock = new VendItem[maxItems];
	// array used to hold the stock of each item
	// array list not suitable as there must still be space available in the array
	// when an item runs out of stock
	private double totalMoney; // total money accrued by the machine, e.g. addition of all net gains (£0.80
								// mars bar, £1 input, £0.80 profit, £0.20 returned to user
	private double userMoney; // money entered before user decides which item they wish to vend
	private Status vmStatus;
	private int coins; // coins inserted?
	// increment each time?
	private int totalItemQty; // total item quantity used to detect when last item is sold
	private int[] coinsQty = new int[6]; // each value in array set to 1 initially
	/* each represents the coin denominations - e.g.
	 cointsQty[0] = number of 5p coins enteredW
	 coinsQty[1] = number of 10p coins entered and so on..
	 example [5, 10, 20, 50, 100, 200]
	 coinsQty[0, 0, 0, 0, 0, 0]*/

	/**
	 * Basic constructor
	 * 
	 * @param owner
	 * @param maxItems
	 */
	public VendingMachine(String owner, int maxItems) {
		setOwner(owner);
		setMaxItems(maxItems);
		setStatus(vmStatus);
		setStock(stock);
		setTotalItemQty(totalItemQty);
		setTotalMoney(totalMoney);
		setCoinsQty(coinsQty);
	}

	// getters
	public String getOwner() {
		return owner;
	}

	public int getMaxItems() {
		return maxItems;
	}

	public Status getStatus() {
		while (totalItemQty == 0) {
			vmStatus = Status.SERVICE_MODE;
			return vmStatus;
		}
		vmStatus = Status.VENDING_MODE;
		return vmStatus;
	}

	public VendItem[] getStock() {
		return stock;
	}

	public double getTotalMoney() {
		return totalMoney;
	}

	public double getUserMoney() {
		return userMoney;
	}

	public int getTotalItemQty() {
		return totalItemQty;
	}

	public int getItemCount() { // to get the item count
		// mainly to test and ensure item count gets incremented correctly when an item
		// is added
		return itemCount;
	}

	public int[] getCoinsQty() {
		return coinsQty;
	}

	// setters
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setMaxItems(int maxItems) {
		if (maxItems > 5) {
			// System.out.println("Vending machine can only hold 5 different items");
			return;
		}
		this.maxItems = maxItems;
	}

	// if the last item in the machine is purchased, status = service mode
	public void setStatus(Status vmStatus) {
		this.vmStatus = vmStatus;
	}

	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public void setUserMoney(double userMoney) {
		this.userMoney = userMoney;
	}

	public void setStock(VendItem[] stock) {
		stock = new VendItem[this.maxItems];
		this.stock = stock;
	}
	
	public void setTotalItemQty(int totalItemQty) {
		this.totalItemQty = totalItemQty;
	}

	public void setCoinsQty(int[] coinsQty) {
		this.coinsQty = coinsQty;
	}

	/**
	 * Display system information
	 * 
	 * @return - string holding all the information about the system
	 */
	public String getSystemInfo() {
		String sysInfo = "----------------------------\n";
		sysInfo += "Vending Machine Information \n";
		sysInfo += "----------------------------\n";
		sysInfo += "Owner: " + this.getOwner() + "\n";
		sysInfo += "Max Items: " + this.getMaxItems() + "\n";
		sysInfo += "Machine Status: " + this.getStatus() + "\n";
		sysInfo += "Stock: " + Arrays.toString(listItems()) + "\n";
		sysInfo += "User money: " + String.format("£%.2f", this.getUserMoney()) + "\n";
		sysInfo += "Total money: " + String.format("£%.2f", this.getTotalMoney()) + "\n";
		sysInfo += "Item count: " + this.getItemCount() + "\n";
		sysInfo += "----------------------------";
		return sysInfo;
	}

	/**
	 * instantiate all values to zero or null to reset the object state
	 */
	public void reset() {
		new VendingMachine(null, 0);
		this.owner = null;
		this.maxItems = 0;
		this.stock = null;
		this.totalMoney = 0;
		this.userMoney = 0;
		this.itemCount = 0;
		this.totalItemQty = 0;
		this.coinsQty = null;
		// reinitializes all variables to 0 when called

	}

	/**
	 * user selects an item and is able to purchase it, updating usermoney, reducing
	 * its quantity and increasing totalmoney
	 * 
	 * @param i - zero based index of the item the user wants to purchase
	 * @return - a string approving or denying the purchase and providing reasoning
	 */
	public String purchaseItem(int i) { // int i is the index of the item in the list of items/stock array
		if (this.stock[i].getQty() == 2) { // deny purchase if there is none of the item available/its quantity is 0
			String denyPurchase = "\nPurchase Declined \nThere are no " + stock[i].getName() + " left.\n";
			return denyPurchase;

		} else if (stock[i].getPrice() > this.userMoney) { // deny purchase if user does not have enough money
			String denyPurchase = "\nPurchase Declined \nYou have not entered enough money for this item. \n";
			denyPurchase += "You have currently entered: " + String.format("£%.2f", this.getUserMoney()) + "\n";
			denyPurchase += "Please insert " + String.format("£%.2f", stock[i].getPrice()) + " to buy this item.\n";
			return denyPurchase;

		} else if (this.vmStatus != Status.VENDING_MODE) {
			String denyPurchase = "\nPurchase Declined \nMachine is in service mode \n";
			return denyPurchase;

		} else if (this.totalItemQty == 0 || this.vmStatus == Status.SERVICE_MODE) {
			String denyPurchase = "\nPurchase Declined \nMachine is in service mode \n";
			return denyPurchase;

		} else
			stock[i].setQtyAvailable(stock[i].getQty() - 1); // approve purchase if the user has enough money and
																// there is atleast one item available
		totalItemQty -= 1;
		double changeDue = userMoney - stock[i].getPrice();
		String approvePurchase = stock[i].deliver();
		approvePurchase += "Change expected: " + String.format("£%.2f", changeDue);
		approvePurchase += ejectChange(changeDue);
		userMoney -= stock[i].getPrice();

		totalMoney += stock[i].getPrice();
		return approvePurchase; // if the user has entered enough money print a message signifying a successful
		// purchase and reduce quantity of the item by 1

	}

	/**
	 * takes the value of the coin entered by the user (in 1ps) to update the money
	 * the user has input
	 * 
	 * @param coins - represents the coin the user enters (in 1ps)
	 * @return - boolean value based upon if the user has entered a valid coin
	 */
	public boolean insertCoin(int coins) { // coins is the value in pence (how many 1p coins)
		switch (coins) {
		case 5:
			userMoney += 0.05; // 5p equivalent
			// System.out.println("You have entered: £0.05, you now have: " +
			// String.format("£%.2f", this.getUserMoney()));
			coinsQty[0]++;
			return true;
		case 10:
			userMoney += 0.10; // 10p equivalent
			// System.out.println("You have entered: £0.10, you now have: " +
			// String.format("£%.2f", this.getUserMoney()));
			coinsQty[1]++;
			return true;
		case 20:
			userMoney += 0.20; // 20p equivalent
			// System.out.println("You have entered: £0.20, you now have: " +
			// String.format("£%.2f", this.getUserMoney()));
			coinsQty[2]++;
			return true;
		case 50:
			userMoney += 0.50; // 50p equivalent
			// System.out.println("You have entered: £0.50, you now have: " +
			// String.format("£%.2f", this.getUserMoney()));
			coinsQty[3]++;
			return true;
		case 100:
			userMoney += 1.00; // £1 equivalent
			// System.out.println("You have entered: £1.00, you now have: " +
			// String.format("£%.2f", this.getUserMoney()));
			coinsQty[4]++;
			return true;
		case 200:
			userMoney += 2.00; // £2 equivalent
			// System.out.println("You have entered: £2.00, you now have: " +
			// String.format("£%.2f", this.getUserMoney()));
			coinsQty[5]++;
			return true;
		default:
			// System.out.println("Invalid amount entered. Please enter a value of either 5,
			// 10, 20, 50, 100 or 200.");
			return false; // return false if value entered is not valid
		}
	}

	/**
	 * 
	 * @param item
	 * @return - true or false if the user was able to add a new item or no
	 */
	// stock[] array initialised when object is instantiated
	// method adds a venditem to the stock array if there is space and if that item
	// does not already exist
	public boolean addNewItem(VendItem item) {
		for (int i = 0; i < maxItems; i++) {
			if (stock[i] != null && stock[i].equals(item) == true) {
				// System.out.println("This item already exists in the vending machine");
				break;
			} else if (stock[i] == null) {
				stock[i] = item;
				itemCount++;
				totalItemQty += item.getQty();
				return true;
			}
		}
		return false;

	}

	/**
	 * lists all items in the vendingmachine in an array
	 * 
	 * @return - string containing list of all items
	 */
	public String[] listItems() {
		String[] listOfItems = new String[itemCount];
		if (itemCount == 0) {
			listOfItems[1] = "There are no items for sale currently";
			return listOfItems;
		} else {
			for (int index = 0; index < itemCount; index++) {
				String name = stock[index].getName() + String.format(", £%.2f", stock[index].getPrice()) + ", "
						+ stock[index].getQty() + " left";
				if (listOfItems[index] != null && listOfItems[index].equals(name) == true) {
					break;
				} else if (listOfItems[index] == null) {
					listOfItems[index] = name;
				}
			}
			// System.out.println(Arrays.toString(listOfItems));

		}
		return listOfItems;
	}

	/**
	 * addition feature: calculate change due and the coin combination necessary
	 * 
	 * @param change - calculated when a purchase is successful
	 * @return - string containing details of the change given, change combination
	 *         and if the machine has enough change to eject
	 */
	public String ejectChange(double change) {
		change = (int) (Math.ceil(change * 100));

		int twoPounds = Math.round((int) change / 200);
		change = change % 200;

		int onePounds = Math.round((int) change / 100);
		change = change % 100;

		int fiftyPence = Math.round((int) change / 50);
		change = change % 50;

		int twentyPence = Math.round((int) change / 20);
		change = change % 20;

		int tenPence = Math.round((int) change / 10);
		change = change % 10;

		int fivePence = Math.round((int) change / 5);
		change = change % 5;

		if (coinsQty[5] >= twoPounds && coinsQty[4] >= onePounds && coinsQty[3] >= fiftyPence
				&& coinsQty[2] >= twentyPence && coinsQty[1] >= tenPence && coinsQty[0] >= fivePence) {
			String changeDue = "\nChange breakdown: ";
			changeDue += "\n£2: " + twoPounds;
			coinsQty[5] -= twoPounds; // updates amount of coins in the coinsQty array if there is enough to eject
										// change.
			// totalMoney += 2.00*twoPounds;

			changeDue += "\n£1: " + onePounds;
			coinsQty[4] -= onePounds; // updates amount of coins in the coinsQty array if there is enough to eject
										// change.
			// totalMoney += 1.00*onePounds;

			changeDue += "\n50p: " + fiftyPence;
			coinsQty[3] -= fiftyPence; // updates amount of coins in the coinsQty array if there is enough to eject
										// change.
			// totalMoney += 0.50*fiftyPence;

			changeDue += "\n20p: " + twentyPence;
			coinsQty[2] -= twentyPence; // updates amount of coins in the coinsQty array if there is enough to eject
										// change.
			// totalMoney += 0.20*twentyPence;

			changeDue += "\n10p: " + tenPence;
			coinsQty[1] -= tenPence; // updates amount of coins in the coinsQty array if there is enough to eject
										// change.
			// totalMoney += 0.10*tenPence;

			changeDue += "\n5p: " + fivePence;
			coinsQty[0] -= fivePence; // updates amount of coins in the coinsQty array if there is enough to eject
										// change.
			// totalMoney += 0.05*fivePence;

			return changeDue;
		}

		String changeDue = "\nMachine does not have enough change to eject.";
		return changeDue;

	}

	/**
	 * writes the current state of any vending machine to a csv file located at
	 * U:/ObjectOriented/40260280_SavedState.csv
	 */
	public void writeState() {
		String stateDir = "/40260280_ObjectOriented/40260280_SavedState.csv"; // location of file to be written to

		try {
			PrintWriter statePrint = new PrintWriter(stateDir);
			statePrint.println(this.getOwner());
			statePrint.println(this.getMaxItems());
			statePrint.println(this.getStatus());
			statePrint.println(Arrays.toString(this.getStock()));
			statePrint.println(this.getTotalItemQty());
			statePrint.println(this.getTotalMoney());
			statePrint.println(Arrays.toString(this.getCoinsQty()));
			statePrint.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			{
			}
		}

	}

	/**
	 * reads the previous state of the vending machine from a csv file located at
	 * U:/ObjectOriented/40260280_SavedState.csv not functional
	 */
	public void readState() {
		String stateDir = "/40260280_ObjectOriented/40260280_SavedState.csv"; // location of file to be read
		int lineNumber = 0;

		try {

			File stateFile = new File(stateDir);
			Scanner stateScanner = new Scanner(stateFile);

			while (stateScanner.hasNextLine()) {
				lineNumber++;
				System.out.println(stateScanner.nextLine());
			}

			if (lineNumber == 1) {
				this.setOwner(stateScanner.nextLine());
			} else if (lineNumber == 2) {
				this.setMaxItems(Integer.parseInt(stateScanner.nextLine()));
			} else if (lineNumber == 3) {
				if (stateScanner.equals("Operating in Vending Mode")) {
					this.setStatus(Status.VENDING_MODE);
				} else {
					this.setStatus(Status.SERVICE_MODE);
				}
//			} else if (lineNumber == 4) {
//				this.setStock(stateScanner.next());
			} else if (lineNumber == 5) {
				this.setTotalItemQty(Integer.parseInt(stateScanner.nextLine()));
			} else if (lineNumber == 6) {
				this.setTotalMoney(Integer.parseInt(stateScanner.nextLine()));
//			} else if (lineNumber == 7) {
//				this.setCoinsQty(stateScanner.nextLine());
			} else {
				return;
			}

			// System.out.println("Contents of " + newFile + " have been successfully
			// read");
			stateScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
