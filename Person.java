package elevatorSimulator;

//Used to store specified variables
//that are required to move a person
//correctly in an elevator.
public class Person {

    private int m_start, m_dest, m_time, m_dir;    
    final int DOWN = -1, UP = 1;
    
    Person (int start, int dest, int time) {
        m_start = start;
        m_dest = dest;
        m_time = time;
        
        //Determines direction of person
        if( start > dest ) { m_dir = DOWN; }
        else               { m_dir = UP; }
    }
    
    //Accessor Methods////////////////////////
    public int getStart() { return m_start; }
    public int getDest()  { return m_dest; }
    public int getTime()  { return m_time; }
    public int getDir()   { return m_dir; }
    ///////////////////////////////////////////
}
