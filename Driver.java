package elevatorSimulator;

public interface Driver {
	public static final int UP = 1;
	public static final int DOWN = -1;

	public void moveElevator(Building building);
}
