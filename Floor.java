package elevatorSimulator;

import java.util.ArrayList;

//import java.util.ArrayList;

public class Floor {

	// Used to compare to persons direction
	public static final int DOWN = -1;
	public static final int IDLE = 0;
	public static final int UP = 1;
	// Represent buttons
	boolean up = false, down = false;

	private ArrayList<Person> m_people = new ArrayList<>();

	// Constructor
	Floor() {
	}

	// Adds person to the floor and updates buttons
	public void addPerson(Person p) {
		m_people.add(p);
		setDir(p);
	}

	// Later change to binary search
	// Moves person to elevator
	public int putOnElevator(Elevator elevator) {
		int changed = 0;
		for (int i = 0; i < m_people.size(); i++) {
			if (m_people.get(i).getStart() == elevator.getFloor()) {
				// Determines if person can fit on elevator
				if (elevator.getWeight() + m_people.get(i).getWeight() <= elevator
						.getCapacity()) {
					elevator.add(m_people.get(i));
					m_people.remove(i--);
					changed++;
				}
			}
		}

		// Resets button to not pressed
		up = false;
		down = false;

		// Rechecks all people left on floor and updates button
		for (int i = 0; i < m_people.size(); i++) {
			setDir(m_people.get(i));
		}

		return changed;
	}

	// sets buttons of floor
	private void setDir(Person p) {
		if (p.getDir() == UP) {
			up = true;
		} else {
			down = true;
		}
	}
	
	public int getStatus() { 
		if(up = true) { return UP; }
		else if(down = true) { return DOWN; }
		else { return IDLE; }
	}
}