package elevatorSimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Building {

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
			stdIn.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
