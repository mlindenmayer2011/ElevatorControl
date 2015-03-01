

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.acl.Group;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GeneratorControls extends JPanel {
	private LineSelection ls;
	private JLabel lsLabel = new JLabel("Probability Line to Draw: ");
	
	public GeneratorControls (GeneratorGrid generator) {
		ls = new LineSelection(generator);
		setLayout(new BorderLayout());
		add(ls, BorderLayout.CENTER);
	}

	public class LineSelection extends JPanel implements ActionListener {
		private JRadioButton probArrivalButton = new JRadioButton();
		private JRadioButton probUpButton = new JRadioButton();
		private JRadioButton probLeavingButton = new JRadioButton();
		private JRadioButton probPersonButton = new JRadioButton();
		
		private JLabel probArrivalLabel = new JLabel("People Arriving:   ");
		private JLabel probUpLabel = new JLabel     ("People Going Up:   ");
		private JLabel probLeavingLabel = new JLabel   ("People Leaving: ");
		private JLabel probPersonLabel = new JLabel   ("New Person: ");
		private JLabel numFloorsLabel = new JLabel   ("Number of Floors: ");
		private JLabel numElevatorsLabel = new JLabel   ("Number of Elevatos: ");
		private JLabel expectedLabel = new JLabel   ("Estimate number of People: ");
		
		private JTextField numElevatorsText = new JTextField ();
		private JTextField numFloorsText = new JTextField ();
		private JTextField expectedText = new JTextField ();
		
		private JButton saveResults = new JButton("Generate Data");
		private JButton clearGrid = new JButton("Clear");
		
		private GeneratorGrid generator;
		
		public LineSelection (GeneratorGrid gen) {
			setLayout(new GridLayout(4,4));
			
			generator = gen;
			
			probArrivalButton.setSelected(true);
			
			ButtonGroup probSelection = new ButtonGroup();
			probSelection.add(probArrivalButton);
			probSelection.add(probUpButton);
			probSelection.add(probLeavingButton);
			probSelection.add(probPersonButton);
			
			probArrivalButton.addActionListener(this);
			probUpButton.addActionListener(this);
			probLeavingButton.addActionListener(this);
			probPersonButton.addActionListener(this);
			clearGrid.addActionListener(this);
			saveResults.addActionListener(this);
			
			probArrivalLabel.setForeground(Color.green);
			probUpLabel.setForeground(Color.blue);
			probLeavingLabel.setForeground(Color.red);
			probPersonLabel.setForeground(Color.orange);
			
			
			add(numFloorsLabel);
			add(numFloorsText);
			add(numElevatorsLabel);
			add(numElevatorsText);
			
			add(expectedLabel);
			add(expectedText);
			
			add(probArrivalLabel);
			add(probArrivalButton);
			add(probUpLabel);
			add(probUpButton);
			
			add(probLeavingLabel);
			add(probLeavingButton);
			add(saveResults);
			
			//add(probPersonLabel);
			//add(probPersonButton);
			add(clearGrid);
			setSize(100, 400);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clearGrid) {
				generator.clear();
			}
			if (e.getSource() == probArrivalButton) {
				generator.setSelectedLine(0);
			}
			if (e.getSource() == probUpButton) {
				generator.setSelectedLine(1);
			}
			if (e.getSource() == probLeavingButton) {
				generator.setSelectedLine(2);
			}
			if (e.getSource() == probPersonButton) {
				generator.setSelectedLine(3);
			}
			if(e.getSource() == saveResults) {
				int floors = Integer.parseInt(numFloorsText.getText());
				int elevators = Integer.parseInt(numElevatorsText.getText());
				double totalTime = 24 * 60 * 60;
				double probPerson = Double.parseDouble(expectedText.getText()) / totalTime;
				generator.setNumFloors(floors);
				generator.setNumElevators(elevators);
				generator.saveResults (probPerson);
			}
			
		}	
	}
}
