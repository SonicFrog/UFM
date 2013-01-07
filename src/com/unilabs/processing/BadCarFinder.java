package com.unilabs.processing;

import java.util.ArrayList;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Classe de traitement des "mauvaises" voitures
 * Permet de voir les voitures effectuant des pleins à de mauvaises heures ou <br />
 * des pleins trop conséquents
 * 
 * @author Ogier
 * @version 1.1
 * @since 1.0
 * @see UnilabsCar
 *
 */
public class BadCarFinder {

	public final static int HEURE_PLEIN = 12;
	private UnilabsCar[] cars;
	
	public BadCarFinder(UnilabsCar[] cars) {
		this.cars = cars;
	}
	
	/**
	 * Trie les voitures pour trouver celle qui ont effectué au moins un plein après midi
	 * @return
	 */
	public UnilabsCar[] getBadCars() {
		ArrayList<UnilabsCar> out = new ArrayList<UnilabsCar>();
		UnilabsCar[] tabOut;
		for(UnilabsCar car : cars) {
			for(Plein p : car.getPleins()) {
				if(p.getHour() >= HEURE_PLEIN)
					out.add(car);
			}
		}
		tabOut = new UnilabsCar[out.size()];
		for(int i = 0 ; i < tabOut.length ; i++) 
			tabOut[i] = out.get(i);
		return tabOut;
	}
	
	/**
	 * Retourne un tableau contenant toutes les voitures ayant effectué au moins un mauvais plein (après midi)
	 * 
	 * @param cars
	 * 		Le tableau de voiture à tester
	 * @return
	 * 		Le tabeleau de "mauvaise" voiture
	 */
	public static UnilabsCar[] getBadCars(UnilabsCar[] cars) {
		return new BadCarFinder(cars).getBadCars();
	}
	
	/**
	 * Compte le nombre de "mauvais" plein pour une voiture
	 * @param car
	 * 		La voiture pour laquelle on compte le nombre de mauvais plein
	 * @return
	 * 		Le nombre de mauvais plein effectué par cette voiture
	 */
	public static int getBadPleinNumber(UnilabsCar car) {
		int out = 0;
		for(int i = 0 ; i < car.getPleins().size() ; i ++) {
			if(car.getPleins().get(i).getHour() > HEURE_PLEIN) {
				out++;
			}
		}
		return out;
	}
	
	/**
	 * Calcule le nombre de pleins trop conséquents (>20L) pour la voiture selectionnée
	 * 
	 * @param car
	 * 		La voiture pour laquelle on vérifie les pleins
	 * @return 
	 * 		Le nombre de plein trop rempli(s)
	 */
	public static int getBadAmountPleinNumber(UnilabsCar car) {
		int out = 0;
		for(Plein p : car.getPleins()) {
			if(p.getFuelAmount() > 20) {
				out++;
			}
		}
		return out;
	}
	
	/**
	 * Trie les voitures pour récupérer celles qui ont effectué au moins un "mauvais" (trop gros) plein sur la période donnée
	 * 
	 * @param cars
	 * 		La liste de voiture parmi laquelle on cherche
	 * @return
	 * 		La liste de voitures ayant effectué des mauvais pleins
	 */
	public static UnilabsCar[] getBadAmountCar(UnilabsCar[] cars) {
		ArrayList<UnilabsCar> out = new ArrayList<UnilabsCar>();
		for(UnilabsCar c : cars) {
			for(Plein p : c.getPleins())  {
				if(p.getFuelAmount() > 20.0) {
					out.add(c);
					break;
				}
			}
		}
		return out.toArray(new UnilabsCar[0]);
	}
}
