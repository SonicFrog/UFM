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
	
	/**
	 * Référence vers l'action de ce menu
	 */
	private GUIAction action;

	/**
	 * Instancie un JMenuActionItem
	 * @param text
	 *		Le texte de ce menu
	 * @param action 
	 *		L'action associé à ce menu
	 */
	public JMenuActionItem(String text, GUIAction action) {
		super(text);
		this.action = action;
		addActionListener(new MenuListener());
	}
	
	/**
	 * Execute l'action associé à ce menu
	 */
	public void execute() {
		action.execute();
	}
	
	@Override
	public String toString() {
		return "Menu entry for : " + getText() + ". Ref : " + super.toString();
	}
}
