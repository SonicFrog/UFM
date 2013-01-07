package com.unilabs.processing;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.unilabs.gui.NonEditableTableModel;

/**
 * Gestion de l'impression depuis le programme
 * @author Ogier
 * @version 1.0
 *
 */
public class PrintManager {

	
	private TableCreator printOut;
	private File fileOut;
	
	public PrintManager(NonEditableTableModel model) throws UnsupportedOperationException, IOException {
		printOut = new TableCreator(model, fileOut = File.createTempFile("ufm", ".ods"));
		printOut.write();
		Desktop.getDesktop().print(fileOut);
	}
}
