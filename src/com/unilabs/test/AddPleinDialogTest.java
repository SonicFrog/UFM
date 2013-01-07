package com.unilabs.test;

import org.junit.Test;

import com.unilabs.entities.Plein;
import com.unilabs.gui.dialog.AddPleinDialog;

public class AddPleinDialogTest {

	
	@Test
	public void addPleinTest() {
		
	}
	
	@Test
	public void editPleinTest() {
		Plein p = new Plein("10-11-2009", null, 48.50, 27.45, 1200);
		new AddPleinDialog(p);
	}
}
