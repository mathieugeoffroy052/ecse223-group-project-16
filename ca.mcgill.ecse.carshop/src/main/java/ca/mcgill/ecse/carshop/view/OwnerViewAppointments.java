package ca.mcgill.ecse.carshop.view;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class OwnerViewAppointments extends JPanel {
	
	//UI elements
	private ArrayList<OwnerViewAppointmentNode> appointments;
	private JLabel titleJLabel;
	
	public OwnerViewAppointments() {
		
		titleJLabel = new JLabel("Appointments");
		appointments = new ArrayList<>();
		
		GroupLayout groupLayout = new GroupLayout(this);
		this.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		ParallelGroup parallelGroup = groupLayout.createParallelGroup();
		SequentialGroup sequentialGroup = groupLayout.createSequentialGroup();
		
		parallelGroup.addComponent(titleJLabel);
		sequentialGroup.addComponent(titleJLabel);
		
		for(int i = 0; i < appointments.size(); i++) {
			parallelGroup.addComponent(appointments.get(i));
			sequentialGroup.addComponent(appointments.get(i));
		}
		
		groupLayout.setHorizontalGroup(parallelGroup);
		
		groupLayout.setVerticalGroup(sequentialGroup);
		
		
		//create appointment components
		Component[] components = new Component[appointments.size()];
		for (int i = 0; i < components.length; i++) {
			components[i] = appointments.get(i);
		}
		
	}
	
}
