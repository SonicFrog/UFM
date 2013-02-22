package com.unilabs.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ActionListener gérant les interactions avec la barre de menu du programme
 * 
 * @version 1.1
 * @author Ogier
 * @see ActionListener
 *
 */
public class MenuListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JMenuActionItem source = (JMenuActionItem) e.getSource();
			source.execute();
		} catch (ClassCastException e1) {
			throw new RuntimeException("Woops a MenuListener was assigned to a non JMenuActionItem !! " + e1.getLocalizedMessage());
		} catch (RuntimeException e1) {
			String message = ((e1.getLocalizedMessage() == null) ? 
						"Aucune information (" + e1.getClass() + ")" : e1.getLocalizedMessage());
			Message.showErrorMessage("Erreur", "Une erreur s'est produite : \n" + message);
			e1.printStackTrace(System.out);
		}
	}
}
