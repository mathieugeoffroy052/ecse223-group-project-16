package ca.mcgill.ecse.carshop.view;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * Class to display the owner view
 * @author Group 16
 *
 */
public class OwnerView extends JPanel {
	
	public OwnerView() {
		super(new GridLayout(1, 1));
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel businessInfo = new OwnerViewBusinessInfo();
		
		JPanel serviceInfoJPanel = new OwnerViewServices();
		
		JPanel appointmentInfo = new OwnerViewAppointments();
		JScrollPane scrollPane = new JScrollPane(appointmentInfo);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		JPanel profileInfo = new OwnerViewProfile();
		
		
		// adding the business info tab
		tabbedPane.addTab("Business Information", businessInfo);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		//adding the services tab
		tabbedPane.addTab("Services", serviceInfoJPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		
		//adding the appointments tab
		tabbedPane.addTab("Appointments", scrollPane);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		
		//adding the profile tab
		tabbedPane.addTab("Profile", profileInfo);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		
		// add the tabbed pane to this panel
		add(tabbedPane);
		
		//The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
	}
	
	
	
}
