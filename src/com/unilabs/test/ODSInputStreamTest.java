package com.unilabs.test;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.junit.Test;

import com.unilabs.io.ODSInputStream;

public class ODSInputStreamTest {

	public final static String TEST_FILE = "test.ods";

	@Test
	public void instantiateTest() throws IOException {
		ODSInputStream in = new ODSInputStream(TEST_FILE, null);
		in.close();
	}

	@Test
	public void columnFindTest() throws Exception {
		ODSInputStream in = new ODSInputStream(TEST_FILE, null);
		String[] columns = in.getColumns();
		String[] expected = { "B" , "C", "E", "F", "G", "K"};
		assertEquals(expected.length, columns.length);
		for(int i = 0 ; i < columns.length ; i++) {
			assertEquals(expected[i], columns[i]);
		}
		in.close();
	}

	@Test
	public  void firstColumnTest() throws Exception {
		ODSInputStream in = new ODSInputStream(TEST_FILE, null);
		DataInputStream dis = new DataInputStream(in);
		byte[] buffer;
		String[] expected = { "347", "FR 30307", "29-02-2012", "77.29" , "43.3", "630" };
		for(int i = 0 ; i < expected.length ; i++) {
			buffer = new byte[dis.readInt()];
			for(int j = 0 ; j < buffer.length ; j++) {
				buffer[j] = dis.readByte();
			}
			assertEquals(expected[i], new String(buffer , Charset.forName("ASCII")));
		}
		dis.close();
	}

	@SuppressWarnings("resource")
	@Test (expected=java.io.EOFException.class)
	public void columnsTest() throws Exception {
		int index = 3;
		byte[] buffer;
		String cell;
		ODSInputStream in = new ODSInputStream(TEST_FILE, null);
		DataInputStream dis = new DataInputStream(in);
		String[] rcol = { "B" , "C", "E", "F" , "G", "K"};
		SpreadSheet sh = SpreadSheet.createFromFile(new File(TEST_FILE));
		while(true) {
			for(int i = 0 ; i < rcol.length ; i++) {
				buffer = new byte[dis.readInt()];
				dis.read(buffer);
				cell = rcol[i] + String.valueOf(index);
				assertEquals(sh.getCellAt(cell).getTextValue(), new String(buffer, Charset.forName("ASCII")));
			}
		}
	}

	@SuppressWarnings("resource")
	@Test(expected=java.io.EOFException.class)
	public void endTest() throws Exception {
		ODSInputStream in = new ODSInputStream(TEST_FILE, null);
		while(true) {
			in.read();
		}
	}

	@SuppressWarnings("resource")
	@Test(expected=java.io.EOFException.class)
	public void dataFlowTest() throws Exception {
		ODSInputStream in = new ODSInputStream(TEST_FILE, null);
		DataInputStream dis = new DataInputStream(in);
		byte[] buffer;
		int size;
		while(true) {
			size = dis.readInt();
			buffer = new byte[size];
			dis.read(buffer);
		}
	}
}
