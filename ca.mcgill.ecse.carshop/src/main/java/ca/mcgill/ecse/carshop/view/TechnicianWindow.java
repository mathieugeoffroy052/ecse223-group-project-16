package ca.mcgill.ecse.carshop.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TechnicianWindow {

	private JFrame frame;
	private JTable table;
	private JTextField txtSetNewOpening;
	private JTextField txtNewClosingTime;
	private JTextField txtNewPassword;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JButton btnNewButton_1;
	private JSeparator separator, separator_1, separator_1_1;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JComboBox comboBox;
	private JButton btnNewButton;
	private JLabel lblNewLabel_2;
	private JTable overviewTable;
	private JScrollPane overviewScrollPane;

	/**
	 * Launch the application.
	 * remove this later when the constructor gets called from elsewhere upon a technician successfully logging in.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TechnicianWindow window = new TechnicianWindow();
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
	public TechnicianWindow() {
		initializeTechnicianView();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeTechnicianView() {
		frame = new JFrame();
		frame.setBounds(400, 400, 700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("My Garage");
		lblNewLabel.setBounds(60, 39, 65, 16);
		frame.getContentPane().add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Business Hours");
		lblNewLabel_1.setBounds(60, 129, 97, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		String[][] data = {{"", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"},{"Opening", "", "", "", "", ""},{"Closing", "", "", "", "", ""}};
		String[] column = {"","","","","",""};
		table = new JTable(data, column);
		table.setBounds(60, 177, 572, 57);
		frame.getContentPane().add(table);
		
		lblNewLabel_2 = new JLabel("Modify Business Hours");
		lblNewLabel_2.setBounds(60, 321, 153, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}));
		comboBox.setBounds(60, 349, 130, 27);
		frame.getContentPane().add(comboBox);
		
		txtSetNewOpening = new JTextField();
		txtSetNewOpening.setBounds(356, 348, 97, 26);
		frame.getContentPane().add(txtSetNewOpening);
		txtSetNewOpening.setColumns(10);
		
		txtNewClosingTime = new JTextField();
		txtNewClosingTime.setBounds(356, 379, 97, 26);
		frame.getContentPane().add(txtNewClosingTime);
		txtNewClosingTime.setColumns(10);
		
		btnNewButton = new JButton("Confirm Changes");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(474, 348, 158, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("Modify Account");
		lblNewLabel_3.setBounds(60, 438, 130, 16);
		frame.getContentPane().add(lblNewLabel_3);
		
		txtNewPassword = new JTextField();
		txtNewPassword.setBounds(204, 466, 130, 26);
		frame.getContentPane().add(txtNewPassword);
		txtNewPassword.setColumns(10);
		
		btnNewButton_1 = new JButton("Confirm Change");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(379, 466, 130, 29);
		frame.getContentPane().add(btnNewButton_1);
		
		separator = new JSeparator();
		separator.setBounds(60, 417, 572, 12);
		frame.getContentPane().add(separator);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(60, 105, 572, 12);
		frame.getContentPane().add(separator_1);
		
		separator_1_1 = new JSeparator();
		separator_1_1.setBounds(60, 288, 572, 12);
		frame.getContentPane().add(separator_1_1);
		
		lblNewLabel_4 = new JLabel("technician-type");
		lblNewLabel_4.setBounds(60, 77, 166, 16);
		frame.getContentPane().add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("New Opening Time");
		lblNewLabel_5.setBounds(215, 353, 119, 16);
		frame.getContentPane().add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("New Closing Time");
		lblNewLabel_6.setBounds(215, 384, 140, 16);
		frame.getContentPane().add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("New Password");
		lblNewLabel_7.setBounds(60, 471, 97, 16);
		frame.getContentPane().add(lblNewLabel_7);
		
		//in progress things
//		String[] appointmentColumnNames = {"Appointment name", "Booked customer", "Start time", "End time"};
//		overviewTable = new JTable(0,0);
////		overviewTable.setcolumn(appointmentColumnNames);
//		overviewScrollPane = new JScrollPane(overviewTable);
//		
//		frame.getContentPane().add(overviewTable);
	}
}
