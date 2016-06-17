package carpark;

// PRODUCER
// Written by James Rogers 542046
// Thread that creates incoming cars to add to the lift
public class Producer extends Thread {

	// The lift to provide cars to
	private Lift lift;
	
	// Constructor
	public Producer(Lift lift) {
		this.lift = lift;
	}
	
	// Run
	// While running, the thread waits for a bounded random time, then creates
	// a new car and attempts to add it to the lift
	public void run() {
		try {
			while(true) {
				Thread.sleep(Param.arrivalLapse());
				lift.addIncomingCar(Car.getNewCar());
			}
		} catch(InterruptedException e) {
			System.out.println("Producer Interrupted");
		}
	}

}