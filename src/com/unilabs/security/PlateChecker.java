package com.unilabs.security;

/**
 * Interface d�finissant le comportement pour v�rifier la validit� d'une plaque min�ralogique
 * @author Ogier
 *
 */
public abstract class PlateChecker {
	public abstract boolean validatePlate(String plate);
}
