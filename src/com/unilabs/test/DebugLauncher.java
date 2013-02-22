package com.unilabs.test;

import java.io.File;

import javax.swing.UIManager;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.gui.Message;
import com.unilabs.gui.UnilabsGUI;
import javax.swing.UnsupportedLookAndFeelException;

public class DebugLauncher {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			Message.showErrorMessage("Clochard", "Habba baba");
		}
		new UnilabsGUI();
		UnilabsFleetManager.getInstance().openODS(new File(ODSInputStreamTest.TEST_FILE));
		UnilabsFleetManager.getInstance().getGUI().paintAll(UnilabsFleetManager.getInstance().getGUI().getGraphics());
	}
}
