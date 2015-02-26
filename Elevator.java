package elevatorSimulator;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import java.util.ArrayList;

public class Elevator extends Container {
	public static final int DOWN = -1;
	public static final int IDLE = 0;
	public static final int UP = 1;

	// Elevator Structure
	private final int m_topFloor; // top floor of building.
	private final int m_maxCapacity; // Max weight allowed.

	// Elevator Status
	private int m_floor = 0; // Current Position.
	private int m_weight = 0; // Current Weight.
	private int m_direction; // UP, DOWN, STOPPED;
	private int m_waitTime; // Delay (ms) when arriving at floor.
	private ArrayList<Person> m_passengers;

	// Canvas
	ElevatorCanvas m_canvas;

	Building parent;

	// Constructor
	public Elevator(Building b, int numFloors, int capacity) {
		parent = b;
		m_topFloor = numFloors - 1;
		m_maxCapacity = capacity;
		m_passengers = new ArrayList<Person>();
		m_direction = UP;
		setLayout(new CardLayout());
		m_canvas = new ElevatorCanvas(this, numFloors, 20, 100);
		add(m_canvas);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		parent.repaint();

	}

	// Accessor methods //////////////////////////////
	public int getTopFloor() {
		return m_topFloor;
	}

	public int getFloor() {
		return m_floor;
	}

	public int getWeight() {
		return m_weight;
	}

	public int getCapacity() {
		return m_maxCapacity;
	}

	public int getDirection() {
		return m_direction;
	}

	//////////////////////////////////////////////////

	public void moveUp() {
		m_floor++;
		m_canvas.addDestination(m_floor);
		m_direction = UP;
	}

	public void moveDown() {
		m_floor--;
		m_canvas.addDestination(m_floor);
		m_direction = DOWN;
	}

	public void switchDirection() {
		if (m_direction == UP) {
			m_direction = DOWN;
		} else {
			m_direction = UP;
		}
	}

	public void makeIdle() {
		m_direction = IDLE;
	}
	
	public void add(Person p) {
		m_weight += p.getWeight();
		m_passengers.add(p);
		m_canvas.addPerson();
	}

	public void removePassenger(int i) {
		m_weight -= m_passengers.get(i).getWeight();
		m_passengers.remove(i);
		m_canvas.removePerson();
	}

	public ArrayList<Person> unloadPassengers() {
		ArrayList<Person> p = new ArrayList<Person>();
		for (int i = 0; i < m_passengers.size(); i++) {
			if (m_passengers.get(i).getDest() == m_floor) {
				p.add(m_passengers.get(i));
				removePassenger(i);
				m_canvas.removePerson();
			}
		}
		return p;
	}
}
