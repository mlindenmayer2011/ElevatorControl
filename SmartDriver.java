package elevatorSimulator;

import java.util.ArrayList;

public class SmartDriver implements Driver {

	private final int m_numFloors;

	// private int m_elevators[];

	SmartDriver(int floorCount, int elevatorCount) {
		m_numFloors = floorCount;
	}

	// here is the algorithm for the base line model
	public void moveElevator(Building building) {
		
		Elevator elevator = building.getElevator();
		ArrayList<Floor> floors = building.getFloors();
		
		
		
		
		
		if (elevator.getDirection() == UP && elevator.getFloor() < m_numFloors) {
			elevator.moveUp();
		} else if (elevator.getDirection() == DOWN && elevator.getFloor() > 0) {
			elevator.moveDown();
		} else {
			elevator.switchDirection();
		}
	}
}
