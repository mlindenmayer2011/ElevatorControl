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
	    if (elevator.getWeight() == 0) {
	    	elevator.makeIdle();
		}
		
		// For going up if elevator is already going up
		if (elevator.getDirection() == UP) {
			elevator.moveUp();
			
		// For going down if elevator is already going down
		} else if (elevator.getDirection() == DOWN) {			
			elevator.moveDown();
		} else {
			int floorsUp = 0;
			int floorsDown = 0;
			
			//////////////////////Doesn't Work//////////////////////////////////////////
	
			// Determines closest floor upwards
			for (int i = elevator.getFloor() + 1; i <= m_numFloors; i++) {
				if (floors.get(i).getUpStatus() && floorsUp == 0) {
					floorsUp = i - elevator.getFloor(); // This breaks program
				}
			}
			
			// Determines closest floor downwards
			for (int i = elevator.getFloor() - 1; i >= 0; i--) {
				if (floors.get(i).getDownStatus() && floorsDown == 0) {
					floorsDown = elevator.getFloor() - i;
				}
			}
			//////////////////////////////////////////////////////////////////////////
			
			if (floorsUp == 0 && floorsDown == 0){
				// Do nothing
			// Moves up if closest floor is upwards or same distance as closest floor downwards
		    } else if (floorsUp <= floorsDown) {
				elevator.moveUp();
			// Moves down if closest floor is downwards
			} else if (floorsDown < floorsUp){
				elevator.moveDown();
			}
		}
	}

}