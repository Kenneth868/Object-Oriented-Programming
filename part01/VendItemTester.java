package part01;

public class VendItemTester {

	public static void main(String[] args) {
		
		/*initialise without quantity
		VendItem item1 = new VendItem("Twix", 0.50);
		System.out.println(item1);*/
		
		/*initialise with incorrect constructor
		VendItem item1 = new VendItem(20, "20");
		System.out.println(item1);*/
		
		/*initialise with valid quantity
		VendItem item1 = new VendItem("Twix", 0.50, 6);
		System.out.println(item1);*/
		
		/*quantity less than 0
		VendItem item1 = new VendItem("Twix", 0.50, -1);
		System.out.println(item1);*/
		
		/*quantity greater than 10
		VendItem item1 = new VendItem("Twix", 0.50, 15);
		System.out.println(item1);*/
		
		/*price greater than 2.00
		VendItem item1 = new VendItem("Twix", 9.00, 5);
		System.out.println(item1);*/
		
		/*price less than 0
		VendItem item1 = new VendItem("Twix", -5.00, 5);
		System.out.println(item1);*/
		
		/*item ID increments
		VendItem item1 = new VendItem("Fanta", 1.00, 5);
		VendItem item2 = new VendItem("Mars", 0.50, 5);
		System.out.println(item1 + "" + item2);*/
		
		/*item ID increments even when not called/printed
		VendItem item1 = new VendItem("Fanta", 1.00, 5);
		VendItem item2 = new VendItem("Orange", 0.50, 9);
		VendItem item3 = new VendItem("Mars", 0.50, 5);
		System.out.println(item1 + "" + item3);*/
		
		/*restock with valid value
		VendItem item1 = new VendItem("Fanta", 1.00, 5);
		System.out.println(item1.getName() + " quantity before restocking: " + item1.getQty());
		item1.restock(4);
		System.out.println(item1.getName() + " quantity after restocking: " + item1.getQty());*/
		
		/*restock with invalid value
		VendItem item1 = new VendItem("Fanta", 1.00, 5);
		System.out.println(item1.getName() + " quantity before restocking: " + item1.getQty());
		item1.restock(7);
		System.out.println(item1.getName() + " quantity after restocking: " + item1.getQty());*/
		
		/*restock with minus value
		VendItem item1 = new VendItem("Fanta", 1.00, 5);
		System.out.println(item1.getName() + " quantity before restocking: " + item1.getQty());
		item1.restock(-7);
		System.out.println(item1.getName() + " quantity after restocking: " + item1.getQty());*/
		
		/*testing deliver() presents the correct message
		VendItem item1 = new VendItem("Fanta", 1.00, 5);
		System.out.println(item1.deliver());*/
		
		/*initialise with a price that does not divide by 0.05
		VendItem item1 = new VendItem("Fanta", 0.47, 5);
		System.out.println(item1);*/
		
	}

}
