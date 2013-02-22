package com.unilabs.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Un dessinateur de cellule de tableau utilisé pour afficher des couleurs dans un tableau de consommation des voitures
 * @version 2.0
 * @author Ogier
 */
public class ConsomationTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Object val = table.getValueAt(row, 2);
		double d = (Double) val;
		if(d > 6.5 || d < 3.6) {
			cell.setBackground(Color.ORANGE);
		} else if(Double.isNaN(d) || Double.isInfinite(d)) {
			cell.setBackground(Color.CYAN);
		} else {
			cell.setBackground(Color.GREEN);
		}

		return cell;
	}
}