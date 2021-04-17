package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
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

import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.TOCarshopCombo;
import ca.mcgill.ecse.carshop.controller.TOCarshopService;

public class OwnerViewServices extends JPanel {
	
	private JLabel errorLabel;

	private JLabel servicesLabel;
	private JLabel serviceCombosLabel;

	private String errorString;
	
	private JTable tableServices;
	private JTable tableCombos;

	private DefaultTableModel servicesDTM;
	private DefaultTableModel combosDTM;
	
	private JScrollPane serviceJScrollPane;
	private JScrollPane comboJScrollPane;

	private JButton btnAddService;
	private JButton btnAddCombo;
	
	private JButton btnEditService;
	private JButton btnEditCombo;
	
	private JButton btnDeleteService;
	private JButton btnDeleteCombo;
	
	
	private String serviceColumnNames[] = { "Service", "Garage", "Duration"};
	private String comboColumnNames[] = { "", "Service", "Garage", "Duration", "Optional"};
	private String comboHeaderNames[] = { "Combo Name", "Service", "Garage", "Duration", "Optional"};
	private String errorMessage;
	private List<TOCarshopService> services;
	private List<TOCarshopCombo> combos;

	private static final int HEIGHT_TABLE = 400;

	public OwnerViewServices() {
		
		initView();
		
	}
	
	private void initView() {
		errorLabel = new JLabel(errorString);
		errorLabel.setForeground(Color.RED);

		servicesLabel = new JLabel("Services");
		servicesLabel.setFont(new Font("Arial", Font.BOLD, 22));
		serviceCombosLabel = new JLabel("Service Combos");
		serviceCombosLabel.setFont(new Font("Arial", Font.BOLD, 22));
		
		tableServices = new JTable();
		tableCombos = new JTable();
		
		serviceJScrollPane = new JScrollPane(tableServices);
		comboJScrollPane = new JScrollPane(tableCombos);
		
		Dimension d1 = tableServices.getPreferredSize();
		serviceJScrollPane.setPreferredSize(new Dimension(d1.width, HEIGHT_TABLE));

		serviceJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		Dimension d2 = tableCombos.getPreferredSize();
		comboJScrollPane.setPreferredSize(new Dimension(d2.width, HEIGHT_TABLE));

		comboJScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.add(serviceJScrollPane);
		this.add(comboJScrollPane);
		
		btnAddService = new JButton("Add");
		btnAddCombo = new JButton("Add");
		btnEditService = new JButton("Edit");
		btnEditCombo = new JButton("Edit");
		btnDeleteService = new JButton("Delete");
		btnDeleteCombo = new JButton("Delete");
		
		GroupLayout groupLayout = new GroupLayout(this);
		this.setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);
		
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup()
					.addComponent(errorLabel)
					.addGroup(groupLayout.createSequentialGroup()
							.addComponent(servicesLabel)
							.addComponent(btnAddService)
							.addComponent(btnEditService)
							.addComponent(btnDeleteService))
					.addComponent(serviceJScrollPane)
					.addGroup(groupLayout.createSequentialGroup()
							.addComponent(serviceCombosLabel)
							.addComponent(btnAddCombo)
							.addComponent(btnEditCombo)
							.addComponent(btnDeleteCombo))
					.addComponent(comboJScrollPane));
		
		
		groupLayout.setVerticalGroup(
				groupLayout.createSequentialGroup()
					.addComponent(errorLabel)
					.addGroup(groupLayout.createParallelGroup()
							.addComponent(servicesLabel)
							.addComponent(btnAddService)
							.addComponent(btnEditService)
							.addComponent(btnDeleteService))
					.addComponent(serviceJScrollPane)
					.addGroup(groupLayout.createParallelGroup()
							.addComponent(serviceCombosLabel)
							.addComponent(btnAddCombo)
							.addComponent(btnEditCombo)
							.addComponent(btnDeleteCombo))
					.addComponent(comboJScrollPane));
		
		groupLayout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { btnAddCombo, btnAddService, btnDeleteCombo, btnDeleteService, btnEditCombo, btnEditService});
		
		groupLayout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { btnAddCombo, btnAddService, btnDeleteCombo, btnDeleteService, btnEditCombo, btnEditService});
		
		groupLayout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { servicesLabel, serviceCombosLabel });
		
		groupLayout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { servicesLabel, serviceCombosLabel });
		
//		groupLayout.linkSize(SwingConstants.HORIZONTAL,
//				new java.awt.Component[] { serviceJScrollPane, comboJScrollPane });
		
		groupLayout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { serviceJScrollPane, comboJScrollPane });
		
		refreshServiceTable();
		refreshComboTable();
	}

	private void refreshServiceTable() {
		errorLabel.setText(errorMessage);
		
		if (errorMessage == null || errorMessage.length() == 0) {
			servicesDTM = new DefaultTableModel(0, 0);
			servicesDTM.setColumnIdentifiers(serviceColumnNames);
			tableServices.setModel(servicesDTM);
			
			services = CarShopController.getCarshopServices();
			
			for(TOCarshopService toCarshopService : services) {
				String service = toCarshopService.getName();
				String garage = toCarshopService.getGarage();
				String duration = String.valueOf(toCarshopService.getDuration());
				Object[] obj = { service, garage, duration};
				servicesDTM.addRow(obj);
			}
			Dimension d = tableServices.getPreferredSize();
			serviceJScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_TABLE));

		}
		revalidate();
		repaint();
	}
	
	private void refreshComboTable() {
		errorLabel.setText(errorMessage);
		
		if (errorMessage == null || errorMessage.length() == 0) {
			combosDTM = new DefaultTableModel(0, 0);
			combosDTM.setColumnIdentifiers(comboHeaderNames);
			tableCombos.setModel(combosDTM);
			
			combos = CarShopController.getCarshopServiceCombos();
			
			for(TOCarshopCombo toCarshopCombo : combos) {
				String name = toCarshopCombo.getComboName();
				String main = toCarshopCombo.getMainService();
				List<String> services = toCarshopCombo.getServices();
				List<String> garages = toCarshopCombo.getGarages();
				List<Integer> durations = toCarshopCombo.getDuration();
				List<Boolean> mandatory = toCarshopCombo.getMandatory();
				
				
				Object[] comboHeader = { name, main, "", "", "", ""};
				combosDTM.addRow(comboHeader);
				Object[] subHeader = comboColumnNames;
				combosDTM.addRow(subHeader);
				for(int i = 0; i < services.size(); i++) {
					String service = services.get(i);
					String garage = garages.get(i);
					Integer duration = durations.get(i);
					Boolean isManditary = mandatory.get(i);
					
					Object[] serv = { "", service, garage, duration, isManditary};
					combosDTM.addRow(serv);
				}
				
				Object[] empty = { "", "", "", "", "", ""};
				combosDTM.addRow(empty);
			}
			Dimension d = tableCombos.getPreferredSize();
			comboJScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_TABLE));

		}
		revalidate();
		repaint();
	}
}
