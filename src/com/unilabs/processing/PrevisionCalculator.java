package com.unilabs.processing;

import com.unilabs.entities.UnilabsCar;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Calculateur pour des prévisions des distances parcourues par les voitures
 * @author Ogier
 * @version 1.0
 */
public class PrevisionCalculator extends Calculator {
	
	public static final int DEFAULT_PREVISION_MONTHS = 12;
	
	private UnilabsCar carData;
	private int months;
	private int monthsOfData;
	
	/**
	 * Calculateur pour les données moyennes que l'on a
	 */
	private Calculator avgForData;
	
	private Date starDate;
	private Date endDate;
	/**
	 * Instancie un calculateur de prévision avec la durée de prévision par défaut
	 * @param data
	 *		Les voitures pour lesquels on fait la prévision
	 */
	public PrevisionCalculator(UnilabsCar data) {
		this.carData = data;
		months = DEFAULT_PREVISION_MONTHS;
		computeStartDate();
	}
	
	/**
	 * Instancie un calculateur de prévision pour la durée spécifiée
	 * @param data
	 *		Les voitures pour lesquels on fait la prévision
	 * @param months 
	 *		La durée pour laquelle on prévoit
	 */
	public PrevisionCalculator(UnilabsCar data, int months) {
		this.carData = data;
		this.months = months;
		computeStartDate();
	}
	
	public PrevisionCalculator(UnilabsCar data, int months, Date start) {
		this.carData = data;
		this.months = months;
		this.starDate = start;
	}
	
	private void computeStartDate() {
		Calendar g = new GregorianCalendar();
		g.setTime(starDate);
		g.add(Calendar.MONTH, months);
		starDate = carData.getPleins().get(0).getDate();
		endDate = new Date(g.getTimeInMillis());
		monthsOfData = PleinUtils.availableMonthsOfData(carData);
	}

	@Override
	public String getName() {
		return "Prévisions pour " + carData.getPlaque() + " sur " + months + " mois";
	}

	@Override
	public double averageConso() {
		double consoForData = avgForData.averageConso();
		double times = months / monthsOfData;
		
		return consoForData * times;
	}

	@Override
	public String averageConsoText() {
		return "Prévision de consommation";
	}

	@Override
	public double averageKilometers() {
		double kiloForData = avgForData.averageKilometers();
		double times = months / monthsOfData;
		return (kiloForData * times);
	}

	@Override
	public String averageKilometersText() {
		return "Prévisions kilomètriques";
	}

	@Override
	public double averagePrice() {
		double priceForData = avgForData.averagePrice();
		double times = months / monthsOfData;
		return priceForData * times;
	}

	@Override
	public String averagePriceText() {
		return "Prévisions de coût";
	}

	@Override
	public double averageLiterPrice() {
		return avgForData.averageLiterPrice();
	}

	@Override
	public String averageLiterPriceText() {
		return "Prix du litre";
	}
}
