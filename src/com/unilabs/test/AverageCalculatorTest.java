package com.unilabs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.io.ODSInputStream;
import com.unilabs.io.ODSReader;
import com.unilabs.processing.AverageCalculator;

public class AverageCalculatorTest {

	public static final String TEST_FILE = "test.ods";

	private static UnilabsCar car;
	private static AverageCalculator avg;
	
	@BeforeClass
	public static void setUp() throws Exception {
		ODSInputStream odsi = new ODSInputStream(TEST_FILE, null);
		ODSReader reader = new ODSReader(odsi);
		car = reader.getCar(reader.getNextPlaque(), UnilabsFleetManager.getInstance().getPlateChecker());
		car = reader.getCar(reader.getNextPlaque(), UnilabsFleetManager.getInstance().getPlateChecker());
		avg = new AverageCalculator(car);
	}
	
	@Test
	public void basicTest() throws Exception {
		assertEquals("GE 230689", car.getPlaque());
		assertEquals(3, car.getPleins().size());
	}
	
	@Test
	public void kilometersTest() throws Exception {
		assertEquals(53.125, avg.averageKilometers(), 0.1);
	}
	
	@Test
	public void consoTest() throws Exception {
		assertEquals(18.6, avg.averageConso(), 0.1);
	}

	@Test
	public void periofOfTimeTest() throws Exception {
		System.out.println(avg.getPeriodOfTime());
		assertTrue("Valeur impossible : " + avg.getPeriodOfTime(), avg.getPeriodOfTime() > 0);
	}
	
	@Test
	public void literPriceTest() throws Exception {
		assertEquals(1.65, avg.averageLiterPrice(), 0.01);
	}
	
	@Test
	public void priceTest() throws Exception {
		assertEquals(15.11, avg.averagePrice(), 0.01);
	}
}
