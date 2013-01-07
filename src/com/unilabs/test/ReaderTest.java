package com.unilabs.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Date;

import org.junit.Test;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.io.ODSInputStream;
import com.unilabs.io.ODSReader;

/**
 * Test case for the reader
 * @author Ogier
 *
 */
public class ReaderTest {
	
	@Test (expected=java.io.EOFException.class)
	public void readPleinTest() throws Exception {
		InputStream in = new ODSInputStream(ODSInputStreamTest.TEST_FILE, null);
		ODSReader reader = new ODSReader(in);
		int i = 0;
		Plein p = reader.readPlein();
		do {
			i++;
			assertTrue("Plein " + i, p.getFuelAmount() >= 0);
			assertTrue(p.getDate().before(new Date(System.currentTimeMillis())));
			assertTrue("Plein avec " + p.getTotalKilometers(), p.getTotalKilometers() >= 0);
			p = reader.readPlein();
		} while(true);
	}

	@Test
	public void readCarTest() throws Exception {
		ODSInputStream in = new ODSInputStream(ODSInputStreamTest.TEST_FILE, null);
		ODSReader r = new ODSReader(in);
		UnilabsCar c = r.getCar("FR 30307", UnilabsFleetManager.getInstance().getPlateChecker());
		assertTrue(c.getNumeroClient() > 0);
		assertFalse(c.getPlaque().isEmpty());
		assertNotNull(c.getPleins());
	}
	
	@Test
	public void NextPlateTest() throws Exception {
		InputStream in = new ODSInputStream(ODSInputStreamTest.TEST_FILE, null);
		ODSReader reader = new ODSReader(in);
		String plate;
		for(int i = 1 ; i < reader.getCarNumber() ; i++) {
			plate = reader.getNextPlaque();
			System.out.println(plate);
			assertNotNull("Plate " + i + " was null", plate);
			assertTrue(UnilabsFleetManager.getInstance().getPlateChecker().validatePlate(plate));
		}
	}

	@Test
	public void refreshCarTest() throws Exception {
		ODSInputStream in = new ODSInputStream(ODSInputStreamTest.TEST_FILE, null);
		ODSReader reader = new ODSReader(in);
		UnilabsCar c = reader.getCar("BE 617494", UnilabsFleetManager.getInstance().getPlateChecker());
		reader.refreshCar(c);
	}

	@Test (expected=java.io.FileNotFoundException.class)
	public void readCarNotFoundTest() throws Exception {
		InputStream in = new ODSInputStream(ODSInputStreamTest.TEST_FILE, null);
		ODSReader reader = new ODSReader(in);
		reader.getCar("UR 999999", UnilabsFleetManager.getInstance().getPlateChecker());
	}
}
