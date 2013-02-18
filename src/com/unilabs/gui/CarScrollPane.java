package com.unilabs.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;fdsfdsfdsfs

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.processing.TableCreator;

/**
 * Panel contenant le tableau d'informations sur toutes les voitures
 * @version 1.0
 * @author Ogier
 *
 */
public class CarScrollPane extends JPanel {

	private static final long serialVersionUID = 2117002516059697632L;
	private JTable table;
	private NonEditableTableModel model;
	private JScrollPane content;
	private boolean drawn = false;
	
	public CarScrollPane() {
		setLayout(new BorderLayout());
		makeTable();
		build();
		listener();
		drawn = true;
	}
	
	private void build() {
		content = new JScrollPane(table);
		add(content, BorderLayout.CENTER);
	}
	
	/**
	 * Ajout du listener sur les colonnes du JScrollPane
	 */
	private void listener() {
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setSelectedColum(e);
			}
		});
	}
	
	/**
	 * Cr�e le tableau et impl�mente le tri des lignes
	 */
	private void makeTable() {
		table = new JTable(model = TableCreator.createAverageTableModel(UnilabsFleetManager.getInstance().getCars()));
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
	}
	
	private void update() {
		Enumeration<TableColumn> tc;
		table.setModel(model = TableCreator.createAverageTableModel(UnilabsFleetManager.getInstance().getCars()));
		tc = table.getColumnModel().getColumns();
		for( ; tc.hasMoreElements() ; ) {
			tc.nextElement().setCellRenderer(new ConsomationTableCellRenderer());
		}
		table.repaint();
		System.out.println("TableModel r�-instanci�");
	}
	
	public void repaint() {
		if(drawn) {
			update();
		}
		super.repaint();
	}
	
	/**
	 * Retourne l'index du tableau qui est selectionn�
	 * @return
	 */
	public int getSelectedCarIndex() {
		System.out.println("Asking for selected table index (" + table.getSelectedRow() + ") to Table " + table.hashCode());
		return table.getSelectedRow();
	}
	
	public void setSelectedColum(MouseEvent e) {
		int column = table.columnAtPoint(e.getPoint());
		model.setSelectedColumn(column);
		table.paint(table.getGraphics());
	}
	
	public String getSelectedCarPlaque() {
		String out = (String) model.getValueAt(table.getSelectedRow(), 1);
		System.out.println("Asking for plate of car in table[" + table.getSelectedRow() + ",1] " + table.hashCode());
		System.out.println("It is " + out);
		return out;
	}

	public JTable getTable() {
		return table;
	}
}
