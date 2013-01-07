package com.unilabs.processing;

import com.unilabs.entities.UnilabsCar;

/**
 * Classe de calcul des moyennes pour une période de pleins sur un ensemble de voiture
 * 
 * @author Ogier
 * @version 1.0
 *
 */
public class FleetAverageCalculator extends Calculator {

	private UnilabsCar[] cars;
	private static String CONSO_TEXT = "L/100 km moyen";
	private static String KILOMETERS_TEXT = "Kilomètres par jour moyen";
	private static String PRICE_TEXT = "Prix moyen par jour";
	private static String LITER_PRICE_TEXT ="Prix moyen du litre";

	public FleetAverageCalculator(UnilabsCar[] cars) {
		this.cars = cars;
		initData(cars);
	}

	public String getName() {
		return "Moyenne pour la flotte (" + cars.length + " voitures) sur " + getPeriodOfTime() + " jours";
	}

	/**
	 * Calcule la consommation moyenne par voiture sur l'ensemble de la flotte donnée
	 * 
	 * @return
	 * 		La consommation en litres pour cent kilomètres
	 */
	@Override
	public double averageConso() {
		double out = 0;
		double buffer;
		int invalidCars = 0;
		for(UnilabsCar c : cars) {
			buffer = new AverageCalculator(c).averageConso();
			if(Double.isNaN(buffer)) {
				System.out.println("Ignoring data for " + c.getPlaque());
				invalidCars++;
			} else
				out += buffer;
		}
		try {
			return getTwoDigitsAfterComma(out / (cars.length - invalidCars));
		} catch (ArithmeticException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Pas assez de données pour faire les calculs");
		}
	}

	/**
	 * Calcule le nombre moyen de kilomètres parcourus par jour sur l'ensemble de la flotte fournie
	 * 
	 * @return
	 * 		Le nombre de kilomètres moyen par voiture par jour
	 */
	@Override
	public double averageKilometers() {
		double out = 0;
		double buffer;
		for(UnilabsCar c : cars) {
			out += (Double.isNaN(buffer = new AverageCalculator(c).averageKilometers()) ? 0.0 : buffer);
		}
		try {
			System.out.println(out + "/" + cars.length);
			return getTwoDigitsAfterComma(out / cars.length);
		} catch (ArithmeticException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Pas assez de données pour faire les calculs");
		}
	}

	/**
	 * Calcule le cout en essence moyen de toute les voitures de la flotte sur la période donnée
	 * 
	 * @return
	 * 		Le prix 
	 */
	@Override
	public double averagePrice() {
		double out = 0;
		double buffer;
		int invalidCars = 0;
		for(UnilabsCar c : cars) {
			if(Double.isInfinite(buffer = new AverageCalculator(c).averagePrice())){
				System.out.println("Ignoring data for " + c.getPlaque());
				invalidCars++;
			} else
				out += buffer;
		}
		return getTwoDigitsAfterComma(out / (cars.length - invalidCars));
	}

	/**
	 * Retourne le prix moyen du litre pour toutes les voitures de la flotte
	 * 
	 * @return 
	 * 		Le prix moyen du litre
	 */
	@Override
	public double averageLiterPrice() {
		double out = 0;
		double buffer;
		int invalidCars = 0;
		for(UnilabsCar c: cars) {
			if(Double.isNaN(buffer = new AverageCalculator(c).averageLiterPrice())) {
				System.out.println("Ignoring data for " + c.getPlaque());
				invalidCars++;
			} else
				out += buffer;
		}
		try {
			return getTwoDigitsAfterComma(out / (cars.length - invalidCars));
		} catch (ArithmeticException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Pas assez de données pour faire les calculs");
		}
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
