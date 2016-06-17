package carpark;

// CONSUMER
// Written by James Rogers 542046
// Thread that removes outgoing cars from the lift
public class Consumer extends Thread {

	// The lift to remove cars from
	private Lift lift;
	
	// Constructor
	public Consumer(Lift lift) {
		this.lift = lift;
	}

	// Run
	// While running, the thread waits for a bounded random time, then attempts
	// to remove an outgoing car from the lift
	public void run() {
		try {
			while(true) {
				Thread.sleep(Param.departureLapse());
				lift.removeOutgoingCar();
			}
		} catch(InterruptedException e) {
			System.out.println("Consumer Interrupted");
		}
	}
	
}