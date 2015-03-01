package elevatorSimulator;

import java.util.ArrayList;

public class SmartDriver implements Driver {

	private final int m_numFloors;
	public static final int DOWN = -1;
	public static final int IDLE = 0;
	public static final int UP = 1;

	SmartDriver(int floorCount, int elevatorCount) {
		m_numFloors = floorCount;
	}

	public void moveElevator(Building building) {
		
		Elevator elevator = building.getElevator();
		ArrayList<Floor> floors = building.getFloors();
		
		// In case elevator is on top floor or bottom floor
		if (elevator.getDirection() == UP && elevator.getFloor() == m_numFloors) {
			elevator.switchDirection();
		} else if (elevator.getDirection() == DOWN && elevator.getFloor() == 0) {
			elevator.switchDirection();
		}
		
		// Checks to see if anyone is on elevator
	    if (elevator.getWeight() != 0) {
	    	// For going up if elevator is already going up
	    	if (elevator.getDirection() == UP) {
	    		elevator.moveUp();
			// For going down if elevator is already going down
	    	} else if (elevator.getDirection() == DOWN) {			
	    		elevator.moveDown();
	    	}
	    } else {
	    	
	    	int belowFloors = 0, aboveFloors = 0;
	    	boolean topFloorCheck = false, firstCheck = false;
	    	
	    	
	    	for (int i = 0 ; i < m_numFloors; i++) {
	    		if (floors.get(i).getDownStatus() || floors.get(i).getUpStatus()) {
	    			if (i < elevator.getFloor()) {
	    				belowFloors++;
	    			} else {
	    				aboveFloors++;
	    			}
	    		}
	    	}
	    	
	    	// Checks if elevator is in bottom half of building and if up
	    	// is pressed on first floor
	    	if (floors.get(0).getUpStatus() && elevator.getFloor() < (m_numFloors/2)){
	    		firstCheck = true;
	    	}
	    	
	    	// Checks if elevator is in top half of building and if down
	    	// is pressed on top floor
	    	if (floors.get(m_numFloors).getDownStatus() && elevator.getFloor() >= (m_numFloors/2)){
	    		topFloorCheck = true;
	    	}
	    	
	    	if (belowFloors == 0 && aboveFloors == 0) {
	    		//elevator.makeIdle();
	    	} else if (firstCheck) {
	    		elevator.moveDown();
	    	} else if (topFloorCheck) {
	    		elevator.moveUp();
	    	} else if (belowFloors >= aboveFloors) {
	    		elevator.moveDown();
	    	} else {
	    		elevator.moveUp();
	    	}
	    	
		}
	}
	
}
