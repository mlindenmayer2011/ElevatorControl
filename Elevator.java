package elevatorSimulator;

import java.util.ArrayList;
 
public class Elevator {
 
	final static int DOWN = -1;

	static final int UP = 1;
 
    private boolean[] m_floorControls;// Tells if person needs to get off on floor
    private static int m_topFloor;
    private static int m_floor = 0; // Floor elevator is currently on
    private static int m_weight = 0; // Current weight
    private static int m_capacity; // Max weight allowed on elevator
    private static int m_direction;
    private ArrayList<Person> m_passengers;
 
    // Constructor
    public Elevator (int numFloors, int capacity) {
        m_topFloor = numFloors;
        m_floorControls = new boolean[ m_topFloor ];
        m_capacity = capacity;
        m_passengers = new ArrayList<>();
        m_direction = UP;
    }
 
    // Accessor methods //////////////////////////////
    public int getTopFloor()  { return m_topFloor; }
    public int getFloor()     { return m_floor; }
    public int getWeight()    { return m_weight; }
    public int getCapacity()  { return m_capacity; }
    public int getDirection() { return m_direction; }
    //////////////////////////////////////////////////
    
    public static void moveUp () {
        m_floor ++;
    }
	
	public static void moveDown () {
        m_floor --;
    }
	
	public static void switchDirection() {
		if(m_direction == UP){
			m_direction = DOWN;
		} else {
			m_direction = UP;
		}
	}
 
    public void add (Person p) {
    	m_passengers.add(p);
    }
 
    public void removePassenger (int i) {
        m_passengers.remove(i);
    }
 
    public ArrayList<Person> unloadPassengers () {
        ArrayList<Person> p = new ArrayList<>();
        for (int i = 0; i < m_passengers.size(); i++) {
            if (m_passengers.get(i).getDest() == m_floor) {
                p.add(m_passengers.get(i));
                removePassenger(i);
            }
        }
        return p;
    } 
}