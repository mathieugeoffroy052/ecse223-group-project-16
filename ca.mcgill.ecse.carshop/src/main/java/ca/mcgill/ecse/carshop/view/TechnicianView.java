package ca.mcgill.ecse.carshop.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TechnicianView extends JPanel {

	private static final long serialVersionUID = -3425184928730692407L;
//	private JFrame frame;
	private JTable table;
	private JTextField txtSetNewOpening;
	private JTextField txtNewClosingTime;
	private JTextField txtNewPassword;
	private String error;
	private JLabel errorMessage;

	/**
	 * Launch the application.
	 * remove this later when the constructor gets called from elsewhere upon a technician successfully logging in.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TechnicianWindow window = new TechnicianWindow();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public TechnicianView() {
		initializeTechnicianView();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeTechnicianView() {
		
		JLabel lblNewLabel = new JLabel("My Garage");
		lblNewLabel.setBounds(60, 39, 65, 16);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Business Hours");
		lblNewLabel_1.setBounds(60, 129, 97, 16);
		add(lblNewLabel_1);
		
		table = new JTable();
		table.setBounds(60, 177, 572, 57);
		add(table);
		JTable scheduleTable = new JTable();
		//make the table non editable (for the user. the table will still get updated):
		DefaultTableModel scheduleModelTable = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};		scheduleModelTable.addColumn("");
		scheduleModelTable.addColumn("Monday");
		scheduleModelTable.addColumn("Tuesday");
		scheduleModelTable.addColumn("Wednesday");
		scheduleModelTable.addColumn("Thursday");
		scheduleModelTable.addColumn("Friday");

		table.setModel(scheduleModelTable);
		
		Vector<String> opening = new Vector<String>();
		opening.addElement("Opening");
		opening.addElement("op monday");
		opening.addElement("op tuesday");
		opening.addElement("op wednesday");
		opening.addElement("op thursday");
		opening.addElement("op friday");
		
		Vector<String> closing = new Vector<String>();
		closing.addElement("Closing");
		closing.addElement("c monday");
		closing.addElement("c tuesday");
		closing.addElement("c wednesday");
		closing.addElement("c thursday");
		closing.addElement("c friday");
		
		scheduleModelTable.addRow(opening);
		scheduleModelTable.addRow(closing);
		
		JScrollPane scheduleScrollTable = new JScrollPane(table);
		scheduleScrollTable.setBounds(60, 177, 572, 57);
		scheduleScrollTable.setVisible(true);
		add(scheduleScrollTable);
		
		JLabel lblNewLabel_2 = new JLabel("Modify Business Hours");
		lblNewLabel_2.setBounds(60, 321, 153, 16);
		add(lblNewLabel_2);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}));
		comboBox.setBounds(60, 349, 130, 27);
		add(comboBox);
		
		txtSetNewOpening = new JTextField();
		txtSetNewOpening.setBounds(356, 348, 97, 26);
		add(txtSetNewOpening);
		txtSetNewOpening.setColumns(10);
		
		txtNewClosingTime = new JTextField();
		txtNewClosingTime.setBounds(356, 379, 97, 26);
		add(txtNewClosingTime);
		txtNewClosingTime.setColumns(10);
		
		JButton btnNewButton = new JButton("Confirm Changes");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(474, 348, 158, 29);
		add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel("Modify Account");
		lblNewLabel_3.setBounds(60, 725, 130, 16);
		add(lblNewLabel_3);
		
		txtNewPassword = new JTextField();
		txtNewPassword.setBounds(204, 750, 130, 26);
		add(txtNewPassword);
		txtNewPassword.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Confirm Change");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(379, 750, 130, 29);
		add(btnNewButton_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(60, 417, 572, 12);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(60, 105, 572, 12);
		add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(60, 288, 572, 12);
		add(separator_1_1);
		
		JLabel lblNewLabel_4 = new JLabel("technician-type");
		lblNewLabel_4.setBounds(60, 77, 166, 16);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New Opening Time");
		lblNewLabel_5.setBounds(215, 353, 119, 16);
		add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("New Closing Time");
		lblNewLabel_6.setBounds(215, 384, 140, 16);
		add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("New Password");
		lblNewLabel_7.setBounds(60, 750, 97, 16);
		add(lblNewLabel_7);
		
		
		JTable table = new JTable();
		//make the table non editable (for the user. the table will still get updated):
		DefaultTableModel modelTable = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modelTable.addColumn("Appointment name");
		modelTable.addColumn("Booked customer");
		modelTable.addColumn("Start time");
		modelTable.addColumn("End time");
		table.setModel(modelTable);
		
		for(int i = 0; i< 20;i++) {
			//here is where we would get the TO information about the appointments 
			//and insert them into the table
			Vector<String> r = new Vector<String>();
			r.addElement("test a");
			r.addElement("test b");
			r.addElement("test c");
			r.addElement("test d");
			modelTable.addRow(r);
		}
		JScrollPane scrollTable = new JScrollPane(table);
		scrollTable.setBounds(60, 460 ,572, 240);
		scrollTable.setVisible(true);
		add(scrollTable);

		
		errorMessage = new JLabel("error messages will appear here!");
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(60, 250, 572, 29 );
		add(errorMessage);
		
		GroupLayout layout = new GroupLayout(this);	// sets a layout
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
}