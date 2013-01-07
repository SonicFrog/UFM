package com.unilabs.processing;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

public class MaximumFinder {

	private UnilabsCar car;
	
	public MaximumFinder(UnilabsCar c) {
		car = c;
	}
	
	/**
	 * Retourne le plus grand nombre de kilomètres parcourus entre deux pleins
	 * @return
	 */
	public int getMaximumKilometers() {
		int max = 0;
		for(int i = 0 ; i < car.getPleins().size() ; i++) {
			if(car.getPleins().get(i).getTotalKilometers() > max) {
				max = car.getPleins().get(i).getTotalKilometers();
			}
		}
		return max;
	}
	
	/**
	 * Retourne le plus grand prix payé pour un plein
	 */
	public double getMaximumPrice() {
		double max = 0;
		Plein p;
		for(int i = 0 ; i < car.getPleins().size() ; i++) {
			p = car.getPleins().get(i);
			if(p.getPrice() > max) 
				max = p.getPrice();
		}
		return max;
	}
}
