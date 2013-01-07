package com.unilabs.entities;

/**
 * Interface d�finissant le comportement des objets observable par d'autres
 * @version 1.0
 * @see Observer
 * @see Observer#update(Observable, Object)
 * @author Ogier
 *
 */
public interface Observable {

	/**
	 * Ajoute un observateur � cette objet
	 * @param o
	 */
	public void addObserver(Observer o);
	
	/**
	 * Appelle la m�thode update de tout les observateur de cet objet avec comme param�tre null <br />
	 * Equivalent de notifyObservers(null)
	 * @see Observable#notifyObservers(Object)
	 */
	public void notifyObservers();
	
	/**
	 * Appelle la m�thode update de chaque observateur de cet objet avec le param�tre fourni
	 * @see Observer#update(Observable, Object)
	 * @param arg
	 */
	public void notifyObservers(Object arg);
}
