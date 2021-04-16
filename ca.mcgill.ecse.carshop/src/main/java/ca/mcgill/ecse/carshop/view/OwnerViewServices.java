package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class OwnerViewServices extends JPanel{
	private JLabel errorLabel;
	
	private JLabel servicesLabel;
	private JLabel serviceCombosLabel;
	
	private String errorString;
	
	private JTable comboTable;
	private DefaultTableModel comboTableModel;
	private String comboColumnNames[] = { "Service", "Garage", "Duration", "Start Time", "End Time" };
	
	
	public OwnerViewServices() {
    	errorLabel = new JLabel(errorString);
    	errorLabel.setForeground(Color.RED);
		
		servicesLabel = new JLabel("Services");
		servicesLabel.setFont(new Font("Arial", Font.BOLD, 22));
		serviceCombosLabel = new JLabel("Service Combos");
		serviceCombosLabel.setFont(new Font("Arial", Font.BOLD, 22));
		
	}
	
	private void refreshComboTable() {
		comboTableModel = new DefaultTableModel(0, 0);
		comboTableModel.setColumnIdentifiers(comboColumnNames);
		comboTable.setModel(comboTableModel);
		
		
	}
	
}
