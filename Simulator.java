public class Simulator 
{
    private static Random RND = new Random();
    private static int time = 0;
    private static double averageWait = 0;
    private static Elavator e1;
    private static Controls c1 = e1.controls;
    private static Person[] p;
 
    public static void update () {
        averageWait += e1.unloadPassengers();
        if (averageWait > 0) {
            averageWait = averageWait / 2;
        }
        for (int i = 0; i < p.length; i++) {
            if (p[i].dest == e1.getFloor()) {
                if(loadPassenger(p[i])) { 
                    //loading failed so repress the call button
                    (dest - star) > 0 ? c1.pressUP(start):c1.pressDown(start);
                }
            }
        } 
 
        time++;
    }
 
    public static void main (String[] args) {
        int numFloors = Integer.parseInt(args[0]);
        int maxCapacity = Integer.parseInt(args[1]);
        int numPeople = Integer.parseInt(args[2]);
 
        e1 = new Elavator(maxCapacity, numFloors);   
        p = new Person[numPeople];
 
 
        for (int i = 0; i < numPeople; i++) {
            int start = 0; dest = 0;
            while (start == dest) {
                start = RND.nextInt(numFloors);
                dest = RND.nextInt(numFloors);
            }
            (dest - start) > 0 ? c1.pressUp(start) : c1.pressDown(start);
            p[i] = new Person (start, dest, 0);
        }
 
    }
 
    public static int getTime () { return time; }
    
}