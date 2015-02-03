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

        int workHours = 60 * 60 * 8;
        int shift = workHours / 2;
        int width = workHours / 8;
        int cnt = 0;

        double probPerson = (double)numPeople / workHours;
        double probFloor  = 1.0 / numFloors;

        for (int i =0; i < workHours; i++) {
            double p = ((i-shift)/30000)*Math.sin((i-shift)/3500);
            int start = RND.nextInt(numFloors+1), end = RND.nextInt(numFloors+1);
            if (RND.nextDouble() < (p+1) * probPerson) {
                if (RND.nextDouble() > p + 0.6) {
                    start = 0;
                } else if (RND.nextDouble() < p + 0.6) {
                    end = 0;
                }
                if (start != end) {
                    out.println(start + " " + end + " " + i);
                    cnt++;
                }
            }
        }
        System.out.println(cnt);
        out.close();
    }
}
