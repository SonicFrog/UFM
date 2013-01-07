package com.unilabs.security;

/**
 * Interface définissant le comportement pour vérifier la validité d'une plaque minéralogique
 * @author Ogier
 *
 */
public interface PlateChecker {
	public boolean validatePlate(String plate);
}
