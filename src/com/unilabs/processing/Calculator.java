package com.unilabs.processing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Interface d�finissant le comportement des classes qui calculent des donn�es � partir des pleins
 * 
 * @author Ogier
 *
 */
public abstract class Calculator {

	private static int INFORMATIONS_COUNT = 6;
	protected ArrayList<Plein> data = new ArrayList<Plein>();
	protected Date start, end;
	
	/**
	 * Tableau contenant les valeurs des informations de ce Calculator sous forme de String
	 */
	private String[] infos = null;
	
	/**
	 * Tableau contenant les intitul�s des valeurs de ce Calculator sous forme de String
	 */
	private String[] infosText = null;
	
	/**
	 * Index de la derni�re informations retourn�e � l'utilisateur
	 */
	private int lastInformationGiven = 0;
	
	/**
	 * Format de date
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("d-M-y");

	/**
	 * Retourne le nom de ce que Calculator calcule
	 * @return
	 */
	public abstract String getName();
	
	public abstract double averageConso();
	public abstract String averageConsoText();
	
	public abstract double averageKilometers();
	public abstract String averageKilometersText();
	
	public abstract double averagePrice();
	public abstract String averagePriceText();
	
	public abstract double averageLiterPrice();
	public abstract String averageLiterPriceText();

	/**
	 * Retourne en jour la dur�e de la p�riode de temps sur laquelle s'�tendent les pleins
	 * @return
	 */
	public int getPeriodOfTime() {
		Date newer, older;
		try {
			newer = getEnd();
			older = getStart();
		} catch (IndexOutOfBoundsException e) {
			return 1;
		}
		if(newer == null || older == null) {
			return 1;
		}
		return (int) Math.ceil((newer.getTime() - older.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * Retourne une Date correspondant au d�but de la p�riode pour laquelle on a des donn�es de plein
	 * @return
	 */
	public Date getStart() {
		if(start == null && data.size() > 0) {
			Collections.sort(data);
			start = data.get(0).getDate();
		}
		return start;
	}
	
	protected void initData(UnilabsCar[] cars) {
		for(UnilabsCar c : cars) {
			for(Plein p : c.getPleins()) {
				data.add(p);
			}
		}
		Collections.sort(data);
	}
	
	/**
	 * Tronque le double pass� en param�tres � deux chiffres apr�s la virgule
	 * @param n
	 * @return
	 */
	protected double getTwoDigitsAfterComma(double n) {
		double buf = n * 100;
		buf = Math.ceil(buf);
		buf = buf / 100;
		return buf;
	}

	/**
	 * Retourne une date correspondant � la fin de la p�riode des pleins
	 * @return
	 */
	public Date getEnd() {
		if(end == null && data.size() > 0) {
			Collections.sort(data);
			end = data.get(data.size() - 1).getDate();
		}
		return end;
	}
	
	public String endValue() {
		return sdf.format(getEnd());
	}
	
	/**
	 * Retourne une string correspondant � la date de d�but de ce Calculator
	 * @return
	 */
	public String startValue() {
		return sdf.format(getStart());
	}
	
	/**
	 * Retourne une string correspondant � la date de fin de ce Calculator
	 * @return
	 */
	public String getEndText() {
		return "Fin";
	}
	
	public String getStartText() {
		return "D�but";
	}
	
	@Override
	public Calculator clone() {
		return this;
	}
	
	public String getNextInformation() {
		if(infos == null) {
			infos = new String[getInformationsCount()];
			infos[0] = String.valueOf(averageConso());
			infos[1] = String.valueOf(averageKilometers());
			infos[2] = String.valueOf(averageLiterPrice());
			infos[3] = String.valueOf(averagePrice());
			infos[4] = startValue();
			infos[5] = endValue();
		}
		if(lastInformationGiven < infos.length) 
			return infos[lastInformationGiven++];
		else
			return infos[lastInformationGiven = 0];
	}
	
	public boolean isNextInformationPrice() {
		if(lastInformationGiven > 2 && lastInformationGiven <= 4) 
			return true;
		return false;
	}
	
	public String getNextInformationText() {
		if(infosText == null) {
			infosText = new String[getInformationsCount()];
			infosText[0] = averageConsoText();
			infosText[1] = averageKilometersText();
			infosText[2] = averageLiterPriceText();
			infosText[3] = averagePriceText();
			infosText[4] = getStartText();
			infosText[5] = getEndText();
		}
		System.out.println("Calculator::getNextInformationsText -> " + infosText[lastInformationGiven]);
		return infosText[lastInformationGiven];
	}

	public int getInformationsCount() {
		return INFORMATIONS_COUNT;
	}
}
