package elevatorSimulator;

import java.util.ArrayList;
 
public class Elevator {
 
	final int DOWN = -1, UP = 1;
 
    private boolean[] m_floorControls;// Tells if person needs to get off on floor
    private int m_topFloor;
    private int m_floor = 0; // Floor elevator is currently on
    private int m_weight = 0; // Current weight
    private int m_capacity; // Max weight allowed on elevator
    private int m_direction;
    private ArrayList<Person> m_passengers;
 
    // Constructor
    public Elevator (int numFloors, int capacity) {
        m_topFloor = numFloors;
        m_floorControls = new boolean[ m_topFloor ];
        m_capacity = capacity;
    }
 
    // Accessor methods //////////////////////////////
    public int getTopFloor()  { return m_topFloor; }
    public int getFloor()     { return m_floor; }
    public int getWeight()    { return m_weight; }
    public int getCapacity()  { return m_capacity; }
    public int getDirection() { return m_direction; }
    //////////////////////////////////////////////////
    
    public static void nextFloor () {
        if (m_direction == UP && m_floor < m_topFloor) {
            floor++;
        } else if (direction == DOWN && floor > 0) {
            floor--;
        } else {
            switch (direction) {
                case UP: direction = DOWN; break;
                case DOWN: direction = UP; break;
            }
        }
        totalFloors++;
    }
 
    public void add () {
        building.get(m_floor).putOnElevator( m_direction );
    }
 
    public void removePassenger (int i) {
        m_passengers.remove(i);
    }
 
    public ArrayList<Person> unloadPassengers () {
        ArrayList<Person> p;
        for (int i = 0; i < m_passengers.length; i++) {
            if (m_passengers.get(i) == m_floor) {
                p.add(m_passengers.get(i));
                removePassenger(i);
            }
        }
        return p;
    } 
}