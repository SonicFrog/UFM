package com.unilabs.gui.dialog;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.processing.TableCreator;

/**
 * Boîte de dialogue d'affichage de résultat de recherche
 * @see UnilabsCar
 * @see JDialog
 * @version 1.0
 * @author Ogier
 *
 */
public class ResultDialog extends JDialog {
	
	private static final long serialVersionUID = -6895615412552932042L;
	private JScrollPane jsp;
	private JTable table;
	
	public ResultDialog(UnilabsCar cars[], String description) {
		setTitle(description);
		setModal(true);
		setSize(new Dimension(800, 600));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		init(cars);
		setContentPane(jsp);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void init(UnilabsCar[] cars) {
		table = TableCreator.createPleinTable(cars);
		setMinimumSize(new Dimension(540, 320));
		table.getTableHeader().setReorderingAllowed(false);
		jsp = new JScrollPane(table);
	}
}
