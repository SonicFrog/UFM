package com.unilabs.entities;

/**
 * Interface définissant le comportement des objets observable par d'autres
 * @version 1.0
 * @see Observer
 * @see Observer#update(Observable, Object)
 * @author Ogier
 *
 */
public interface Observable {

	/**
	 * Ajoute un observateur à cette objet
	 * @param o
	 */
	public void addObserver(Observer o);
	
	/**
	 * Appelle la méthode update de tout les observateur de cet objet avec comme paramètre null <br />
	 * Equivalent de notifyObservers(null)
	 * @see Observable#notifyObservers(Object)
	 */
	public void notifyObservers();
	
	/**
	 * Appelle la méthode update de chaque observateur de cet objet avec le paramètre fourni
	 * @see Observer#update(Observable, Object)
	 * @param arg
	 */
	public void notifyObservers(Object arg);
}
