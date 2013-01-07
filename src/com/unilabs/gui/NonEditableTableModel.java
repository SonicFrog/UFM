package com.unilabs.gui;

import java.util.Collections;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * TableModel n'autorisant pas la modification des cellules
 * @see DefaultTableModel
 * @version 1.0
 * @author Ogier
 *
 */
public class NonEditableTableModel extends DefaultTableModel {

	private static final long serialVersionUID = -1735550168735673165L;

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/**
	 * Trie les lignes de ce TableModel par rapport � la colonne selectionn�e
	 * @param column
	 * 		L'index de la colonne par rapport � laquelle le tri doit �tre fait
	 */
	public void setSelectedColumn(int column) {
		System.out.println("Sorting table with col " + column);
		Collections.sort((Vector<?>) dataVector, new TableComparator(column));
	}
}