package part02;

public class VendItem implements Vendible {
	private static int nextId = 0;
	private int itemId;
	private String name;
	private double unitPrice;
	private int qtyAvailable;

	public VendItem(String name, double price) {
		setName(name);
		setPrice(price);
		this.itemId = nextId();
	}

	public VendItem(String name, double price, int qtyAvailable) {
		setName(name);
		setPrice(price);
		setQtyAvailable(qtyAvailable);
		this.itemId = nextId();
	}

	// getters
	public int getId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return unitPrice;
	}

	public int getQty() {
		return qtyAvailable;
	}

	// setters
	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(double price) { // if the price is higher than £2 or not divisible by 5p
		if (price > 2.00 || price * 100 % 5 != 0 || price < 0) {// 5 is 0.05 * 100 to avoid rounding errors with doubles
			//System.out.println("That is not a valid price.");
			return;
		} else
			this.unitPrice = price;
	}

	protected void setQtyAvailable(int qtyAvailable) {
		if (qtyAvailable <= 10 && qtyAvailable >= 0) {
			this.qtyAvailable = qtyAvailable;
		}

	}

	public static int nextId() {
		int itemId = nextId;
		nextId++;
		return nextId;
	}

	// restock quantity of items
	// enter value to be restocked - only if the quantity is less than the max
	// capacity (e.g. 20) AND if the value + qty is less than 20
	public boolean restock(int restockValue) {
		if (qtyAvailable < 10 && (restockValue + qtyAvailable) <= 10 && (restockValue + qtyAvailable) >= qtyAvailable) {
			qtyAvailable += restockValue;
			// System.out.println("Item has been restocked by: " + restockValue + ".");
			return true;
		}
		// System.out.println("Item could not be restocked.");
		return false;
	}

	/**
	 * converts the information held about the venditem to string
	 * allowing it to be printed as such
	 */
	public String toString() {
		String data = "";
		data += "ID: " + this.getId();
		data += "Item: " + this.getName();
		data += "Price: " + String.format("£%.2f", this.getPrice());
		data += "Quantity Available: " + this.getQty();
		return data; // prints the venditems details to a string
	}

	/**
	 * returns a string when a purchase is successful
	 */
	public String deliver() {
		String approvePurchase = "\nSuccessful Purchase \nYou have purchased " + this.getName() + " for: "
				+ String.format("£%.2f", this.getPrice()) + "\n";
		approvePurchase += "This item now has: " + this.getQty() + " available. \n";
		return approvePurchase;
	}

}
