package elevatorSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Building {
	
	private static int time = 0;
	private static int pplServiced = 0;
	private static int totalWaitTime = 0;
	private static int runCost = 0;
	
	private static ArrayList<Person> personQueue = new ArrayList<>();
	

	public static void main(String[] args) {
	
		File file = new File("data.txt");
		
		ArrayList<Floor> floors = new ArrayList<>();
		
		// Checks for correct file name.
		try {
			
			Scanner stdIn = new Scanner(file);
			
			// Takes first number of file.
			// Which correlates to number of floors.
			// Then creates floors and adds them to floors list.
			int numFloors = stdIn.nextInt();
			for(int i = 0; i < numFloors; i++) {
				Floor floor = new Floor();
				floors.add(floor);
			}
			int pplCount = 0;
			while(stdIn.hasNextLine()){
				//run through the rest of the file
				final int startFloor = stdIn.nextInt();
				final int endFloor = stdIn.nextInt();
				final int startTime = stdIn.nextInt();
				Person newPerson = new Person(startFloor,endFloor,startTime,1);
				personQueue.add(newPerson);
				pplCount ++;
			}
			stdIn.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		runLoop(floors);
	}
	
	public static void runLoop(ArrayList<Floor> floors) {
		
		//create a loop that will loop through the day
		Elevator mainElevator = new Elevator(floors.size()-1,10);
		Driver mainDriver = new Driver(floors.size()-1,1);
		int waitTime = 0;
		while(time < 60*60*8 + 100) { //time in the workday
			//System.out.println("Time : " + time);
			//check the personQueue to spawn a person
			for(int i=0;i<personQueue.size();i++){
				Person p = personQueue.get(i);
				if(p.getTime() == time){
					floors.get(p.getStart()).addPerson(p);
					//System.out.println("("+time+")Added Person on Floor : " + p.getStart());
				}
			}
			//use the current floor to move ppl on the elevator
			final int currentFloor = mainElevator.getFloor();
			boolean elevatorStatusChanged = false;
			ArrayList<Person> unloadedPassengers = new ArrayList<>();
			//remove people from the elevator first
			unloadedPassengers = mainElevator.unloadPassengers();
			int pplOff = 0;
			for(int i=0;i<unloadedPassengers.size();i++){
				Person p = unloadedPassengers.get(i);
				pplServiced ++;
				totalWaitTime += time - p.getTime();
				elevatorStatusChanged = true;
				pplOff ++;
				System.out.println("("+time+")Floor " + currentFloor + " : Passenger Unloaded!!");
			}
			
			
			//get a return value here so we can check if someone actually got on the elevator???
			int pplOn = 0;
			if((pplOn = floors.get(currentFloor).putOnElevator(mainElevator)) > 0){
				elevatorStatusChanged = true;
			}
			
			if(waitTime == 0){
			
				if(elevatorStatusChanged){
					//do nothing right now
					//System.out.println("("+time+")Floor " + currentFloor + " : Unload : " + unloadedPassengers.size());
					waitTime = pplOn * 5 + pplOff * 5;
				} else {
					waitTime = 4;
					mainDriver.moveElevator(mainElevator);
					runCost ++;
					//System.out.println("Elevator Floor : " + mainElevator.getFloor());
				}
			
			} else {
				waitTime --;
			}
			
			
			
			time ++; //increment the time
		}
		float finalResult = totalWaitTime/pplServiced;
		System.out.println("AVG WAIT : " + finalResult + "("+pplServiced+","+totalWaitTime+") Run Cost : " + runCost);
	}
}
