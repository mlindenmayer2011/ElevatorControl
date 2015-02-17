package elevatorSimulator;

import java.util.ArrayList;

public class BaseDriver implements Driver {
	
	private final int m_numFloors;
    private final int m_numElevators;
	//private int m_elevators[];
	
	BaseDriver (int floorCount, int elevatorCount){
		m_numFloors = floorCount;
		m_numElevators = elevatorCount;
	}
	
	//here is the algorithm for the base line model
	@Override
	public void moveElevator(Elevator elevator){
		if (elevator.getDirection() == UP && elevator.getFloor() < m_numFloors) {
            elevator.moveUp();
        } else if (elevator.getDirection() == DOWN && elevator.getFloor() > 0) {
            elevator.moveDown();
        } else {
            elevator.switchDirection();
        }
	}
}
