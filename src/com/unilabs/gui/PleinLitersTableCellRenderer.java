package com.unilabs.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class PleinLitersTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -8959118381681562051L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Object val = table.getValueAt(row, 4);
		double d = (Double) val;
		if(d > 20.0) {
			cell.setBackground(Color.RED);
		} else if(Double.isNaN(d)) {
			cell.setBackground(Color.BLUE);
		} else {
			cell.setBackground(Color.GREEN);
		}

		return cell;
	}
}
