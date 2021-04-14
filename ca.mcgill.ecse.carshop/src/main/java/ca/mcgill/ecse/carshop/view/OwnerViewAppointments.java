package ca.mcgill.ecse.carshop.view;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.TOAppointment;
import ca.mcgill.ecse.carshop.controller.TOServiceBooking;

public class OwnerViewAppointments extends JPanel {
	
	//UI elements
	private ArrayList<OwnerViewAppointmentNode> appointmentNodes;
	private JLabel titleJLabel;
	
	private List<TOAppointment> appointments;
	
	public OwnerViewAppointments() {
		
		initAppointments();
		
		
		
	}
	
	public void initAppointments() {
		appointments = CarShopController.getAppointmentsOwner();
		for (TOAppointment appt : appointments) {
			String customer = appt.getCustomerName();
			String service = appt.getServiceName();
			Date date = appt.getDate();
			Time startTime = appt.getStartTime();
			String status = appt.getStatus();
			List<TOServiceBooking> serviceBookings = appt.getServiceBookings();
			
		appointmentNodes.add(new OwnerViewAppointmentNode(customer, service, date, startTime, status, serviceBookings));
		}
		
		titleJLabel = new JLabel("Appointments");
		appointmentNodes = new ArrayList<>();
		
		GroupLayout groupLayout = new GroupLayout(this);
		this.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		ParallelGroup parallelGroup = groupLayout.createParallelGroup();
		SequentialGroup sequentialGroup = groupLayout.createSequentialGroup();
		
		parallelGroup.addComponent(titleJLabel);
		sequentialGroup.addComponent(titleJLabel);
		
		for(int i = 0; i < appointmentNodes.size(); i++) {
			parallelGroup.addComponent(appointmentNodes.get(i));
			sequentialGroup.addComponent(appointmentNodes.get(i));
		}
		
		groupLayout.setHorizontalGroup(parallelGroup);
		
		groupLayout.setVerticalGroup(sequentialGroup);
		
		
		//add propertyChangeListener
		for (int i = 0; i < appointmentNodes.size(); i++) {
			appointmentNodes.get(i).addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					refreshData();
					
				}
			});
		}
		
		//create appointment components
		Component[] components = new Component[appointmentNodes.size()];
		for (int i = 0; i < components.length; i++) {
			components[i] = appointmentNodes.get(i);
		}
	}
	
	public void refreshData() {
		appointments = null;
		appointmentNodes.clear();
		
		removeAll();
		initAppointments();
		revalidate();
		repaint();
	}
	
}
