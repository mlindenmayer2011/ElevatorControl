import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class ElevatorStarter extends JFrame implements ActionListener {

	JTextField numFloorsTextField = new JTextField ();
	JTextField resultsTextField = new JTextField();	
	
	JLabel numFloorsLabel = new JLabel("Number of floors: ");
	
	JButton runButton = new JButton("Run!");
	JButton generatorButton = new JButton("Generate New Data");
	
	Container buttons = new Container();
	Container results = new Container();

	public Controls ()
	{
		resultsTextField.setEditable(false);
		results.setLayout(new BorderLayout());
		results.add(resultsTextField, BorderLayout.CENTER);
		
		buttons.setLayout(new GridLayout(0, 2));
		//buttons.add(numFloorsLabel);
		//buttons.add(numFloorsTextField);
		buttons.add(runButton);
		buttons.add(generatorButton);
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(results, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
		
		runButton.addActionListener(this);
		generatorButton.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == runButton)
		{
			
		}
		if (e.getSource() == generatorButton)
		{
			Generator gen = new Generator();
			gen.setSize(900, 500);
			gen.setDefaultCloseOperation(HIDE_ON_CLOSE);
			gen.setVisible(true);
		}
	}
	
	public static void main (String[] args) {
		
		Controls controlPanel = new Controls();
		controlPanel.setSize(400, 300);
		controlPanel.setVisible(true);
	}
}
