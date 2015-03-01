package elevatorSimulator;



import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GeneratorGrid extends JPanel implements MouseListener, MouseMotionListener {
	
	public static final int HOURS_IN_DAY = 24;
	public static final double Y_AXIS_TICS = 0.05;
	
	//Probabilities of people arriving and going up and down in elevator.
	private ArrayList<Point> probArrival = new ArrayList<Point>(); 	//Green
	private ArrayList<Point> probUp = new ArrayList<Point>();		//Blue
	private ArrayList<Point> probLeaving = new ArrayList<Point>();	//Red
	private ArrayList<Point> probPerson = new ArrayList<Point>(); 	//Orange

	private Point start = null;
	private Point end = null;

	private int selectedLine = 0;
	private int selectedPointIndex = -1;
	private boolean isPressed = false;
	
	private int numFloors = 0;
	private int numElevators = 0;
	
	public GeneratorGrid ()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	
	/************************ Painting methods *******************************/
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		g.clearRect(0, 0, getWidth(), getHeight());		
		g.drawImage(drawLines(), 0, 0, null);
		g.drawImage(drawBackGround(), 0, 0, null);
		g.dispose();
	}
	
	public BufferedImage drawLines() {
		BufferedImage lines = new BufferedImage
				(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D)lines.createGraphics();
		g2d.setStroke(new BasicStroke(3));
		
		
		if (start != null && end != null && isPressed) {
			g2d.drawLine(start.x, start.y, end.x, end.y);
		}
		
		g2d.setColor(Color.green);
		drawLines (g2d, probArrival);
		drawPoints(g2d, probArrival);
		
		g2d.setColor(Color.blue);
		drawLines (g2d, probUp);
		drawPoints(g2d, probUp);
		
		g2d.setColor(Color.red);
		drawLines (g2d, probLeaving);
		drawPoints(g2d, probLeaving);
		
		g2d.setColor(Color.orange);
		drawLines (g2d, probPerson);
		drawPoints(g2d, probPerson);

		lines.flush();
		g2d.dispose();
		return lines;
	}
	
	public void drawLines (Graphics2D g, ArrayList<Point> points)
	{
		for (int i = 0; i < points.size() - 1; i++) {
			Point a = points.get(i);
			Point b = points.get(i + 1);
			g.drawLine(a.x, a.y, b.x, b.y);
		}
	}

    public void drawPoints(Graphics2D g, ArrayList<Point> points) {
    	Color temp = g.getColor();
    	g.setColor(Color.black);
    	for (int i = 0; i < points.size(); i++) {
			Point a = points.get(i);
			if (i == selectedPointIndex)
				g.fillOval(a.x-3, a.y-3, 8, 8);
			else
				g.fillOval(a.x-3, a.y-3, 6, 6);
		}
        g.setColor(temp);
    }
	
	public BufferedImage drawBackGround () {
		BufferedImage bg = new BufferedImage
				(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = bg.createGraphics();
		g.setColor(Color.black);
		
		int numMarksY = (int)(1 / Y_AXIS_TICS) + 2;
		int deltaY = (int)(getHeight() / numMarksY);
		int deltaX = getWidth() / (HOURS_IN_DAY + 2);
		
		//leave a gap at the ends
		int height = getHeight() - deltaY;
		int width = getWidth() - deltaX;
		
		//draw y-axis
		g.drawLine(deltaX, height, deltaX, deltaY);
		
		//draw markers along y-axis
		for (int i = deltaY; i < height-deltaY; i+=deltaY) {
			g.drawLine(deltaX, i+2, deltaX+5, i+2);
			int prob = (int)(((numMarksY-1) - i/deltaY)*(100*Y_AXIS_TICS));
			g.drawString(Integer.toString(prob), deltaX/2-10, i+6);
		}
		
		//draw x-axis 
		g.drawLine(deltaX, height, getWidth()-deltaX, height);
		
		//draw markers along the x-axis
		for (int i = 2*deltaX; i < width; i+=deltaX) {
			String time = null;
			int prob = (i/deltaX - 1);
			time = (prob/12) == 1 ? "pm" : "am";
			prob %= 12;
			if (prob == 0)
				prob = 12;
				
			g.drawString(Integer.toString(prob)+time, i, getHeight()-deltaY/2+3);
			g.drawLine(i+2, height, i+2, height - 5);
		}
		
		//draw grid along x-y plane
		for (int x = 2*deltaX; x < width; x+=deltaX) {
			for (int y = deltaY; y < height-deltaY; y+=deltaY) {
				g.drawOval(x, y, 5, 5);
			}
		}
		return bg;
	}
	
	/************************ Mouse Events *******************************/
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		start = getNearestPoint(arg0.getPoint());
		isPressed = true;
		
		Point p = getNearestPoint(arg0.getPoint());
		
		if (selectedPointIndex == -1 && isValidPoint(p))
			addPoint(p);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		isPressed = true;
		Point p = getNearestPoint(arg0.getPoint());
		start = p;
		selectedPointIndex = getPointIndex(p);
		if (selectedPointIndex == -1 && isValidPoint(p))
			addPoint(p);
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		isPressed = true;
		Point p = getNearestPoint(arg0.getPoint());
		selectedPointIndex = getPointIndex(p);
		if (selectedPointIndex == -1 && isValidPoint(p))
			addPoint(p);
		
		//selectedPointIndex = -1;
		start = end = null;
		repaint();	
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point p = getNearestPoint(arg0.getPoint());
		end = p;
		if (selectedPointIndex >= 0)
			setPoint(selectedPointIndex, p);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}
	
	/************************ Get Functions *******************************/
	
	public Point getNearestPoint (Point p) {
		int numMarksY = (int)(1 / Y_AXIS_TICS) + 2;
		int deltaY = (int)(getHeight() / numMarksY);
		int deltaX = getWidth() / (HOURS_IN_DAY + 2);
		int x = p.x / deltaX;
		int y = p.y /  deltaY;
		return new Point (x*deltaX+2, y*deltaY+2);
	}
	
	public Point getStartPoint (ArrayList<Point> p, double x) {
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i).x <= x)
				return p.get(i);
		}
		return null;
	}
	
	public Point getEndPoint (ArrayList<Point> p, double x) {
		int deltaX = getWidth() / (HOURS_IN_DAY + 2);
		for (int i = 0; i < p.size(); i++) {
			if (p.get(i).x+deltaX >= x)
				return p.get(i);
		}
		return null;
	}

	public int getPointIndex (Point p) {
		ArrayList<Point> temp = getSelectedLine();
		
		for (int i = 0; i < temp.size(); i++) {
			//Allow an error margin for the clicked point.
			if (temp.get(i).distance(p) < 6) {
				return i;
			}
		}
		
		return -1;
	}
	
	public ArrayList<Point> getSelectedLine() {
		switch (selectedLine) {
		case 0: return probArrival; 
		case 1: return probUp;
		case 2: return probLeaving;
		case 3: return probPerson;
		}
		return null;
	}
	
	/************************ Set Functions *******************************/
	
	public void addPoint (Point p) {
		ArrayList<Point> temp = getSelectedLine();
		
		int index = -1;		
		for (int i = 0; i < temp.size(); i++) {
			if (p.x < temp.get(i).x) {
				index = i;
				break;
			}
		}
		
		if (index < 0)
			temp.add(p);
		else
			temp.add(index, p);
		
	}
	
	public void setSelectedLine (int line) {
		assert (line == 0 || line == 1 || line == 2 || line == 3);
		System.out.println("Line Changed");
		selectedLine = line;
	}
	
	public void setPoint (int i, Point p) {
		ArrayList<Point> temp = getSelectedLine();
		
		if (!isValidPoint(p))
			return;
	
		if (i > 0 && temp.get(i-1).x > p.x)
			return;
		if (i < temp.size() - 1 && temp.get(i+1).x < p.x)
			return;
		
		temp.set(i, p);
	}
	
	public void setNumFloors (int f) {
		numFloors = f;
	}
	
	public void setNumElevators(int e) {
		numElevators = e;
	}
	
	public void clear() {
		System.out.println("Cleared");
		probArrival.clear();
		probUp.clear();
		probLeaving.clear();
		repaint();
	}
	
	/************************ Boolean Functions *******************************/
	
	public boolean isValidLine (ArrayList<Point> p) {
		int deltaX = getWidth() / (HOURS_IN_DAY + 2);
		int width = getWidth() - 2*deltaX;

		if (p.size() < 2) {
			System.out.println("Not enough points for a probability line.");
			return false;
		}
		
		if (p.get(0).x <= 2*deltaX+2 && p.get(p.size() - 1).x > width)
			return true;
		
		System.out.println("Probability line must fill entire timeline.");
		return false;
	}
	
	public boolean isValidPoint (Point p) {
		ArrayList<Point> temp = getSelectedLine();
		int numMarksY = (int)(1 / Y_AXIS_TICS) + 2;
		int deltaY = (int)(getHeight() / numMarksY);
		int deltaX = getWidth() / (HOURS_IN_DAY + 2);
		
		if (p.y > getHeight() - 2*deltaY || p.y < deltaY)
			return false;
		if (p.x < 2*deltaX || p.x > getWidth() - deltaX)
			return false;
		
		for (int i = 0; i < temp.size(); i++) {
			if (i != selectedPointIndex && temp.get(i).x == p.x) {
				return false;
			}
		}
		
		return true;
	}
	
	/***************************************************************************/

	public double getProbability(Point start, Point end, double time) {
		//equation of line: y = mx + b
		int numMarksY = (int)(1 / Y_AXIS_TICS) + 2;
		int deltaY = (int)(getHeight() / numMarksY);
		int deltaX = getWidth() / (HOURS_IN_DAY + 2);
		
		
		double y1 = (((numMarksY-1) - start.y/deltaY)*Y_AXIS_TICS);
		double y2 = (((numMarksY-1) - end.y/deltaY)*Y_AXIS_TICS);
		
		double x1 = (start.x/deltaX - 1);
		double x2 = (end.x/deltaX - 1);
		
		double m = 0;	
		if (x1 - x2 != 0) 
			m = (double)(y1 - y2) / (x1 - x2);
		
		double b = y1 - m * x1;
		return m * time + b;
	}
	
	public double getProbability(ArrayList<Point> p, double sec) {
		int deltaX = getWidth() / (HOURS_IN_DAY + 2);
		double hours = (double)sec/(60*60)+2;
		double x = (hours * deltaX);
		
		Point a = getStartPoint(p, x);
		Point b = getEndPoint(p, x);
		if (a != null && b != null) {
			return getProbability(a, b, hours);
		}
		
		return 0;
	}
	
	public void saveResults (double probPerson) {

		Random rnd = new Random();
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("data.txt"));
		} catch (Exception e) {
			System.out.println("Invalid Input: ");
		}
		
		if (!isValidLine(probArrival)) {
			System.out.println("Problem with probability of arrivals.");
			return;
		} else if (!isValidLine(probUp)) {
			System.out.println("Problem with probability of going up.");
			return;
		} else if (!isValidLine(probLeaving)) {
			System.out.println("Problem with probability of going down.");
			return;
		} 
		
		out.println(numFloors + " " + numElevators);
		
		int cnt = 0;
		for (int m = 1; m < HOURS_IN_DAY*60*60; m++) {
			int origin = 0, destination = 0;
						
			//probability of creating a new person
			if (rnd.nextDouble() < probPerson) {
				do { //continue unite origin != destination
					
					double prob = rnd.nextDouble();					
					if (prob < getProbability(probUp, m)) {
						//probability of person going up
						origin = rnd.nextInt(numFloors - 1); //exclude last floor
						if (prob < getProbability(probArrival, m)) {
							//probability of person just entering building
							origin = 0;
						}
						destination = origin + rnd.nextInt(numFloors - origin);
					} else {
						//probability of person going down
	                    origin = 1 + rnd.nextInt(numFloors-1); //can't go down if it was on level 1 already
	                    if (prob < getProbability(probLeaving, m)) {
	                    	//probability of person leaving the building
	                    	destination = 0;
	                    } else {
	                        destination = rnd.nextInt(origin) + 1;
	                    }
					}
				} while (origin == destination);
				
				out.println(origin + " " + destination + " " + m);				
				cnt++;
			}
			
			if (m == HOURS_IN_DAY*60*60-1)
				out.print(0 + " " + 1 + " " + m);

		}

		System.out.println(cnt);
		out.close();
	}
	
	public static void main (String[] args) {

	}
}
