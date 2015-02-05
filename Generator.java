package elevatorSimulator;

import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Generator {

    private final static Random RND = new Random();
    private final static String FILE = "data.txt";

    public static void main (String[] args) {
        PrintWriter out = null;
        int numFloors = 0, numPeople = 0;
        try {
            out = new PrintWriter(new FileWriter(FILE));
            numFloors = Integer.parseInt(args[0]);
            numPeople = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Invalid Input:");
        }

        // Prints into file number of floors
        // and people. Used in creating the right amount
        // of floor and people objects.
        out.println( numFloors );
        out.println( numPeople );
        
        int workHours = 60 * 60 * 8;
        int shift = workHours / 2;
        int width = workHours / 8;
        int cnt = 0; //What is this?

        double probPerson = (double)numPeople / workHours;
        double probFloor  = 1.0 / numFloors;

        //Calculates the time a person will be created,
        //their start and their end.
        for (int i =0; i < workHours; i++) {
            double p = ((i-shift)/30000)*Math.sin((i-shift)/3500);
            int start = 0, end = 0;
            if (RND.nextDouble() < (p+1) * probPerson) {
                if (RND.nextDouble() > p + 0.6) {        
                    start = 1 + RND.nextInt(numFloors);
                    if (RND.nextDouble() < 0.6 || start == 1)
                        end = 0;
                    else
                        end = RND.nextInt(start - 1);
                } else {
                    start = RND.nextInt(numFloors+1);
                    if (RND.nextDouble() < 0.6 || start == numFloors) 
                        start = 0;
                    end = start + RND.nextInt(numFloors - start);
                }
                if (start != end) {
                    out.println(i + " " + start + " " + end);
                    cnt++;
                }
            }
        }
        System.out.println(cnt);
        out.close();
    }
}
