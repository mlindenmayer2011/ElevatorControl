package elevatorSimulator;
import java.io.File;
import java.util.Scanner;

public class Stats {

	public static void main(String[] args) {

		double mu = 0;
		int cnt = 0;
		try {

			Scanner in = new Scanner(new File("data.txt"));

			in.nextInt();

			while (in.hasNextLine()) {
				int start = in.nextInt();
				int dest = in.nextInt();
				int time = in.nextInt();

				// mu += time;
				mu += dest;
				cnt++;
			}

			in.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println(cnt + " | " + (double) mu / cnt);
	}

}
