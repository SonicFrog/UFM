package com.unilabs.security;

import java.io.Serializable;

public class DummyPlate implements PlateChecker, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean validatePlate(String plate) {
		return true;
	}

}
