package ca.mcgill.ecse.carshop.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JSeparator;

public class WinBuilder {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinBuilder window = new WinBuilder();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WinBuilder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(400, 400, 1440, 1024);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(13, 64, 216, 38);
		frame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Book an Appointment");
		lblNewLabel.setBounds(16, 16, 136, 18);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Choose a service/service combo:");
		lblNewLabel_1.setBounds(16, 45, 208, 18);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(513, 41, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Choose a start time: (hh:mm)");
		lblNewLabel_1_1.setBounds(274, 73, 208, 18);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Choose a start date: (yyyy-mm-dd)");
		lblNewLabel_1_1_1.setBounds(274, 45, 227, 18);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(513, 69, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Select optional services: (leave empty if just a service)");
		lblNewLabel_1_1_2.setBounds(16, 114, 339, 18);
		frame.getContentPane().add(lblNewLabel_1_1_2);
		
		JList list = new JList();
		list.setBounds(18, 144, 208, 70);
		frame.getContentPane().add(list);
		
		JButton btnNewButton = new JButton("Confirm");
		btnNewButton.setBounds(526, 185, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogOut.setBounds(1306, 12, 117, 29);
		frame.getContentPane().add(btnLogOut);
	}
}
