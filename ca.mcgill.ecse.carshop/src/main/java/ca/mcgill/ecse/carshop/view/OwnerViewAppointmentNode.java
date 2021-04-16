package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.controller.TOAppointment;
import ca.mcgill.ecse.carshop.controller.TOServiceBooking;

public class OwnerViewAppointmentNode extends JPanel {

	// UI elements
	private JLabel errorMessage;

	private JLabel txtCustomerName;
	private JLabel txtServiceName;
	private JLabel txtDate;
	private JLabel txtTime;
	private JLabel txtStatus;

	private JTable tableServices;
	private JScrollPane scrollPane;

	private JButton btnStartButton;
	private JButton btnEndButton;
	private JButton btnNoShowButton;

	private String error;
	private static final int TABLE_HEIGHT = 200;
	private DefaultTableModel tableModel;
	private String overviewColumnNames[] = { "Service", "Garage", "Duration", "Start Time", "End Time" };
	private List<TOServiceBooking> toServiceBookings;
	private TOAppointment toAppointment;

	public OwnerViewAppointmentNode(String customer, String service, Date date, Time time, String status,
			List<TOServiceBooking> toServiceBookings, TOAppointment toAppt) {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		txtCustomerName = new JLabel(customer);
		txtServiceName = new JLabel(service);
		txtDate = new JLabel(CarShopController.dateToString(date));
		txtTime = new JLabel(CarShopController.timeToString(time));
		txtStatus = new JLabel(status);
		this.toServiceBookings = toServiceBookings;
		this.toAppointment = toAppt;

		tableServices = new JTable();
		scrollPane = new JScrollPane(tableServices);
		Dimension d = tableServices.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, TABLE_HEIGHT));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scrollPane);

		btnStartButton = new JButton("Start");
		btnEndButton = new JButton("End");
		btnNoShowButton = new JButton("No Show");

		btnStartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButtonActionPerformed(e);
			}
		});
		
		btnEndButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endButtonActionPerformed(e);
			}
		});
		
		btnNoShowButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noShowButtonActionPerformed(e);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(this);
		this.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
			.addComponent(errorMessage)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup()
					.addComponent(txtCustomerName)
					.addComponent(txtServiceName)
					.addComponent(txtDate)
					.addComponent(txtTime)
					.addComponent(txtStatus))
				.addComponent(btnStartButton)
				.addComponent(btnEndButton)
				.addComponent(btnNoShowButton))
			.addComponent(scrollPane));

		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
			.addComponent(errorMessage)
				.addGroup(groupLayout.createParallelGroup()
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(txtCustomerName)
						.addComponent(txtServiceName)
						.addComponent(txtDate)
						.addComponent(txtTime)
						.addComponent(txtStatus))
				.addComponent(btnStartButton)
				.addComponent(btnEndButton)
				.addComponent(btnNoShowButton))
			.addComponent(scrollPane));

		// link elements
		groupLayout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { txtCustomerName, txtServiceName, txtDate, txtTime, txtStatus });

		groupLayout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { btnStartButton, btnEndButton, btnNoShowButton });
		
		refreshServiceList();
	}

	private void startButtonActionPerformed(ActionEvent event) {
		// clear error message and basic input validation
		error = "";

		try {
			CarShopController.startAppointmentAt(CarShopController.findAppointment(toAppointment), CarShopApplication.getSystemDateTime());
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		refreshServiceList();
		if (error == "") {
			firePropertyChange("Start button pressed", false, true);
		}
	}
	
	private void endButtonActionPerformed(ActionEvent event) {
		// clear error message and basic input validation
		error = "";

		try {
			CarShopController.endAppointmentAt(CarShopController.findAppointment(toAppointment), CarShopApplication.getSystemDateTime());
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		refreshServiceList();
		if (error == "") {
			firePropertyChange("End button pressed", false, true);
		}
	}
	
	
	private void noShowButtonActionPerformed(ActionEvent event) {
		// clear error message and basic input validation
		error = "";

		try {
			CarShopController.updateNoShowAt(CarShopController.findAppointment(toAppointment), CarShopApplication.getSystemDateTime());
		} catch (Exception e) {
			error = e.getMessage();
		}
		refreshServiceList();
		if (error == "") {
			firePropertyChange("No Show button pressed", false, true);
		}
	}
	
	

	private void refreshServiceList() {
		errorMessage.setText(error);
		
		tableModel = new DefaultTableModel(0, 0);
		tableModel.setColumnIdentifiers(overviewColumnNames);
		tableServices.setModel(tableModel);
		for (TOServiceBooking item : toServiceBookings) {
			String service = item.getService().getName();
			String garage = item.getService().getGarage().getName();
			String duration = Integer.toString(item.getService().getDuration());
			String start = CarShopController.timeToString(item.getTimeSlot().getStartTime());
			String end = CarShopController.timeToString(item.getTimeSlot().getEndTime());
			
			Object[] obj = { service, garage, duration, start, end };
			tableModel.addRow(obj);
		}
		Dimension d = tableServices.getPreferredSize();
		scrollPane.setPreferredSize(new Dimension(d.width, TABLE_HEIGHT));
	}

}
