
import java.util.ArrayList;

public class BaseDriver implements Driver {

	private final int m_numFloors;

	// private int m_elevators[];

	BaseDriver(int floorCount, int elevatorCount) {
		m_numFloors = floorCount;
	}

	// here is the algorithm for the base line model
	@Override
	public void moveElevator(ArrayList<Floor> floors, Elevator elevator) {
		
		
		if (elevator.getDirection() == UP && elevator.getFloor() < m_numFloors) {
			elevator.moveUp();
		} else if (elevator.getDirection() == DOWN && elevator.getFloor() > 0) {
			elevator.moveDown();
		} else {
			elevator.switchDirection();
		}
	}
}
