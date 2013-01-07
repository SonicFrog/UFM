package com.unilabs.test;

import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import org.junit.Test;

import com.unilabs.gui.dialog.AddCarDialog;
import com.unilabs.gui.dialog.AddPleinDialog;
import com.unilabs.gui.dialog.CarComparisonDialog;
import com.unilabs.gui.dialog.CarInfoDialog;
import com.unilabs.gui.dialog.DeleteDialog;

public class DialogTests {

	@Test
	public void deleteTest() throws Exception {
		new DeleteDialog("GE 245654");
		confirm();
	}
	
	@Test
	public void addTest() throws Exception {
		new AddCarDialog();
		confirm();
	}
	
	@Test
	public void comparisonTest() throws Exception {
		new CarComparisonDialog(RandomCarGenerator.generateCar(), RandomCarGenerator.generateCar());
		confirm();
	}
	
	@Test
	public void carInfoTest() throws Exception {
		new CarInfoDialog(null, RandomCarGenerator.generateCar());
		confirm();
	}
	
	@Test
	public void addPleinTest() throws Exception {
		new AddPleinDialog(null, "");
		confirm();
	}
	public void confirm() {
		int i = JOptionPane.showConfirmDialog(null, "Y a-t-il des problèmes d'affichage sur ce dialogue ?", "Confirmation", JOptionPane.YES_NO_OPTION);
		assertTrue("Le dialogue ne s'affiche pas correctement", i == JOptionPane.NO_OPTION);
	}
}
