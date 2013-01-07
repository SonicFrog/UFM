package com.unilabs.processing;

import java.util.ArrayList;
import java.util.Collections;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Classe qui traite les données d'une voiture pour faire les calculs de moyenne
 * 
 * @author Ogier
 * @version 1.0
 * @see UnilabsCar
 * @see Plein
 *
 */
public class AverageCalculator extends Calculator {

	protected UnilabsCar car;
	private static String CONSO_TEXT = "L/100km";
	private static String LITER_PRICE_TEXT = "Prix du litre";
	private static String PRICE_TEXT = "Coût par jour";
	private static String KILOMETERS_TEXT = "Kilomètres par jour";

	public AverageCalculator(UnilabsCar car) {
		this.car = car;
		data = new ArrayList<Plein>();
		for(Plein plein : car.getPleins()) {
			data.add(plein);
		}
		Collections.sort(data);
	}
	
	public String getName() {
		return "Informations pour " + car.getPlaque();
	}

	/**
	 * Retourne en litres pour cent kilomètres la consommation de la voiture sur les pleins fournis en entrée
	 * @return
	 * 		La consommation en litre pour cent kilomètres
	 */
	public double averageConso() {
		//TODO demander plus de précisions par rapport à comment on calcule ça (problème de premier et  dernier plein)
		double fuel = 0;
		double kilometers = 0;
		for (int i = 1 ; i < data.size() ; i++) {
			fuel += data.get(i).getFuelAmount();
			kilometers += data.get(i - 1).getRelativeKilometers(data.get(i));
		}
		return getTwoDigitsAfterComma(fuel / (kilometers /100));
	}

	/**
	 * Retourne le cout en essence de la voiture sur les pleins donnés par jour
	 * @return
	 */
	public double averagePrice() {
		double price = 0 ;
		for(Plein p : data) {
			price += p.getPrice();
		}
		return getTwoDigitsAfterComma(price / getPeriodOfTime());
	}

	/**
	 * Retourne le nombre de kilomètres effectué par jour sur la période de plein donnés
	 * @return
	 */
	public double averageKilometers() {
		double kilometres = 0;
		Plein first, last;
		try {
			first = data.get(0);
			last = data.get(data.size() - 1);
			kilometres = first.getRelativeKilometers(last);
			return getTwoDigitsAfterComma(kilometres / getPeriodOfTime());
		} catch (IndexOutOfBoundsException e) {
			return Double.NaN;
		}
	}

	/**
	 * Calcule le prix moyen par litre sur la période de plein donnée
	 * @return
	 */
	public double averageLiterPrice() {
		double totalLitters = 0;
		double totalPrice = 0;

		for(Plein p : data) {
			totalLitters += p.getFuelAmount();
			totalPrice += p.getPrice();
		}
		return getTwoDigitsAfterComma(totalPrice / totalLitters);
	}

	@Override
	public String toString() {
		String str = "AverageCalculator with " + data.size() + " data to process :\n";
		str += "Average kilometers per day : " + averageKilometers() + "\n";
		str += "Average liters per 100 kilometers " + averageConso() + "\n";
		str += "Total cost for the period given : " + averagePrice() + "\n";
		str += "Period of time : " + getPeriodOfTime() + "\n";
		return str;
	}

	@Override
	public String averageConsoText() {
		return CONSO_TEXT;
	}

	@Override
	public String averageKilometersText() {
		return KILOMETERS_TEXT;
	}

	@Override
	public String averagePriceText() {
		return PRICE_TEXT;
	}

	@Override
	public String averageLiterPriceText() {
		return LITER_PRICE_TEXT;
	}
}
