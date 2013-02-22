package com.unilabs.gui.action;

import javax.swing.JFrame;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.options.OptionDialog;
import com.unilabs.options.OptionStorage;

/**
 * Action pour afficher les options
 * @author Ogier
 */
public class OptionAction implements GUIAction {

	@Override
	public void execute() {
		UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
		OptionStorage os = ufm.getOptions();
		JFrame gui = ufm.getGUI();
		new OptionDialog(gui, os);
	}
}
