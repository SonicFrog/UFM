package com.unilabs.processing;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe utilitaire 
 * @author Ogier
 */
public abstract class PleinUtils {
	
	/**
	 * Calcule le nombre de mois sur lequel s'étendent les pleins fournis
	 * @param data
	 *			Les pleins pour lesquels on veut calculer le nombre de mois
	 * @return 
	 *			Le nombre sur lequel sont répartis les pleins
	 */
	public static int availableMonthsOfData(Plein[] data) {
		Date start = data[0].getDate(), end = data[0].getDate();
		Calendar startC, endC;
		int months = 0;
		for(Plein p : data) {
			if(p.getDate().after(end)) {
				end = p.getDate();
			}
			if(p.getDate().before(start)) {
				start = p.getDate();
			}
		}
		startC = new GregorianCalendar();
		endC = new GregorianCalendar();
		startC.setTime(start);
		endC.setTime(end);
		
		if(startC.get(Calendar.YEAR) == endC.get(Calendar.YEAR)) {
			months = endC.get(Calendar.MONTH) - startC.get(Calendar.MONTH);
		} else {
			months += startC.getActualMaximum(Calendar.MONTH) - startC.get(Calendar.MONTH);
			months += endC.get(Calendar.MONTH);
			for(int i = startC.get(Calendar.YEAR) ; i < endC.get(Calendar.YEAR) ; i++) {
				months += startC.getActualMaximum(Calendar.MONTH);
			}
		}
		return months + 1;
	}
	
	public static int availableMonthsOfData(UnilabsCar data) {
		return availableMonthsOfData(data.getPleinsArray());
	}
	
	public static int availableMonthsOfData(UnilabsCar[] data) {
		ArrayList<Plein> totalData = new ArrayList<>();
		Plein[] out;
		for(UnilabsCar c : data) {
			totalData.addAll(c.getPleins());
		}
		if(totalData.isEmpty()) {
			throw new RuntimeException("Aucun plein pour calculer les données");
		}
		out = new Plein[totalData.size()];
		totalData.toArray(out);
		return availableMonthsOfData(out);
	}
}