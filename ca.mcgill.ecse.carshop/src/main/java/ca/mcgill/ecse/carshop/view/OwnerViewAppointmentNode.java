package ca.mcgill.ecse.carshop.view;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

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

}
