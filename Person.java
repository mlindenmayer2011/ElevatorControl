package elevatorSimulator;

public class Person {

    private int m_start, m_dest, m_time;
    
    Person (int start, int dest, int time) {
        this.m_start = start;
        this.m_dest = dest;
        this.m_time = time;
    }
    
    //Accessor Methods////////////////////////
    public int getStart() { return m_start; }
    public int getDest() { return m_dest; }
    public int getTime() { return m_time; }
    ///////////////////////////////////////////
    
    //Mutator Methods//////////////////////////
    public void setStart( int start ) {
    	m_start = start;
    }
    
    public void setDest( int dest ) {
    	m_dest = dest;
    }
    
    public void setTime( int time ) {
    	m_time = time;
    }
    ///////////////////////////////////////////
}
