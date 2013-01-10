package com.unilabs.security;

import java.io.Serializable;

/**
 * Implémentation de PlateChecker pour les plaques suisses
 * @see PlateChecker
 * @author Ogier
 * @version 1.0
 *
 */
public class SwissPlate extends PlateChecker implements Serializable {

	private static final long serialVersionUID = 472104549971581569L;

	public boolean validatePlate(String plate) {
		if(plate.length() < 4)
			return false;
		String canton = plate.substring(0, 2);
		String id = plate.substring(3);
		for(int i = 0 ; i < canton.length() ; i++) {
			if(!Character.isLetter(canton.charAt(i)))
				return false;
		}
		for(int i = 0 ; i < id.length() ; i++) {
			if(!Character.isDigit(id.charAt(i)))
				return false;
		}
		return true;
	}
}
