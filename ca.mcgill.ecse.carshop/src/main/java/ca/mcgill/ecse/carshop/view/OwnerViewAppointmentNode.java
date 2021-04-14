package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;

public class OwnerViewAppointmentNode extends JPanel {

	// UI elements
	private JLabel errorMessage;

	private JLabel txtCustomerName;
	private JLabel txtServiceName;
	private JLabel txtDate;
	private JLabel txtTime;
	private JLabel txtStatus;

	private JTable tableServices;

	private JButton btnStartButton;
	private JButton btnEndButton;
	private JButton btnNoShowButton;
	
	private String error;

	public OwnerViewAppointmentNode() {
		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);

		txtCustomerName = new JLabel();
		txtServiceName = new JLabel();
		txtDate = new JLabel();
		txtTime = new JLabel();
		txtStatus = new JLabel();

		tableServices = new JTable();

		btnStartButton = new JButton("Start");
		btnEndButton = new JButton("End");
		btnNoShowButton = new JButton("No Show");

		GroupLayout groupLayout = new GroupLayout(this);
		this.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup()
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
					.addComponent(tableServices));


		groupLayout.setHorizontalGroup(
				groupLayout.createSequentialGroup()
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
					.addComponent(tableServices));

		
		
		// link elements
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {
			txtCustomerName, txtServiceName, txtDate, txtTime, txtStatus
		});
		
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {
				btnStartButton, btnEndButton, btnNoShowButton
			});
		
	}
	
	private void startButtonActionPerformed(ActionEvent event) {
		// clear error message and basic input validation
		error = "";
		
		try {
			CarShopController.startAppointmentAt(null, error);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
	}
	

}
