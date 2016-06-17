package carpark;

// CAR
// Adapted from SWEN90004 sample code by James Rogers 542046
// Identifies the cars to be moved around the simulation
public class Car {

	// The car's id number
	public final int id;
	
	// The next id to be allocated
	private static int nextId = 1;

	// Constructs a new car with a given Id
	private Car(int id) {
		this.id = id;
	}

	// getNewCar
	// Get a new car instance with a unique Id
	public static Car getNewCar() {
		return new Car(nextId++);
	}

	// toString
	// Produces an identifying string for the car
	public String toString() {
		return "[" + id + "] ";
	}

}