package com.unilabs.gui.action;

import com.unilabs.gui.dialog.AddCarDialog;


public class AddGUIAction implements GUIAction {

	@Override
	public void execute() {
		new AddCarDialog();
	}

}
