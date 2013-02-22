package com.unilabs.gui;

import java.util.Comparator;
import java.util.Vector;

/**
 * Comparateur utilisé pour trier les tableaux de voitures selon la colonne selectionnée
 * @author Ogier
 */
public class TableComparator implements Comparator<Object> {

	private int column;

	public TableComparator(int column) {
		this.column = column;
	}

	public int compare(Object o1, Object o2) {
		o1 = ((Vector<?>) o1).elementAt(column);
		o2 = ((Vector<?>) o2).elementAt(column);
		if(o1 instanceof Double && o2 instanceof Double) {
			return ((Double) o1).compareTo(((Double) o2));
		}
		if(o1 instanceof String && o2 instanceof String) {
			return ((String) o1).compareTo(((String) o2));
		}
		if(o1 instanceof Integer && o2 instanceof Integer) {
			return ((Integer) o1).compareTo(((Integer) o2));
		}
		throw new RuntimeException("Comparaison entre deux lignes du tableau impossible");
	}
}
