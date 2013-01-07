package com.unilabs.gui;

import javax.swing.JMenuItem;

import com.unilabs.gui.action.GUIAction;

/**
 * JMenuItem possédant la capacité de stocker et d'exécuter une action spécifiée par une interface
 * 
 * @author Ogier
 * @version 1.0
 */
public class JMenuActionItem extends JMenuItem{

	private static final long serialVersionUID = 454114993004999132L;
	
	private GUIAction action;

	public JMenuActionItem(String text, GUIAction action) {
		super(text);
		this.action = action;
		addActionListener(new MenuListener());
	}
	
	public void execute() {
		action.execute();
	}
	
	public String toString() {
		return "Menu entry for : " + getText() + ". Ref : " + super.toString();
	}
}
