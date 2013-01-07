package com.unilabs.gui.dialog;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.unilabs.gui.FileChooser;

public class ExportDialog extends JDialog {

	private static final long serialVersionUID = 4916168869711366927L;
	private FileChooser jfc = new FileChooser();
	
	public ExportDialog(JFrame parent, String title) {
		super(parent, title);
		init();
	}
	
	private void init() {
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileFilter(new FileNameExtensionFilter("Fichier UnilabsFleetManager (.ufm)", "ufm"));
		jfc.setToolTipText("Selectionnez où sauvegarder votre tableau");
		jfc.showSaveDialog(getParent());
	}
	
	public File getFile() {
		return jfc.getSelectedFile();
	}
}
