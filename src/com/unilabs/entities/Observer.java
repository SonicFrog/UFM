package com.unilabs.entities;

/**
 * Interface d�finissant le comportement de l'objet observant
 * 
 * @version 1.0
 * @see Observable
 * @author Ogier
 *
 */
public interface Observer {

	/**
	 * M�thode appell� lorsque l'objet observable est modifi�
	 * @param o
	 * 		L'objet Observable appelant
	 * @param arg
	 * 		Le param�tre qu'il passe
	 */
	public void update(Observable o, Object arg);
}
