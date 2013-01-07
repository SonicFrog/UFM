package com.unilabs.test;

import java.io.File;

import javax.swing.UIManager;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;
import com.unilabs.gui.UnilabsGUI;

public class DebugLauncher {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			Message.showErrorMessage("Clochard", "Habba baba");
		}
		new UnilabsGUI();
		UnilabsFleetManager.getInstance().openODS(new File(ODSInputStreamTest.TEST_FILE));
	}
}
