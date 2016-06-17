package carpark;

// OPERATOR
// Written by James Rogers 542046
// Thread that changes the position of the lift periodically
public class Operator extends Thread {

	// The lift to manage
	private Lift lift;
	
	// Constructor
	public Operator(Lift lift) {
		this.lift = lift;
	}

	// Run
	// While running, the thread waits for a bounded random time, then attempts
	// to change the position of the lift
	public void run() {		
		try {
			while(true) {
				Thread.sleep(Param.operateLapse());
				lift.changePosition();
			}
		} catch(InterruptedException e) {
			System.out.println("Operator Interrupted");
		}

	}
}