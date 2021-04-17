package ca.mcgill.ecse.carshop.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.controller.CarShopController;
import ca.mcgill.ecse.carshop.controller.InvalidInputException;
import ca.mcgill.ecse.carshop.controller.TOCarshopCombo;
import ca.mcgill.ecse.carshop.controller.TOCarshopService;

public class OwnerViewServices extends JPanel {
	// UI elements 
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
	
	
	private String selectedServiceName;
	private String selectedServiceDuration;
	private String selectedServiceGarage;
	
	private String serviceColumnNames[] = { "Service", "Garage", "Duration"};
	private String comboColumnNames[] = { "", "Service", "Garage", "Duration", "mandatory"};
	private String comboHeaderNames[] = { "Combo Name", "Service", "Garage", "Duration", "mandatory"};
	private String errorMessage;
	private List<TOCarshopService> services;
	private List<TOCarshopCombo> combos;
	private List<String> serviceList;
	private List<String> comboList;

	private static final int HEIGHT_TABLE = 400;

	public OwnerViewServices() {
		
		initView();
		refreshData();
		
	}
	
	private void initView() {
		//Initializing UI elements
		errorLabel = new JLabel(errorString);
		errorLabel.setForeground(Color.RED);

		selectedServiceName = "";
		selectedServiceDuration = "";
		selectedServiceGarage = "";
		
		serviceList = new ArrayList<>();
		comboList = new ArrayList<>();
		
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
							.addComponent(btnEditService))
					.addComponent(serviceJScrollPane)
					.addGroup(groupLayout.createSequentialGroup()
							.addComponent(serviceCombosLabel)
							.addComponent(btnAddCombo)
							.addComponent(btnEditCombo))
					.addComponent(comboJScrollPane));
		
		
		groupLayout.setVerticalGroup(
				groupLayout.createSequentialGroup()
					.addComponent(errorLabel)
					.addGroup(groupLayout.createParallelGroup()
							.addComponent(servicesLabel)
							.addComponent(btnAddService)
							.addComponent(btnEditService))
					.addComponent(serviceJScrollPane)
					.addGroup(groupLayout.createParallelGroup()
							.addComponent(serviceCombosLabel)
							.addComponent(btnAddCombo)
							.addComponent(btnEditCombo))
					.addComponent(comboJScrollPane));
		
		groupLayout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { btnAddCombo, btnAddService,  btnEditCombo, btnEditService});
		
		groupLayout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { btnAddCombo, btnAddService, btnEditCombo, btnEditService});
		
		groupLayout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { servicesLabel, serviceCombosLabel });
		
		groupLayout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { servicesLabel, serviceCombosLabel });
		
		groupLayout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { serviceJScrollPane, comboJScrollPane });
		
		btnAddService.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addServiceButtonPerformed(e);
				
			}
		});
		
		btnEditService.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateServiceButtonPerformed(e);
				
			}
		});
		
		btnAddCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addComboButtonPerformed(e);
				
			}
		});
		
		btnEditCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateComboButtonPerformed(e);
				
			}
		});
		
		refreshServiceTable();
		refreshComboTable();
	}
	

	private void refreshServiceTable() {
		errorLabel.setText(errorMessage);
		serviceList = new ArrayList<>();
		
		if (errorMessage == null || errorMessage.length() == 0) {
			servicesDTM = new DefaultTableModel(0, 0);
			servicesDTM.setColumnIdentifiers(serviceColumnNames);
			tableServices.setModel(servicesDTM);
			
			services = CarShopController.getCarshopServices();
			
			for(TOCarshopService toCarshopService : services) {
				String service = toCarshopService.getName();
				serviceList.add(service);
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
		comboList = new ArrayList<>();
		
		if (errorMessage == null || errorMessage.length() == 0) {
			combosDTM = new DefaultTableModel(0, 0);
			combosDTM.setColumnIdentifiers(comboHeaderNames);
			tableCombos.setModel(combosDTM);
			
			combos = CarShopController.getCarshopServiceCombos();
			
			for(TOCarshopCombo toCarshopCombo : combos) {
				String name = toCarshopCombo.getComboName();
				
				comboList.add(name);
				
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
	
	public void addServiceButtonPerformed(ActionEvent event) {
		errorMessage = "";
		JFrame addServiceFrame = new JFrame();
		JLabel smallErrorLabel;
		smallErrorLabel = new JLabel();
		smallErrorLabel.setForeground(Color.RED);
		JLabel updateTitle = new JLabel("Add new service");
		updateTitle.setFont(new Font("Arial", Font.BOLD, 22));
		JLabel name = new JLabel("Name:");
		JTextField nameText = new JTextField();
		JLabel duration = new JLabel("Duration:");
		JTextField durationText = new JTextField();
		JLabel phoneNum = new JLabel("Garage:");
		String [] garageNames = { "Tire", "Engine", "Transmission", "Electronics", "Fluids" };
		JComboBox<String> garageComboBox = new JComboBox<>(garageNames);
		JButton addService = new JButton("Add");
		
		
		GroupLayout layout = new GroupLayout(addServiceFrame.getContentPane());
		addServiceFrame.getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		//horizontal
		layout.setHorizontalGroup(
				layout.createParallelGroup()
					.addComponent(smallErrorLabel)
					.addComponent(updateTitle)
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
									.addComponent(name)
									.addComponent(phoneNum)
									.addComponent(duration))
							.addGroup(layout.createParallelGroup()
									.addComponent(nameText)
									.addComponent(garageComboBox)
									.addComponent(durationText)
									))
					.addComponent(addService));
		

		
		//vertical 
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(smallErrorLabel)
					.addComponent(updateTitle)
					.addGroup(layout.createParallelGroup()
							.addComponent(name)
							.addComponent(nameText))
					.addGroup(layout.createParallelGroup()
							.addComponent(phoneNum)
							.addComponent(garageComboBox))
					.addGroup(layout.createParallelGroup()
							.addComponent(duration)
							.addComponent(durationText))
					.addComponent(addService));
				
		layout.linkSize(SwingConstants.HORIZONTAL,
				new java.awt.Component[] { name, nameText, phoneNum, garageComboBox, duration, durationText,addService  });
		layout.linkSize(SwingConstants.VERTICAL,
				new java.awt.Component[] { name, nameText, phoneNum, garageComboBox, duration, durationText, addService });

		addServiceFrame.pack();
		centerWindow(addServiceFrame);
		addServiceFrame.setVisible(true);
		
		addService.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String newName = nameText.getText();
				String newDuration = durationText.getText();
				try {
					int duration = Integer.parseInt(newDuration);
				} catch (Exception e) {
					errorMessage = e.getMessage();
				}
				String newGarage = (String)garageComboBox.getSelectedItem();
				
				try {
					CarShopController.ownerDefinesService(CarShopApplication.getCurrentUser(), newName, newDuration, newGarage, CarShopApplication.getCarShop());
					errorMessage = null;
					addServiceFrame.setVisible(false);
				} catch (InvalidInputException e) {
					errorMessage = e.getMessage();
				}
				
				if (errorMessage!= null && errorMessage != "") {
					smallErrorLabel.setText(errorMessage);
					errorMessage = "";
				}
				refreshData();
				addServiceFrame.pack();
			}
		});
	}
	
	public void updateServiceButtonPerformed(ActionEvent event) {
		if (tableServices.getSelectedRow() == -1) {
			errorMessage = "Please select a service";
		} else {
			errorMessage = "";
			int selectedRow = tableServices.getSelectedRow();
			selectedServiceName = (String)tableServices.getValueAt(selectedRow, 0);
			selectedServiceGarage = (String)tableServices.getValueAt(selectedRow, 1);
			selectedServiceDuration = (String)tableServices.getValueAt(selectedRow, 2);
			
			String oldName = selectedServiceName;
			
			JFrame addServiceFrame = new JFrame();
			JLabel smallErrorLabel;
			smallErrorLabel = new JLabel();
			smallErrorLabel.setForeground(Color.RED);
			JLabel updateTitle = new JLabel("Update service");
			updateTitle.setFont(new Font("Arial", Font.BOLD, 22));
			JLabel name = new JLabel("Name:");
			JTextField nameText = new JTextField(selectedServiceName);
			JLabel duration = new JLabel("Duration:");
			JTextField durationText = new JTextField(selectedServiceDuration);
			JLabel phoneNum = new JLabel("Garage:");
			String [] garageNames = { "Tire", "Engine", "Transmission", "Electronics", "Fluids" };
			JComboBox<String> garageComboBox = new JComboBox<>(garageNames);
			garageComboBox.setSelectedItem(selectedServiceGarage);
			JButton addService = new JButton("Update");
			
			
			GroupLayout layout = new GroupLayout(addServiceFrame.getContentPane());
			addServiceFrame.getContentPane().setLayout(layout);
			layout.setAutoCreateGaps(true);
			layout.setAutoCreateContainerGaps(true);
			
			//horizontal
			layout.setHorizontalGroup(
					layout.createParallelGroup()
						.addComponent(smallErrorLabel)
						.addComponent(updateTitle)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(name)
										.addComponent(phoneNum)
										.addComponent(duration))
								.addGroup(layout.createParallelGroup()
										.addComponent(nameText)
										.addComponent(garageComboBox)
										.addComponent(durationText)
										))
						.addComponent(addService));
			

			
			//vertical 
			layout.setVerticalGroup(
					layout.createSequentialGroup()
						.addComponent(smallErrorLabel)
						.addComponent(updateTitle)
						.addGroup(layout.createParallelGroup()
								.addComponent(name)
								.addComponent(nameText))
						.addGroup(layout.createParallelGroup()
								.addComponent(phoneNum)
								.addComponent(garageComboBox))
						.addGroup(layout.createParallelGroup()
								.addComponent(duration)
								.addComponent(durationText))
						.addComponent(addService));
					
			layout.linkSize(SwingConstants.HORIZONTAL,
					new java.awt.Component[] { name, nameText, phoneNum, garageComboBox, duration, durationText,addService  });
			layout.linkSize(SwingConstants.VERTICAL,
					new java.awt.Component[] { name, nameText, phoneNum, garageComboBox, duration, durationText, addService });

			addServiceFrame.pack();
			centerWindow(addServiceFrame);
			addServiceFrame.setVisible(true);
			
			addService.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					String newName = nameText.getText();
					String newDuration = durationText.getText();
					try {
						int duration = Integer.parseInt(newDuration);
					} catch (Exception e) {
						errorMessage = e.getMessage();
					}
					String newGarage = (String)garageComboBox.getSelectedItem();
					
					try {
						CarShopController.updateService(CarShopApplication.getCurrentUser(), oldName , newName, newDuration, newGarage, CarShopApplication.getCarShop());

						errorMessage = null;
						addServiceFrame.setVisible(false);
					} catch (InvalidInputException e) {
						errorMessage = e.getMessage();
					}
					
					if (errorMessage!= null && errorMessage != "") {
						smallErrorLabel.setText(errorMessage);
						errorMessage = "";
					}
					refreshData();
					addServiceFrame.pack();
				}
				
			});
			
		}
		refreshData();
	}
	
	// Update info and action listeners
	public void addComboButtonPerformed(ActionEvent event) {
		if (serviceList.size() == 0) {
			errorMessage = "The carshop doesn't have any services!";
		} else {
			errorMessage = "";
			JFrame addServiceFrame = new JFrame();
			JLabel smallErrorLabel;
			smallErrorLabel = new JLabel();
			smallErrorLabel.setForeground(Color.RED);
			JLabel updateTitle = new JLabel("Add new service combo");
			updateTitle.setFont(new Font("Arial", Font.BOLD, 22));
			JLabel name = new JLabel("Name:");
			JTextField nameText = new JTextField();
			JLabel mainService = new JLabel("Main Service:");
			String[] services = new String[serviceList.size()];
			for (int i = 0; i < serviceList.size(); i++) {
				services[i] = serviceList.get(i);
			}

			JComboBox<String> mainComboBox = new JComboBox<String>(services);

			JTable comboServiceTable;

			if (errorMessage == null || errorMessage.length() == 0) {
				DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
				String[] tableCol = { "Service", "Add", "mandatory" };

				defaultTableModel.setColumnIdentifiers(tableCol);

				comboServiceTable = new JTable(defaultTableModel) {
					@Override
					public Class getColumnClass(int column) {
						switch (column) {
						case 0:
							return String.class;
						case 1:
							return Boolean.class;
						case 2:
							return Boolean.class;
						default:
							return String.class;
						}
					}
				};
				for (String service : serviceList) {

					Object[] obj = { service, false, false };
					defaultTableModel.addRow(obj);
				}

				JScrollPane comboServiceScrollPane = new JScrollPane(comboServiceTable);
				Dimension d1 = comboServiceTable.getPreferredSize();
				comboServiceScrollPane.setPreferredSize(new Dimension(d1.width, HEIGHT_TABLE / 2));

				comboServiceScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

				addServiceFrame.add(comboServiceScrollPane);
				JButton addService = new JButton("Add");

				GroupLayout layout = new GroupLayout(addServiceFrame.getContentPane());
				addServiceFrame.getContentPane().setLayout(layout);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);

				// horizontal
				layout.setHorizontalGroup(layout.createParallelGroup().addComponent(smallErrorLabel)
						.addComponent(updateTitle)
						.addGroup(layout.createSequentialGroup().addComponent(name).addComponent(nameText)
								.addComponent(addService))
						.addGroup(layout.createSequentialGroup().addComponent(mainService).addComponent(mainComboBox))
						.addComponent(comboServiceScrollPane));

				// vertical
				layout.setVerticalGroup(
						layout.createSequentialGroup().addComponent(smallErrorLabel).addComponent(updateTitle)
								.addGroup(layout.createParallelGroup().addComponent(name).addComponent(nameText)
										.addComponent(addService))
								.addGroup(layout.createParallelGroup().addComponent(mainService).addComponent(mainComboBox))
								.addComponent(comboServiceScrollPane));

				layout.linkSize(SwingConstants.HORIZONTAL,
						new java.awt.Component[] { name, nameText, addService, mainService, mainComboBox });
				layout.linkSize(SwingConstants.VERTICAL,
						new java.awt.Component[] { name, nameText, addService, mainService, mainComboBox });

				addServiceFrame.pack();
				centerWindow(addServiceFrame);
				addServiceFrame.setVisible(true);

				smallErrorLabel.setText(errorMessage);

				addService.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						String newName = nameText.getText();
						String mainService = (String) mainComboBox.getSelectedItem();

						List<Boolean> selected = new ArrayList<>();
						List<Boolean> mandatory = new ArrayList<>();

						String serviceString = "";
						String mandatoryString = "";

						for (int i = 0; i < comboServiceTable.getRowCount(); i++) {
							Boolean selectedBoolean = (Boolean) comboServiceTable.getValueAt(i, 1);
							Boolean mandatoryBoolean = (Boolean) comboServiceTable.getValueAt(i, 2);

							selected.add(selectedBoolean);
							mandatory.add(mandatoryBoolean);
						}

						serviceString += mainService;
						mandatoryString += true;

						for (int i = 0; i < serviceList.size(); i++) {
							if (selected.get(i).equals(true) && !serviceList.get(i).equals(mainService)) {
								serviceString = serviceString + "," + serviceList.get(i);
								mandatoryString = mandatoryString + "," + (mandatory.get(i));
							}
						}

						try {
							String owner = CarShopApplication.getCurrentUser();
							CarShopController.OwnerDefinesServiceCombo(owner, newName, mainService, serviceString,
									mandatoryString, CarShopApplication.getCarShop());
							errorMessage = null;
							addServiceFrame.setVisible(false);
						} catch (InvalidInputException e) {
							errorMessage = e.getMessage();
						}

						if (errorMessage != null && errorMessage != "") {
							smallErrorLabel.setText(errorMessage);
							errorMessage = "";
						}
						refreshData();
						addServiceFrame.pack();
					}
				});
			}
		}
		refreshData();
	}
	
	public void updateComboButtonPerformed(ActionEvent event) {
		if (comboList.size() == 0) {
			errorMessage = "The carshop doesn't have any service combos!";
		} else {
			errorMessage = "";
			JFrame addServiceFrame = new JFrame();
			JLabel smallErrorLabel;
			smallErrorLabel = new JLabel();
			smallErrorLabel.setForeground(Color.RED);
			JLabel updateTitle = new JLabel("Update service combo");
			JLabel selectCombo = new JLabel("Select Combo");
			
			String[] combosArray = new String[comboList.size()];
			
			for(int i = 0; i < comboList.size(); i++) {
				combosArray[i] = comboList.get(i);
			}
			
			JComboBox<String> selectComboBox = new JComboBox<>(combosArray);
			updateTitle.setFont(new Font("Arial", Font.BOLD, 22));
			JLabel name = new JLabel("Name:");
			JTextField nameText = new JTextField();
			JLabel mainService = new JLabel("Main Service:");
			String[] services = new String[serviceList.size()];
			for (int i = 0; i < serviceList.size(); i++) {
				services[i] = serviceList.get(i);
			}

			JComboBox<String> mainComboBox = new JComboBox<String>(services);

			JTable comboServiceTable;

			if (errorMessage == null || errorMessage.length() == 0) {
				DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
				String[] tableCol = { "Service", "Add", "mandatory" };

				defaultTableModel.setColumnIdentifiers(tableCol);

				comboServiceTable = new JTable(defaultTableModel) {
					@Override
					public Class getColumnClass(int column) {
						switch (column) {
						case 0:
							return String.class;
						case 1:
							return Boolean.class;
						case 2:
							return Boolean.class;
						default:
							return String.class;
						}
					}
				};
				for (String service : serviceList) {

					Object[] obj = { service, false, false };
					defaultTableModel.addRow(obj);
				}

				JScrollPane comboServiceScrollPane = new JScrollPane(comboServiceTable);
				Dimension d1 = comboServiceTable.getPreferredSize();
				comboServiceScrollPane.setPreferredSize(new Dimension(d1.width, HEIGHT_TABLE / 2));

				comboServiceScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

				addServiceFrame.add(comboServiceScrollPane);
				JButton addService = new JButton("Add");

				GroupLayout layout = new GroupLayout(addServiceFrame.getContentPane());
				addServiceFrame.getContentPane().setLayout(layout);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);

				// horizontal
				layout.setHorizontalGroup(layout.createParallelGroup().addComponent(smallErrorLabel)
						.addComponent(updateTitle)
						.addGroup(layout.createSequentialGroup()
								.addComponent(selectCombo)
								.addComponent(selectComboBox))
						.addGroup(layout.createSequentialGroup().addComponent(name).addComponent(nameText)
								.addComponent(addService))
						.addGroup(layout.createSequentialGroup().addComponent(mainService).addComponent(mainComboBox))
						.addComponent(comboServiceScrollPane));

				// vertical
				layout.setVerticalGroup(
						layout.createSequentialGroup().addComponent(smallErrorLabel).addComponent(updateTitle)
							.addGroup(layout.createParallelGroup()
								.addComponent(selectCombo)
								.addComponent(selectComboBox))
								.addGroup(layout.createParallelGroup().addComponent(name).addComponent(nameText)
										.addComponent(addService))
								.addGroup(layout.createParallelGroup().addComponent(mainService).addComponent(mainComboBox))
								.addComponent(comboServiceScrollPane));

				layout.linkSize(SwingConstants.HORIZONTAL,
						new java.awt.Component[] { name, nameText, addService, mainService, mainComboBox, selectCombo, selectComboBox });
				layout.linkSize(SwingConstants.VERTICAL,
						new java.awt.Component[] { name, nameText, addService, mainService, mainComboBox, selectCombo, selectComboBox });

				addServiceFrame.pack();
				centerWindow(addServiceFrame);
				addServiceFrame.setVisible(true);

				smallErrorLabel.setText(errorMessage);

				addService.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						String newName = nameText.getText();
						String mainService = (String) mainComboBox.getSelectedItem();

						String oldName = (String) selectComboBox.getSelectedItem();
						
						List<Boolean> selected = new ArrayList<>();
						List<Boolean> mandatory = new ArrayList<>();

						String serviceString = "";
						String mandatoryString = "";

						for (int i = 0; i < comboServiceTable.getRowCount(); i++) {
							Boolean selectedBoolean = (Boolean) comboServiceTable.getValueAt(i, 1);
							Boolean mandatoryBoolean = (Boolean) comboServiceTable.getValueAt(i, 2);

							selected.add(selectedBoolean);
							mandatory.add(mandatoryBoolean);
						}

						serviceString += mainService;
						mandatoryString += true;

						for (int i = 0; i < serviceList.size(); i++) {
							if (selected.get(i).equals(true) && !serviceList.get(i).equals(mainService)) {
								serviceString = serviceString + "," + serviceList.get(i);
								mandatoryString = mandatoryString + "," + (mandatory.get(i));
							}
						}

						try {
							String owner = CarShopApplication.getCurrentUser();
							CarShopController.updateServiceCombo(owner,oldName, newName, mainService, serviceString,
									mandatoryString, CarShopApplication.getCarShop());
							errorMessage = null;
							addServiceFrame.setVisible(false);
						} catch (InvalidInputException e) {
							errorMessage = e.getMessage();
						}

						if (errorMessage != null && errorMessage != "") {
							smallErrorLabel.setText(errorMessage);
							errorMessage = "";
						}
						refreshData();
						addServiceFrame.pack();
					}
				});
			}
		}
		refreshData();
	}
	
	public void refreshData() {
		errorLabel.setText(errorMessage);
		
		if (errorMessage == null || errorMessage.length() == 0) {
			refreshServiceTable();
			refreshComboTable();
			selectedServiceName = "";
			selectedServiceDuration = "";
			selectedServiceGarage = "";
		}
		revalidate();
		repaint();

    }
	
	
	
	//helper methods
    public static void centerWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

    }
	
    
    
    
}
