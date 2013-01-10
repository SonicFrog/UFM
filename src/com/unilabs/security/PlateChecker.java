package com.unilabs.security;

/**
 * Interface définissant le comportement pour vérifier la validité d'une plaque minéralogique
 * @author Ogier
 *
 */
public abstract class PlateChecker {
	public abstract boolean validatePlate(String plate);
}
