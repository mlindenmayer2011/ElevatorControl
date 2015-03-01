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
	private ArrayList<Elevator> elevators = new ArrayList<>();
	private Driver mainDriver;

	public Building(int driver) {
		loadLevels();
		
		if (driver == 0) {
			mainDriver = new BaseDriver(floors.size() - 1, 1);
		} else {
			mainDriver = new SmartDriver(floors.size() - 1, 1);
		}

		//setLayout(new BorderLayout());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//add(mainElevator);
		//setSize(500, 700);
		//setVisible(true);
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
			int numElevators = stdIn.nextInt();
			for (int i = 0; i < numFloors; i++) {
				Floor floor = new Floor();
				floors.add(floor);
			}
			for (int i = 0; i < numElevators; i++) {
				Elevator ele = new Elevator(this,numFloors,10);
				elevators.add(ele);
				System.out.println("ELEVATOR ADDED");
			}
			int pplCount = 0;
			while (stdIn.hasNextLine()) {
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
		while (time < 60 * 60 * 24 + 100) { // time in the workday
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
			runCost = 0;
			ArrayList<Floor> tempFloors = new ArrayList<>();
			for(Floor f : floors){
				tempFloors.add(new Floor(f.up, f.down));
			}
			for(Elevator e : elevators){
				if(e.getWaitTime() == 0){
					ArrayList<Person> unloadedPassengers = new ArrayList<>();
					unloadedPassengers = e.unloadPassengers();
					for (Person p : unloadedPassengers) {
						pplServiced ++;
						totalWaitTime += time - p.getTime();
						System.out.println("(" + time + ")Passenger Unloaded!! Start Floor: "
								+ p.getStart() +" End Floor: "+ e.getFloor() + " Wait: " + (time - p.getTime()));
					}
					//System.out.println("ELE : " + e.getFloor());
					int pplOn = floors.get(e.getFloor()).putOnElevator(e);
					if(pplOn != 0 || unloadedPassengers.size() != 0){
						e.setWaitTime(pplOn * 5 + unloadedPassengers.size() * 5);
					} else {
						mainDriver.moveElevator(tempFloors, e);
					}
				} else {
					e.reduceWait();
				}
				runCost += e.getRunCost();
			}

			time++; // increment the time
		}
		float finalResult = totalWaitTime / pplServiced;
		System.out.println("AVG WAIT : " + finalResult + "(" + pplServiced
				+ "," + totalWaitTime + ") Run Cost : " + runCost);
	}

	public Elevator getElevator(int index) { return elevators.get(index); }
	public ArrayList<Floor> getFloors() { return floors; }
	
	public static void main(String[] args) {

		//Building b = new Building();

	}

}
