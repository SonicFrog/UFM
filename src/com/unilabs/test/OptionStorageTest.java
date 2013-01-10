package com.unilabs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.EOFException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.io.ODSInputStream;
import com.unilabs.io.ODSReader;
import com.unilabs.io.Reader;
import com.unilabs.io.Writer;
import com.unilabs.io.XMLWriter;
import com.unilabs.options.OptionStorage;
import com.unilabs.security.PlateChecker;
import com.unilabs.security.SwissPlate;

public class OptionStorageTest {

	private final static String WRITER_CLASS = "com.unilabs.io.XMLWriter";
	private static final String READER_CLASS = "com.unilabs.io.XMLReader";

	private static PlateChecker pc = new SwissPlate();

	private static UnilabsCar[] car;
	private static int singleTestIndex = 10;
	private static Properties p = new Properties();

	@BeforeClass
	public static void setupData() throws Exception {
		InputStream in = new ODSInputStream(ODSInputStreamTest.TEST_FILE, null);
		ODSReader r = new ODSReader(in);
		car = new UnilabsCar[r.getCarNumber()];
		p.setProperty(OptionStorage.WRITER_CLASS_KEY, WRITER_CLASS);
		p.setProperty(OptionStorage.READER_CLASS_KEY, READER_CLASS);
		for(int i = 0 ; i < r.getCarNumber() ; i++) {
			System.out.println(i);
			car[i] = r.getCar(r.getNextPlaque(), pc);
		}
		assertTrue(singleTestIndex > 1);
	}

	@Test
	public void writeInstantiateTest() throws Exception {
		Vector args = new Vector();
		args.add(File.createTempFile("ufm", ".xml"));
		OptionStorage os = new OptionStorage(p);
		Writer w = os.getWriter(args);
		assertTrue(w.getClass().getName().equals(WRITER_CLASS));
	}

	@Test
	public void readerInstantiateTest() throws Exception {
		Vector args = new Vector();
		args.add(File.createTempFile("ufm", ".xml"));
		args.add(pc);
		Writer w = new XMLWriter((File) args.get(0));
		w.writeCar(car[singleTestIndex]);
		w.flush();
		OptionStorage os = new OptionStorage(p);
		Reader r = os.getReader(args);
		assertTrue(r.getClass().getName().equals(READER_CLASS));
	}

	@Test
	public void defaultValueTest() throws Exception {
		OptionStorage os = new OptionStorage(p);
		assertEquals(OptionStorage.DEFAULT_CURRENCY, os.getCurrency());
	}
	
	@Test(timeout=400)
	public void performanceTest() throws Exception {
		Writer w;
		Reader r;
		ArrayList<UnilabsCar> read = new ArrayList<UnilabsCar>();
		Vector args = new Vector();
		OptionStorage os = new OptionStorage(p);
		args.add(File.createTempFile("ufm", ".xml"));

		w = os.getWriter(args);

		for(UnilabsCar c : car) {
			w.writeCar(c);
		}
		w.flush();
		args.add(pc);
		r = os.getReader(args);
		try {
			while(true) {
				read.add(r.readCar());
			}
		} catch (EOFException e) {
			
		}
		assertEquals(car.length, read.size());
		for(int i = 0 ; i < car.length ; i++) 
			assertTrue(car[i].equals(read.get(i)));
	}
}
