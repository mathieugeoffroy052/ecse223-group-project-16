package ca.mcgill.ecse.carshop.view;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
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
		
		// adding the business info tab
		tabbedPane.addTab("Business Info", getComponentPopupMenu());
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		
		//adding the services tab
		tabbedPane.addTab("Services", getComponentPopupMenu());
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		
		//adding the appointments tab
		tabbedPane.addTab("Appointments", getComponentPopupMenu());
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
		
		// add the tabbed pane to this panel
		add(tabbedPane);
		
		//The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
	}
	
	
	
}
