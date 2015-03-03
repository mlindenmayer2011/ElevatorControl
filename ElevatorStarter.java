
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ElevatorStarter extends JFrame implements ActionListener {

	JTextField numFloorsTextField = new JTextField ();
	JTextArea resultsTextField = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(resultsTextField);
	
	JLabel numFloorsLabel = new JLabel("Number of floors: ");
	
	JButton runButton = new JButton("Run!");
	JButton generatorButton = new JButton("Generate New Data");
	
	JRadioButton smartDriverButton = new JRadioButton();
	JRadioButton baseDriverButton = new JRadioButton();
	
	JLabel smartDriverLabel = new JLabel("Smart Driver: ");
	JLabel baseDriverLabel = new JLabel("Base Driver: ");
	
	Container buttons = new Container();
	Container results = new Container();

	public ElevatorStarter ()
	{
		resultsTextField.setEditable(false);
		results.setLayout(new BorderLayout());
		results.add(scrollPane, BorderLayout.CENTER);
		
		buttons.setLayout(new GridLayout(0, 2));
		
		ButtonGroup probSelection = new ButtonGroup();
		probSelection.add(smartDriverButton);
		probSelection.add(baseDriverButton);

		smartDriverButton.setSelected(true);
		
		buttons.add(smartDriverLabel);
		buttons.add(smartDriverButton);
		buttons.add(baseDriverLabel);
		buttons.add(baseDriverButton);
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
			Building b;
			if (smartDriverButton.isSelected())
				 b = new Building(this, 1);
			else
				 b = new Building(this, 0);
		}
		if (e.getSource() == generatorButton)
		{
			Generator gen = new Generator();
			gen.setSize(900, 500);
			gen.setDefaultCloseOperation(HIDE_ON_CLOSE);
			gen.setVisible(true);
		}
	}
	
	public void println(String s) {
		resultsTextField.append("\n" + s);
	}
	
	public void clearResults() {
		resultsTextField.setText("");
	}
	public static void main (String[] args) {
		
		ElevatorStarter starter = new ElevatorStarter();
		starter.setSize(400, 300);
		starter.setVisible(true);
	}
}
