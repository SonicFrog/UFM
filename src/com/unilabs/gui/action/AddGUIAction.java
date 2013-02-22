package com.unilabs.gui.action;

import com.unilabs.gui.dialog.AddCarDialog;


/**
 * Action d'ajout d'une voiture
 * @author Ogier
 */
public class AddGUIAction implements GUIAction {

	@Override
	public void execute() {
		new AddCarDialog();
	}

}
