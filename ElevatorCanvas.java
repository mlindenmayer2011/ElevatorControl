package elevatorSimulator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

public class ElevatorCanvas extends JPanel implements ActionListener {
	public static final int UP = 1;
	public static final int DOWN = -1;

	public static final int WIDTH = 250;
	public static final int HEIGHT = 3 * WIDTH;
	public static final int XGAP = 25;

	private Deque<Integer> dest = new ArrayDeque<Integer>();

	private int people = 0;
	private boolean updateElevator = true;
	private int x = 0, y = 0; // top left of the elevator.
	private final int step; // Space between each floor.

	private Timer m_speed;
	private Timer m_unload;

	private BufferedImage bg;
	Elevator parent;

	public ElevatorCanvas(Elevator e, int totalFloors, int speed, int unloadTime) {
		parent = e;
		m_speed = new Timer(speed, this);
		m_speed.setRepeats(true);

		m_unload = new Timer(unloadTime, this);
		m_unload.setRepeats(false);

		step = HEIGHT / totalFloors;
		setSize(WIDTH, HEIGHT);

		bg = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		// draw each floor
		Graphics g = bg.getGraphics();
		g.setColor(Color.black);
		for (int i = 0; i < totalFloors; i++) {
			g.drawLine(0, i * step, WIDTH, i * step);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == m_speed) {

			if (m_unload.isRunning()) {
				return;
			}

			if (dest.size() > 0) {
				int dir = (y < dest.getFirst()) ? UP : DOWN;
				if (y == dest.getFirst()) {
					dest.remove();
					m_unload.start();
				} else {
					y += dir;
					parent.repaint();
					repaint();
				}
			} else {
				m_speed.stop();
			}
		}
		if (e.getSource() == m_unload) {

		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		BufferedImage elevator;
		elevator = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		Graphics e = elevator.getGraphics();
		e.setColor(Color.black);
		e.drawRoundRect(x, y, step, step, 20, 20);
		for (int i = 0; i < people; i++) {
			System.out.println("1");
			e.drawOval(x * i, y * i, 5, 5);
		}
		g.drawImage(elevator, 0, 0, null);
		g.drawImage(bg, 0, 0, null);
		g.dispose();
	}

	public void addDestination(int floor) {
		dest.add(step * floor);
		m_speed.start();
	}

	public void addPerson() {
		people++;
		updateElevator = true;
	}

	public void removePerson() {
		people--;
		updateElevator = true;
	}

	public static void main(String[] args) {/*
											 * JFrame test = new JFrame();
											 * test.setDefaultCloseOperation
											 * (JFrame.EXIT_ON_CLOSE); Container
											 * con = test.getContentPane();
											 * ElevatorCanvas e = new
											 * ElevatorCanvas(3, 20, 100);
											 * con.add(e); test.setSize(500,
											 * 800); test.setVisible(true);
											 * e.addDestination(2);
											 * e.addDestination(1);
											 */
	}
}
