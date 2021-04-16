package ca.mcgill.ecse.carshop.view;

import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.controller.TOBusinessHour;
import ca.mcgill.ecse.carshop.controller.TOAppointment;

import java.util.List;


public class TechnicianView extends JPanel {

	private static final long serialVersionUID = -3425184928730692407L;
//	private JFrame frame;
	private JTable table;
	private JTextField txtSetNewOpening;
	private JTextField txtNewClosingTime;
	private JTextField txtNewPassword;
	private String error;
	private JLabel errorMessage;
	private DefaultTableModel modelTable;
	private JComboBox comboBox;
	private static List<TOBusinessHour> garageBusinessHours = CarShopController.getGarageTOBusinessHours();
	private JLabel labelNotification;
	
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
		
		
		labelNotification = new JLabel();
		labelNotification.setBounds(206, 775, 300, 29);
		add(labelNotification);
		
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
		};	
			
		scheduleModelTable.addColumn("");
		scheduleModelTable.addColumn("Monday");
		scheduleModelTable.addColumn("Tuesday");
		scheduleModelTable.addColumn("Wednesday");
		scheduleModelTable.addColumn("Thursday");
		scheduleModelTable.addColumn("Friday");


		table.setModel(scheduleModelTable);
		
		Vector<String> opening = new Vector<String>();
		Vector<String> closing = new Vector<String>();

		opening.addElement("Opening");
		closing.addElement("Closing");

		//set the garage business hours
		for(int i=0; i<5;i++) {
			opening.addElement(garageBusinessHours.get(i).getStartTime().toString());
			closing.addElement(garageBusinessHours.get(i).getEndTime().toString());
		}

		scheduleModelTable.addRow(opening);
		scheduleModelTable.addRow(closing);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}));
		comboBox.setBounds(60, 349, 130, 27);
		add(comboBox);
		
		
		//changing garage opening hours
		JButton btnNewButton = new JButton("Confirm Changes");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Time openingTime = null;
				Time closingTime = null;
				Time oldOpening = null;
				Time oldClosing = null;
				try {
					error = "";
					//works properly as long as proper times are inputted and no errors are thrown...
					//if one of the text fields is empty it freaks out D:
					openingTime = CarShopController.stringToTime(txtSetNewOpening.getText());
					closingTime = CarShopController.stringToTime(txtNewClosingTime.getText());
					if(openingTime == null||(closingTime == null||openingTime.equals("") || closingTime.equals(""))) {
						throw new InvalidInputException("Please fill in both fields");
					}
				} catch (InvalidInputException e1) {
					error = e1.getMessage();
				}
				if(error!=null) {
					errorMessage.setText(error);
				}
				if(!(openingTime == null||(closingTime == null||openingTime.equals("") || closingTime.equals("")))) {
					String day = (String) comboBox.getSelectedItem();
					
					int columnToChange = 0;
					if(day.equals("Monday")) {
						columnToChange = 1;
					}
					if(day.equals("Tuesday")) {
						columnToChange = 2;
					}
					if(day.equals("Wednesday")) {
						columnToChange = 3;
					}
					if(day.equals("Thursday")) {
						columnToChange = 4;
					}
					if(day.equals("Friday")) {
						columnToChange = 5;
					}
					int rowToChange1 = 0;
					int rowToChange2 = 0;
					try {
						error = "";
						String op = openingTime.toString();
						String cl = closingTime.toString();
						String user = CarShopApplication.getCurrentUser();
						CarShopController.changeGarageBusinessHour(day, op, cl,user , CarShopApplication.getCarShop());
//						oldClosing = CarShopController.stringToTime(scheduleModelTable.getValueAt(rowToChange2, columnToChange).toString());
					} catch (Exception e1) {
						error = e1.getMessage();
					}
					if(error!=null) {
						errorMessage.setText(error);
					}
					if(!openingTime.equals("")) {
						rowToChange1 = 0;
						scheduleModelTable.setValueAt(openingTime,rowToChange1,columnToChange);
						try {
							error = "";
							String op = openingTime.toString();
							String cl = closingTime.toString();
							String user = CarShopApplication.getCurrentUser();
							CarShopController.changeGarageBusinessHour(day, op, cl,user , CarShopApplication.getCarShop());
						} catch (InvalidInputException e1) {
							// TODO Auto-generated catch block
							error = e1.getMessage();
						}
						if(error!=null) {
							errorMessage.setText(error);
						}
					}
					if(!closingTime.toString().equals("")) {
						rowToChange2 = 1;
						scheduleModelTable.setValueAt(closingTime, rowToChange2, columnToChange);
					}
					
					
//					try {
//						error = "";
//						//it doesnt ever check that the garage business hours are inside the business hours of the car shop...
//						CarShopController.setGarageBusinessHours(day, openingTime.toString(), closingTime.toString(), oldOpening.toString(), oldClosing.toString(), CarShopApplication.getCarShop());
//					} catch (InvalidInputException e1) {
//						// TODO Auto-generated catch block
//						error = e1.getMessage();
//					}
//					if(error!=null) {
//						errorMessage.setText(error);
//					}
				}
				labelNotification.setText("");
				txtSetNewOpening.setText("");
				txtNewClosingTime.setText("");
			}
		});
		btnNewButton.setBounds(474, 348, 158, 29);
		add(btnNewButton);
		
		JScrollPane scheduleScrollTable = new JScrollPane(table);
		scheduleScrollTable.setBounds(60, 177, 572, 57);
		scheduleScrollTable.setVisible(true);
		add(scheduleScrollTable);
		
		JLabel lblNewLabel_2 = new JLabel("Modify Business Hours");
		lblNewLabel_2.setBounds(60, 321, 153, 16);
		add(lblNewLabel_2);
		
		txtSetNewOpening = new JTextField();
		txtSetNewOpening.setBounds(356, 348, 97, 26);
		add(txtSetNewOpening);
		txtSetNewOpening.setColumns(10);
		
		txtNewClosingTime = new JTextField();
		txtNewClosingTime.setBounds(356, 379, 97, 26);
		add(txtNewClosingTime);
		txtNewClosingTime.setColumns(10);
		
		
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
				String username = CarShopApplication.getCurrentUser();
				String password = txtNewPassword.getText();
				try {
					CarShopController.setTechnicianPassword(username, password, CarShopApplication.getCarShop());
					txtNewPassword.setText("");
				} catch (InvalidInputException e1) {
					error = e1.getMessage();
				}
				if(error != null) {
					errorMessage.setText(error);
				}else {
					//change was successful. send notification
					labelNotification.setText("Password changed successfully");	
				}
			}
		});
		btnNewButton_1.setBounds(379, 750, 130, 29);
		add(btnNewButton_1);
		
		//separators
		JSeparator separator = new JSeparator();
		separator.setBounds(60, 417, 572, 12);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(60, 105, 572, 12);
		add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(60, 288, 572, 12);
		add(separator_1_1);
		
		//returns the username of the current user, which is also the string for technician type
		JLabel lblNewLabel_4 = new JLabel(CarShopApplication.getCurrentUser());
		lblNewLabel_4.setBounds(60, 77, 166, 16);
		add(lblNewLabel_4);
		
		//new garage hours labels
		JLabel lblNewLabel_5 = new JLabel("New Opening Time");
		lblNewLabel_5.setBounds(215, 353, 119, 16);
		add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("New Closing Time");
		lblNewLabel_6.setBounds(215, 384, 140, 16);
		add(lblNewLabel_6);
		
		//new password label
		JLabel lblNewLabel_7 = new JLabel("New Password");
		lblNewLabel_7.setBounds(60, 750, 97, 16);
		add(lblNewLabel_7);
		
		
		JTable table = new JTable();
		//make the table non editable (for the user. the table will still get updated):
		modelTable = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		modelTable.addColumn("Appointment date");
		modelTable.addColumn("Start time");
		modelTable.addColumn("Appointment name");
		modelTable.addColumn("Customer name");
		table.setModel(modelTable);
		
		List<TOAppointment> toGarageAppointments = CarShopController.getGarageAppointments();
		for(TOAppointment app: toGarageAppointments) {
			
			Vector<String> r = new Vector<String>();
			
			r.addElement(app.getDate().toString());			//appointment date
			r.addElement(app.getStartTime().toString());	//start time
			r.addElement(app.getServiceName());				//appt name
			r.addElement(app.getCustomerName());			//customer name
			
			modelTable.addRow(r);
		}			
		JScrollPane scrollTable = new JScrollPane(table);
		scrollTable.setBounds(60, 460 ,572, 240);
		scrollTable.setVisible(true);
		add(scrollTable);

		//initialize the error message with an empty message
		errorMessage = new JLabel("");
		errorMessage.setForeground(Color.RED);
		errorMessage.setBounds(60, 250, 572, 29 );
		add(errorMessage);
		
		GroupLayout layout = new GroupLayout(this);	// sets a layout
		this.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
}