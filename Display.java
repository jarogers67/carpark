package carpark;

// DISPLAY
// Written by James Rogers 542046
// Can be used to display a simple graphical representation of the system
public class Display extends Thread {
	
	// List of sections to be displayed
	private Section[] sections;
	
	// Constructor
	public Display(Section[] sections) {
		this.sections = sections;
	}
	
	// Run
	// Sleep for 1s then display the state
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			displayState();		
		}
	}
	
	// Prints a rudimentary display of the system when called
	public synchronized void displayState() {
		
		int n = Param.SECTIONS;
		
		String liftDisplay = sections[0].displaySection();
		
		String up;
		String down;
		if(((Lift)sections[0]).isRaised()) {
			up = liftDisplay;
			down = "T";
		} else {
			up = " ";
			down = liftDisplay;
		}
		
		System.out.println();
		System.out.print(up);
		for(int i = 1; i <= n; i++) {
			String sectionDisplay = sections[i].displaySection();
			System.out.print(sectionDisplay);
		}
		System.out.println(up);
		
		System.out.print(down);
		for(int i = 1; i <= n; i++) {
			System.out.print(" ");
		}
		System.out.println(down);
		System.out.println();
		
	}

}