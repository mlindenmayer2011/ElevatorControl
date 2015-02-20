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

	// here is the algorithm for the base line model
	public void moveElevator(Building building) {
		
		Elevator elevator = building.getElevator();
		ArrayList<Floor> floors = building.getFloors();
		
		//In case elevator is on top floor or bottom floor
		if (elevator.getDirection() == UP && elevator.getFloor() == m_numFloors) {
			elevator.switchDirection();
		} else if (elevator.getDirection() == DOWN && elevator.getFloor() == 0) {
			elevator.switchDirection();
		}
		
		//For going up
		if (elevator.getDirection() == UP) {			
			for (int i = elevator.getFloor() + 1; i < floors.size(); i++) {
				if (floors.get(i).getStatus() == UP) {
					elevator.moveUp();
				}
			}
		//For going down
		} else if (elevator.getDirection() == DOWN) {
			for (int i = elevator.getFloor() - 1; i > floors.size(); i--) {
				if (floors.get(i).getStatus() == DOWN) {
					elevator.moveDown();
				}
			}
		} else {
			elevator.makeIdle();
		}
	}
}