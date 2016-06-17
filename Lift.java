package carpark;

// LIFT
// Written by James Rogers 542046
// An extension to Section which adds the ability to move the position of
// the lift, and to manage incoming and outgoing cars 
public class Lift extends Section {
	
	// Car and position state values
	public enum State { EMPTY, INCOMING, OUTGOING }
	public enum Position { LOWERED, RAISED }
	
	// The state of the lift in regards to car storage
	private State state;
	
	// The position of the lift
	private Position position;
	
	// Variable to monitor space within the carpark system
	private int vacancyCount;
	
	// Constructor
	// Creates an empty lift at the lowered position
	// Carpark has vacancies equal to the number of sections
	public Lift(int number) {
		super(number);
		state = State.EMPTY;
		position = Position.LOWERED;
		vacancyCount = Param.SECTIONS;
	}


	// Remove Car - Overrides the method in Section
	// Waits until the lift is raised and has an incoming car, then attempts
	// to add the car to the next section and remove it from the lift
	public synchronized void removeCar(Section nextSection) {
		while(state != State.INCOMING || position != Position.RAISED) {
			try {
				wait();
			} catch(InterruptedException e) {}
		}
		
		String leaveMessage = "leaves the lift";
		nextSection.addCar(car, leaveMessage);
		car = null;
		state = State.EMPTY;
		notifyAll();
	}
	
	
	// Remove Car - Overrides the method in Section
	// Waits until the lift is empty and raised, then tows the car into
	// the lift, and automatically lowers the lift
	public synchronized void addCar(Car newCar, String leaveMessage) {
		while(state != State.EMPTY || position != Position.RAISED) {
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
		
		System.out.println(newCar.toString() + "enters lift to go down");
		car = newCar;
		state = State.OUTGOING;
		
		lowerLift();
				
		notifyAll();
	}
	
	
	// Remove Outgoing Car
	// Additional method that allows for outgoing cars to be removed
	// Waits until the lift is lowered, and removes the car, updates the system
	public synchronized void removeOutgoingCar() {
		while(state != State.OUTGOING || position != Position.LOWERED) {
			try {
				wait();
			} catch(InterruptedException e) {}
		}
		
		System.out.println(car.toString() + "departs");
		car = null;
		state = State.EMPTY;
		vacancyCount++;
		notifyAll();
	}
	
	// Add Incoming Car
	// Waits until the lift is empty, lowered and carpark spaces are available,
	// then adds the incoming car and raises the lift
	public synchronized void addIncomingCar(Car newCar) {
		while(state != State.EMPTY || position != Position.LOWERED
												|| vacancyCount == 0) {
			try {
				wait();
			} catch(InterruptedException e) {}
		}
		
		car = newCar;
		state = State.INCOMING;
		vacancyCount--;
		System.out.println(car.toString() + "enters lift to go up");
		
		raiseLift();
		
		notifyAll();
	}
	
	// Change Position
	// If the lift is empty, change lift position after a delay
	public synchronized void changePosition() {
		
		if(state == State.EMPTY) {
			if(position == Position.LOWERED) {
				raiseLift();
				System.out.println("lift goes up");
			} else if(position == Position.RAISED) {
				lowerLift();
				System.out.println("lift goes down");
			}
		}
		notifyAll();
		
	}
	
	// isRaised
	// Returns true if the lift is raised
	public boolean isRaised() {
		if(position == Position.RAISED) {
			return true;
		} else {
			return false;
		}
	}
	
	// Raise Lift
	// Raises the lift after sleeping for the operating time
	private void raiseLift() {
		try {
			Thread.sleep(Param.OPERATE_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		position = Position.RAISED;
	}
	
	// Lower Lift
	// Lowers the lift after sleeping for the operating time
	private void lowerLift() {
		try {
			Thread.sleep(Param.OPERATE_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		position = Position.LOWERED;
	}

}