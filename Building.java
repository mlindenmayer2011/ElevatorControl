package elevatorSimulator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Building extends JFrame {

	private int time = 0;
	private int pplServiced = 0;
	private int totalWaitTime = 0;
	private int runCost = 0;

	private ArrayList<Person> personQueue = new ArrayList<>();
	private ArrayList<Floor> floors = new ArrayList<>();
	private Elevator mainElevator;
	private Driver mainDriver;

	public Building() {
		loadLevels();

		mainElevator = new Elevator(this, floors.size() - 1, 10);
		mainDriver = new SmartDriver(floors.size() - 1, 1);

		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(mainElevator);
		setSize(500, 700);
		setVisible(true);
		runLoop();
	}

	public void loadLevels() {
		File file = new File("data.txt");

		try {
			Scanner stdIn = new Scanner(file);
			// Takes first number of file.
			// Which correlates to number of floors.
			// Then creates floors and adds them to floors list.
			int numFloors = stdIn.nextInt();
			for (int i = 0; i < numFloors; i++) {
				Floor floor = new Floor();
				floors.add(floor);
			}
			int pplCount = 0;
			while (pplCount <= 371) {
				// run through the rest of the file
				final int startFloor = stdIn.nextInt();
				final int endFloor = stdIn.nextInt();
				final int startTime = stdIn.nextInt();
				Person newPerson = new Person(startFloor, endFloor, startTime, 1);
				personQueue.add(newPerson);
				pplCount++;
			}
			stdIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void runLoop() {

		// create a loop that will loop through the day
		int waitTime = 0;
		while (time < 60 * 60 * 8 + 100) { // time in the workday
			// System.out.println("Time : " + time);
			// check the personQueue to spawn a person
			for (int i = 0; i < personQueue.size(); i++) {
				Person p = personQueue.get(i);
				if (p.getTime() == time) {
					floors.get(p.getStart()).addPerson(p);
					// System.out.println("("+time+")Added Person on Floor : " +
					// p.getStart());
				}
			}
			// use the current floor to move ppl on the elevator
			final int currentFloor = mainElevator.getFloor();
			boolean elevatorStatusChanged = false;
			ArrayList<Person> unloadedPassengers = new ArrayList<>();
			// remove people from the elevator first
			if (waitTime == 0) {
				unloadedPassengers = mainElevator.unloadPassengers();
				int pplOff = 0;
				for (int i = 0; i < unloadedPassengers.size(); i++) {
					Person p = unloadedPassengers.get(i);
					pplServiced++;
					totalWaitTime += time - p.getTime();
					elevatorStatusChanged = true;
					pplOff++;
					System.out.println("(" + time + ")Passenger Unloaded!! Start Floor: "
					+ p.getStart() +" End Floor: "+ currentFloor + " Wait: " + (time - p.getTime()));
				}

				// get a return value here so we can check if someone actually got
				// on the elevator???
				int pplOn = 0;
				if ((pplOn = floors.get(currentFloor).putOnElevator(mainElevator)) > 0) {
					elevatorStatusChanged = true;
				}
				if (elevatorStatusChanged) {
					// do nothing right now
					// System.out.println("("+time+")Floor " + currentFloor +
					// " : Unload : " + unloadedPassengers.size());
					waitTime = pplOn * 5 + pplOff * 5;
				} else {
					waitTime = 4;
					mainDriver.moveElevator(this);
					runCost++;
					// System.out.println("Elevator Floor : " +
					// mainElevator.getFloor());
				}

			} else {
				waitTime--;
			}

			time++; // increment the time
		}
		float finalResult = totalWaitTime / pplServiced;
		System.out.println("AVG WAIT : " + finalResult + "(" + pplServiced
				+ "," + totalWaitTime + ") Run Cost : " + runCost);
	}

	public Elevator getElevator() { return mainElevator; }
	public ArrayList<Floor> getFloors() { return floors; }
	
	public static void main(String[] args) {

		Building b = new Building();

	}

}
