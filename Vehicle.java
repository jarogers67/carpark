package carpark;

// VEHICLE
// Written by James Rogers 542046
// Thread that moves cars from one section to the next
public class Vehicle extends Thread {

	// The two sections that the thread has access to
	private Section a;
	private Section b;

	// Constructor
	public Vehicle(Section a, Section b) {
		this.a = a;
		this.b = b;
	}

	// Run
	// Continue moving cars from section a to section b while the thread runs
	public void run() {
		while(true) {
			a.removeCar(b);
		}
	}

}