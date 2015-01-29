import java.util.ArrayList;

public class Floor {

	private boolean up = false, down = false;
	
	private ArrayList<Person> people;
	
	// Constructor
	Floor( Elevator elevator ) { }
	
	// Adds person to the floor
	public void addPerson( Person p ) {
		people.add(p);
		setDir();
	}
	
	// Later change to binary search
	// Moves person to elevator
	public void putOnElevator( boolean dir ) {
		for(int i = 0; i < people.length(); i++) {
			if( people.dir == dir) {
				if( elevator.weight + people.get(i) <= elevator.limit ) { // Determines if person can fit on elevator
					elevator.add( people.get(i) );
					people.remove(i);
				}
			}
		}
		
		up = false;
		down = false;
		
		// checks all people on floor and updates button
		for(int i = 0; i < people.length(); i++) {
			setDir( people.get(i) );
		}
	}
	
	// sets buttons of floor
	private void setDir(Person p) {
		if( p.dir == UP ) { up = true; }
		else { down = true; }
	}
	
}
