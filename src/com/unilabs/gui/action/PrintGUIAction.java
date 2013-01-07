package com.unilabs.gui.action;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;


/**
 * Action graphique pour l'impression de fichiers
 * @author Ogier
 * @version 1.0
 *
 */
public class PrintGUIAction implements GUIAction {

	private File print;

	public PrintGUIAction(File prt) {
		print = prt;
	}

	public void execute() {
		try {
			Desktop.getDesktop().print(print);
		} catch (IOException e) {
			Message.showErrorMessage(UnilabsFleetManager.getInstance().getGUI(), "Erreur", e.getLocalizedMessage());
		}
	}
}
