package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.TOAppointment;
import ca.mcgill.ecse.carshop.controller.TOServiceBooking;

public class OwnerViewAppointments extends JPanel {
	
	//UI elements
	private ArrayList<OwnerViewAppointmentNode> appointmentNodes;
	private JLabel titleJLabel;
	
	private JLabel errorLabel;
	
	private JLabel selectDateLabel;
	private JTextField selectDateTxt;
	private JButton dateButton;
	
	private List<TOAppointment> appointments;
	private String error;
	
	private String dateString;
	private Date date;
	
	public OwnerViewAppointments() {
		
		initComponents();
		
		
		
	}
	
	public void initComponents() {
		selectDateLabel = new JLabel("Select Date (YYYY-MM-dd)");
		selectDateTxt = new JTextField(dateString);
		dateButton = new JButton("Refresh"); 
		errorLabel = new JLabel();
		errorLabel.setForeground(Color.RED);
		error = "";
		
		if (date == null || dateString == null || dateString == "" || dateString.length() == 0) {
			appointments = CarShopController.getAppointmentsOwner();
			appointmentNodes = new ArrayList<>();
			for (TOAppointment appt : appointments) {
				String customer = appt.getCustomerName();
				String service = appt.getServiceName();
				Date date = appt.getDate();
				Time startTime = appt.getStartTime();
				String status = appt.getStatus();
				List<TOServiceBooking> serviceBookings = appt.getServiceBookings();
				
				if (date != null && startTime != null) {
					appointmentNodes.add(new OwnerViewAppointmentNode(customer, service, date, startTime, status, serviceBookings, appt));
				}
				
			}
		} else {
			appointments = CarShopController.getAppointmentsOwner();
			appointmentNodes = new ArrayList<>();
			for (TOAppointment appt : appointments) {
				String customer = appt.getCustomerName();
				String service = appt.getServiceName();
				Date date = appt.getDate();
				Time startTime = appt.getStartTime();
				String status = appt.getStatus();
				List<TOServiceBooking> serviceBookings = appt.getServiceBookings();
				
				if (date != null && startTime != null && date.equals(this.date)) {
					appointmentNodes.add(new OwnerViewAppointmentNode(customer, service, date, startTime, status, serviceBookings, appt));
				}
				
			}
		}
		
		titleJLabel = new JLabel("Appointments");
		titleJLabel.setFont(new Font("Arial", Font.BOLD, 22));
		
		GroupLayout groupLayout = new GroupLayout(this);
		this.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		ParallelGroup parallelGroup = groupLayout.createParallelGroup();
		SequentialGroup sequentialGroup = groupLayout.createSequentialGroup();
		
		parallelGroup.addComponent(titleJLabel)
						.addComponent(errorLabel)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(selectDateLabel)
								.addComponent(selectDateTxt)
								.addComponent(dateButton));
		
		sequentialGroup.addComponent(titleJLabel)
			.addComponent(errorLabel)
			.addGroup(groupLayout.createParallelGroup()
				.addComponent(selectDateLabel)
				.addComponent(selectDateTxt)
				.addComponent(dateButton));
		
		for(int i = 0; i < appointmentNodes.size(); i++) {
			parallelGroup.addComponent(appointmentNodes.get(i));
			sequentialGroup.addComponent(appointmentNodes.get(i));
		}
		
		groupLayout.setHorizontalGroup(parallelGroup);
		
		groupLayout.setVerticalGroup(sequentialGroup);
		
		
		groupLayout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { selectDateLabel, selectDateTxt, dateButton });
		
		groupLayout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { selectDateLabel, selectDateTxt, dateButton });
		
		//add propertyChangeListener
		for (int i = 0; i < appointmentNodes.size(); i++) {
			appointmentNodes.get(i).addPropertyChangeListener(new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals("End button pressed")
							|| evt.getPropertyName().equals("No Show button pressed")
							|| evt.getPropertyName().equals("Start button pressed")) {
						System.out.println("Please wait, refreshing data");
						refreshData();
					}
					
				}
			});
		}
		
		dateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectDateActionPerformed(e);
				
			}
		});
		
		
	}
	
	private void refreshData() {
		appointments = null;
		appointmentNodes.clear();
		
		removeAll();
		initComponents();
		revalidate();
		repaint();
	}
	
	
	private void selectDateActionPerformed(ActionEvent event) {
		error = "";
		dateString = selectDateTxt.getText();
		if (dateString == null || dateString == "" || dateString.length() == 0) {
			refreshData();
		} else {
			try {
				System.out.println(dateString);
				this.date = CarShopController.stringToDate(dateString);
			} catch (Exception e) {
				error = e.getMessage();
			}
			
			if (error == null || error.length() == 0) {
				refreshData();
			} else {
				errorLabel.setText(error);
			}
		}
		
		
		revalidate();
		repaint();
		
	}
	
}
