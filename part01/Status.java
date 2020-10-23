package part01;

public enum Status {
	VENDING_MODE(1), SERVICE_MODE(2);
	
	private String statusNames[] = {"Operating in Vending Mode", "Operating in Service Mode"};
	private int statusValue;
	
	private Status(int value) {
		statusValue = value;
	}
	
	public int getValue() {
		return statusValue;
	}
	
	public String getStatus() {
		return statusNames[statusValue-1];
	}
	
	@Override
	public String toString() {
		return getStatus();
	}
	
}
