
import java.util.ArrayList;

public interface Driver {
	public static final int DOWN = -1;
	public static final int IDLE = 0;
	public static final int UP = 1;

	public void moveElevator(ArrayList<Floor> floors, Elevator elevator);
}
