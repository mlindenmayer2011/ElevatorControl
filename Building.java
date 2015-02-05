package elevatorSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Building {
	
	private int time = 0;
	private int pplServiced = 0;
	private int totalWaitTime = 0;
	
	private ArrayList<Person> personQueue = new ArrayList<>;
	

	public static void main(String[] args) {
	
		File file = new File("data.txt");
		
		ArrayList<Floor> floors = new ArrayList<>();
		
		// Checks for correct file name.
		try {
			
			Scanner stdIn = new Scanner(file);
			
			// Takes first number of file.
			// Which correlates to number of floors.
			// Then creates floors and adds them to floors list.
			for(int i = 0; i < stdIn.nextInt(); i++) {
				Floor floor = new Floor();
				floors.add(floor);
			}
			//for not to accommodate the current file
			stdIn.nextInt();
			stdIn.nextInt();
			while(stdIn.hasNext()){
				//run through the rest of the file
				final int startFloor = stdIn.nextInt();
				final int endFloor = stdIn.nextInt();
				final int startTime = stdIn.nextInt();
				Person newPerson = new Person(startFloor,endFloor,startTime,0);
				personQueue.add(newPerson);
			}
			stdIn.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void runLoop(ArrayList<Floor> floors) {
		//create a loop that will loop through the day
		Elevator mainElevator = new Elevator(floors.size(),10);
		while(time < 60*60*8) { //time in the workday
			//check the personQueue to spawn a person
			for(int i=0;i<personQueue.size();i++){
				Person p = personQueue.objectAtIndex(i);
				if(p.getTime() == time){
					floor.addPerson(p);
				}
			}
			//use the current floor to move ppl on the elevator
			final int currentFloor = mainElevator.getFloor();
			boolean elevatorStatusChanged = false;
			ArrayList<Person> unloadedPassengers = new ArrayList<>;
			//remove people from the elevator first
			unloadedPassengers = mainElevator.unloadPassengers();
			
			for(int i=0;i<unloadPassengers.size();i++){
				Person p = unloadedPassengers.objectAtIndex(i);
				pplServiced ++;
				totalWaitTime += time - p.getTime();
			}
			
			
			//get a return value here so we can check if someone actually got on the elevator???
			floors.objectAtIndex(currentFloor).putOnElevator(mainElevator);
			
			if(elevatorStatusChanged){
				
			} else {
				mainElevator.nextFloor;
			}
			
			time ++; //increment the time
		}
	}
}
