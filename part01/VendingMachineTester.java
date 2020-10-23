package part01;

import java.util.Arrays;

public class VendingMachineTester {

	public static void main(String[] args) {
		VendingMachine vm1 = new VendingMachine("Bill", 2);
		VendItem item1 = new VendItem("Twix", 0.50, 5);
		VendItem item2 = new VendItem("Mars", 0.50, 5);
		VendItem item3 = new VendItem("Milky Way", 0.50, 5);
		System.out.println(vm1.addNewItem(item1));
		System.out.println(vm1.addNewItem(item2));
		System.out.println(vm1.addNewItem(item3));
		
		System.out.println(Arrays.toString(vm1.listItems()) + "\n");
		
		for (String vendItem: vm1.listItems()) {
			System.out.println(vendItem);
		}
	}

}
