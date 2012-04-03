/*
 * @Author Bruce Martin
 * Created on 10/08/2005
 *
 * Purpose:
 *   Display a help screen, provide Help related functions
 *
 * Changes
 */
package net.sf.RecordEditor.utils.swing;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Standard Panel with builtin help functionality.
 *
 * @author Bruce Martin
 *
 */
@SuppressWarnings("serial")
public class BaseHelpPanel extends BasePanel  {

	private HelpWindow help = new HelpWindow(null);
    
    private ArrayList<KeyAdapter> list = new ArrayList<KeyAdapter>();
    private ArrayList<Component> componentList = new ArrayList<Component>(15);


    /**
     * Register a component (ie for PF1 handling)
     *
     * @see net.sf.RecordEditor.utils.swing.BasePanel#registerComponent
     */
    public final void registerComponent(JComponent component) {
    	registerComponent(component, 0, help);
    	componentList.add(component);
//        int i;
//        registerOneComponent(component);
// 
//        for (i = 0; i < component.getComponentCount(); i++) {
//        	registerOneComponent(component.getComponent(i));
//        }
    }
    
    public final void registerListner(KeyAdapter keyListner) {
    	registerComponent(this, -2, keyListner);
    }
   
    
    /**
     * register one component
     * @param component component to be registered
     */
    public final void registerOneComponent(Component component) {
    	registerOneComponent(component, help);
    	componentList.add(component);
    }

    
    /**
     * Recursively register components
     * @param component component to register
     * @param depth current recursion depth
     */
    private void registerComponent(JComponent component, int depth, KeyAdapter keyListner) {
        int i;
        Component c;
        registerOneComponent(component, keyListner);
 
        for (i = 0; i < component.getComponentCount(); i++) {
        	c = component.getComponent(i);
        	//registerOneComponent(c);
        	
        	if (depth < 4 && c instanceof JComponent) {
        		registerComponent((JComponent) c, depth + 1, keyListner);
        	} else {
        		registerOneComponent(c, keyListner);
        	}
        }
    }

    /**
     * register one component
     * @param component component to be registered
     * @param listner to register
     */
    private void registerOneComponent(Component component, KeyAdapter keyListner) {
    	
    
        component.addKeyListener(keyListner);
        //System.out.println("}} " + component.getName() + " " + component.getClass().getName());
        
        for (int i = 0; i < list.size(); i++) {     	
        	component.addKeyListener(list.get(i));
        }
   }

    /**
     * Add a key adapter to be added to the list
     * @param adapter keyadapter 
     */
    public void addReKeyListener(KeyAdapter adapter) {
    	list.add(adapter);
    	this.addKeyListener(adapter);
    	
    	for (Component c : componentList) {
    		if (c instanceof BaseHelpPanel) {
    			((BaseHelpPanel) c).addReKeyListener(adapter);
    		} else {
    			c.addKeyListener(adapter);
    		}
    	}
    	//System.out.println("==}} " +  " " + adapter.getClass().getName());
    }


    /**
     * Add Help button to the panel
     *
     * @param btn help button
     */
    public final void addHelpBtn(JButton btn) {

    	addHelpBtn(null, btn);
     }

    /**
     * Add Help button to the panel
     *
     * @param btn help button
     */
	public final void addHelpBtn(JComponent component, JButton btn) {

        addLine("", component, btn);
        btn.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                help.showHelp();
            }
        });
        registerOneComponent(btn);
    }


    /**
     * Shows the help screen
     *
     */
    public final void showHelp() {

        help.showHelp();
    }


    /**
     * Define the Help URL
     *
     * @param helpUrl name of the Help URL
     */
    public final void setHelpURL(String helpUrl) {
        help.setHelpURL(helpUrl);
        registerOneComponent(this, help);
    }
}
