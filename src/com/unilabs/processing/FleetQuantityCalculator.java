package com.unilabs.processing;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Calcul des quantités pour toute une flotte de voiture
 * Dépend des fonctionnalités de AverageCalculator pour plusieurs des fonctions mises en place
 * 
 * @see Plein
 * @see UnilabsCar
 * @author Ogier
 * @version 1.0
 *
 */
public class FleetQuantityCalculator extends Calculator {

	private UnilabsCar[] cars;
	
	private double fuelAmount = 0;
	private double price = 0;
	private int kilometers = 0;
	private double literPrice = 0.0;
	
	private static String CONSO_TEXT = "Consommation en litres";
	private static String LITER_PRICE_TEXT = "Prix du litre";
	private static String PRICE_TEXT = "Coût total";
	private static String KILOMETERS_TEXT = "Kilomètres parcourus";
	
	public FleetQuantityCalculator(UnilabsCar[] cars) {
		this.cars = cars;
		initData(cars);
	}
	
	public String getName() {
		return "Totaux pour la flotte ("+ cars.length + " voitures) sur " + getPeriodOfTime() + " jours";
	}
	
	/**
	 * Calcule la quantité d'essence utilisé sur la période donnée
	 * @return
	 */
	public double averageConso() {
		if(fuelAmount == 0.0) {
			for(UnilabsCar c : cars) {
				for(Plein p : c.getPleins()) {
					fuelAmount += p.getFuelAmount();
				}
			}
		}
		return getTwoDigitsAfterComma(fuelAmount);
	}
	
	/**
	 * Calcule le coût total en essence pour toute la flotte
	 * @return
	 */
	public double averagePrice() {
		if(price == 0.0) {
			for(UnilabsCar c: cars) {
				for(Plein p : c.getPleins()) {
					price += p.getPrice();
				}
			}
		}
		return getTwoDigitsAfterComma(price);
	}
	
	
	public double averageLiterPrice() {
		AverageCalculator avg;
		int total = 0;
		if(literPrice == 0.0) {
			for(UnilabsCar c : cars) {
				avg = new AverageCalculator(c);
				literPrice += avg.averageLiterPrice();
				total++;
			}
			literPrice = literPrice / total;
		}
		return getTwoDigitsAfterComma(literPrice);
	}
	
	/**
	 * Calcule le nombre total de kilomètres parcourus par la flotte de voiture
	 * @return
	 */
	public double averageKilometers() {
		if(kilometers == 0) {
			for(UnilabsCar c : cars) {
				for(int i = 1 ; i < c.getPleins().size() ; i++){
					//TODO vérifier si c'est dans le bon sens
					kilometers += c.getPleins().get(i - 1).getRelativeKilometers(c.getPleins().get(i));
				}
			}
		}
		return kilometers;
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
