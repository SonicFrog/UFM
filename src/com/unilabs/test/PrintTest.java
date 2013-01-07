package com.unilabs.test;

import java.util.Random;
import java.util.Vector;

import org.junit.Test;

import com.unilabs.gui.NonEditableTableModel;
import com.unilabs.processing.PrintManager;

public class PrintTest {

	@Test
	public void printODSTest() throws Exception {
		NonEditableTableModel netm = new NonEditableTableModel();
		Vector<String> arg;
		netm.addColumn("Prout");
		netm.addColumn("Hoho");
		for(int i = 0 ; i < 10 ; i++) {
			arg = new Vector<String>();
			arg.add(String.valueOf(new Random().nextInt()));
			arg.add(String.valueOf(new Random().nextInt()));
			netm.addRow(arg);
		}
		new PrintManager(netm);
	}
}
