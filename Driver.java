package elevatorSimulator;

import java.util.ArrayList;

public class Driver {
	
	final static int DOWN = -1;
	static final int UP = 1;
	
	private static int m_numFloors = 0;
	private static int m_numElevators = 0;
	
	//private int m_elevators[];
	
	Driver (int floorCount, int elevatorCount){
		m_numFloors = floorCount;
		m_numElevators = elevatorCount;
	}
	
	//here is the algorithm for the base line model
	public static void moveElevator(Elevator elevator){
		if (elevator.getDirection() == UP && elevator.getFloor() < m_numFloors) {
            elevator.moveUp();
        } else if (elevator.getDirection() == DOWN && elevator.getFloor() > 0) {
            elevator.moveDown();
        } else {
            elevator.switchDirection();
        }
	}
}
