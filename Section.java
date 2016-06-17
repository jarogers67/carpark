package carpark;

// SECTION
// Written by James Rogers 542046
// A generic numbered car park position which may contain a car
public class Section {

	// The id of the section
	public final int number;
	
	// Reference to the car that may be present
	protected Car car;
	
	// Constructor
	// Build a new empty section with the given number
	public Section(int number) {
		this.number = number;
		car = null;
	}
	
	// Remove Car
	// Waits while there is no car present, then once car is available
	// adds the car to the given next section and removes it from this section
	public synchronized void removeCar(Section nextSection) {
		while(car == null) {
			try {
				wait();
			} catch(InterruptedException e) {}
		}
		
		String leaveMessage = "leaves section " + number;
		nextSection.addCar(car, leaveMessage);
		car = null;
		notifyAll();
	}
	
	// Add Car
	// Waits until section is free, then waits for the towing time and
	// adds the car to the section
	public synchronized void addCar(Car newCar, String leaveMessage) {
		while(car != null) {
			try {
				wait();
			} catch(InterruptedException e) {}
		}
		
		System.out.println(newCar.toString() + leaveMessage);
		
		try {
			Thread.sleep(Param.TOWING_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(newCar.toString() + "enters section " + number);
		car = newCar;
		notifyAll();
	}
	
	// Display Section
	// Generates a one character string representing the car id at the section
	public String displaySection() {
		String display;
		if(car != null) {
			display = "" + (car.id % 10);
		} else {
			display = "_";
		}
		return display;
	}
}