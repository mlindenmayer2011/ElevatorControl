

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

public class Generator extends JFrame  {

    public Generator()
    {
		Container con = getContentPane();
		
		GeneratorGrid grid = new GeneratorGrid();
		GeneratorControls controls = new GeneratorControls(grid);
		
		con.setLayout(new BorderLayout());
		con.add(grid, BorderLayout.CENTER);
		con.add(controls, BorderLayout.SOUTH);

    }

}
