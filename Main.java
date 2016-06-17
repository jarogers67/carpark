package carpark;

// MAIN
// Adapted from SWEN90004 sample code by James Rogers 542046
// The top-level component of the carpark system simulator
public class Main {

	// The driver of the lift/carpark system:
	// - create the components for the system; 
	// - start all of the processes; 
	// - supervise processes regularly to check all are alive.
	public static void main(String[] args) {
		
		boolean display = false;
		if(args.length == 1 && args[0].equals("display")) {
			display = true;
		}
		
		int n = Param.SECTIONS;

		// Create an array of n+1 sections, and construct them
		// Section 0 is a Lift - an extension of Section 
		Section[] sections = new Section[n + 1];
		Lift lift = new Lift(0);
		sections[0] = lift;
		for(int i = 1; i <= n; i++) {
			sections[i] = new Section(i);
		}
		
		// Create an array of n+1 vehicles linking sections, and construct them
		// The last vehicle must connect the last section back to the first
		Vehicle[] vehicles = new Vehicle[n + 1];
		for(int i = 0; i < n; i++) {
			vehicles[i] = new Vehicle(sections[i], sections[i + 1]);
		}
		vehicles[n] = new Vehicle(sections[n], sections[0]);
		
		// Start the vehicles
		for(int i = 0; i <= n; i++) {
			vehicles[i].start();
		}

		// Generate the producer, the consumer and the operator, and start them
		Producer producer = new Producer(lift);
		Consumer consumer = new Consumer(lift);
		Operator operator = new Operator(lift);
		
		producer.start();
		consumer.start();
		operator.start();
		
		if(display) {
			Display d = new Display(sections);
			d.start();
		}

		// THREAD STATUS CHECKING BELOW
		
		// Regularly check on the status of threads
		boolean vehiclesAlive = true;
		for (int i = 0; i <= n; i++) {
			vehiclesAlive = vehiclesAlive && vehicles[i].isAlive();
		}
		while (producer.isAlive() && consumer.isAlive()
				&& operator.isAlive() && vehiclesAlive) {
			try {
				Thread.sleep(Param.MAIN_INTERVAL);
			}
			catch (InterruptedException e) {
				System.out.println("Main was interrupted");
				break;
			}
			for (int i = 0; i <= n; i++) {
				vehiclesAlive = vehiclesAlive && vehicles[i].isAlive();
			}
		}

		// If some thread died, interrupt all other threads and halt
		producer.interrupt();    
		consumer.interrupt();
		operator.interrupt();

		for (int i = 0; i <= n; i++) {
			vehicles[i].interrupt();
		}

		System.out.println("Main terminates, all threads terminated");
		System.exit(0);
	}
}
