package com.unilabs.gui.dialog;

import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.unilabs.gui.FileChooser;

public class ExportDialog extends JDialog {

	private static final long serialVersionUID = 4916168869711366927L;
	
	private static final String DEFAULT_FILETYPE = "Fichier UnilabsFleetManager (.ufm)";
	private static final String DEFAULT_FILEDESC = "ufm";
	
	private FileChooser jfc = new FileChooser();
	
	public ExportDialog(JFrame parent, String title) {
		super(parent, title);
		init(DEFAULT_FILETYPE, DEFAULT_FILEDESC);
	}
	
	public ExportDialog(JFrame parent, String filename, String filetype) {
		super(parent);
		init(filename, filetype);
	}
	
	private void init(String ftype, String fext) {
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileFilter(new FileNameExtensionFilter(ftype, fext));
		jfc.setToolTipText("Selectionnez où sauvegarder votre tableau");
		jfc.showSaveDialog(getParent());
	}
	
	public File getFile() {
		return jfc.getSelectedFile();
	}
}
