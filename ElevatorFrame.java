
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ElevatorFrame extends JFrame implements ActionListener {
	public static final int WIDTH = 250;
	public static final int HEIGHT = 3 * WIDTH;
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int WAIT = 1000;

	private LinkedList<Integer> dest = new LinkedList<Integer>();

	private int x = 0, y = 0;
	private int step = 0;
	private int floors = 0;

	private Timer speed = new Timer(20, this);
	private Timer unload = new Timer(WAIT, this);

	public ElevatorFrame(int totalFloors) {
		floors = totalFloors;
		step = HEIGHT / floors;
		x = 25;
		y = 0;
		dest.add(y);

		unload.setRepeats(false);
		speed.setRepeats(true);

		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void nextFloor(int direction) {
		int dir = (direction > 0) ? UP : DOWN;
		dest.push(dest.getFirst() + dir * step);
		speed.start();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == speed) {
			int dir = (y < dest.getLast()) ? 1 : -1;
			if (y != dest.getLast() && !unload.isRunning()) {
				y += dir;
				repaint();
			} else if (dest.size() > 1 && y == dest.getLast()) {
				unload.start();
				dest.removeLast();
			}
		} else if (e.getSource() == unload) {
			// code for unloading
		}
	}

	public void paint(Graphics gr) {
		super.paint(gr);
		BufferedImage bf = new BufferedImage(getWidth(), getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		Graphics g = bf.getGraphics();
		g.setColor(Color.black);
		for (int i = 0; i < floors; i++)
			g.drawLine(0, i * step, WIDTH, i * step);

		g.drawRoundRect(x, y, step, step, 20, 20);
		gr.drawImage(bf, 0, 0, null);
	}

	public static void main(String[] args) {
		ElevatorFrame e = new ElevatorFrame(5);
		for (int i = 0; i < 4; i++)
			e.nextFloor(UP);
		for (int i = 0; i < 4; i++)
			e.nextFloor(DOWN);
		e.nextFloor(UP);
		e.setVisible(true);
		e.setSize(250, 750);
	}
}
