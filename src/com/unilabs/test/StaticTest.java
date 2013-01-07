package com.unilabs.test;

import org.junit.Test;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.processing.AverageCalculator;
import com.unilabs.processing.Calculator;
import com.unilabs.security.SwissPlate;

public class StaticTest {

	@Test
	public void staticAttribTest() {
		UnilabsCar car = new UnilabsCar("GE 1111", 111, new SwissPlate());
		car.addPlein(new Plein("10-10-2012", null, 0, 0, 0));
		Calculator c = new AverageCalculator(car);
		System.out.println(c.averageConsoText());
	}
}
