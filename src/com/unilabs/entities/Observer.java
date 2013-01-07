package com.unilabs.entities;

/**
 * Interface définissant le comportement de l'objet observant
 * 
 * @version 1.0
 * @see Observable
 * @author Ogier
 *
 */
public interface Observer {

	/**
	 * Méthode appellé lorsque l'objet observable est modifié
	 * @param o
	 * 		L'objet Observable appelant
	 * @param arg
	 * 		Le paramètre qu'il passe
	 */
	public void update(Observable o, Object arg);
}
