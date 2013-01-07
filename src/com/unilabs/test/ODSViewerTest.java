package com.unilabs.test;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import org.jopendocument.dom.XMLFormatVersion;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.model.OpenDocument;
import org.jopendocument.panel.ODSViewerPanel;
import org.junit.Test;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.gui.NonEditableTableModel;
import com.unilabs.processing.TableCreator;

public class ODSViewerTest {

	@Test
	public void PanelTest() throws Exception {
		UnilabsCar[] car = RandomCarGenerator.generateCarArray();
		OpenDocument doc = new OpenDocument();
		ODSViewerPanel panel;
		File temp = File.createTempFile("UFM", ".ods");
		NonEditableTableModel ctm = TableCreator.createPleinTableModel(car);
		SpreadSheet.export(ctm, temp, XMLFormatVersion.getDefault());
		doc.loadFrom(temp);
		panel = new ODSViewerPanel(doc);
		panel.setSize(new Dimension(800, 600));
		JFrame main = new JFrame();
		main.setSize(panel.getSize());
		main.setContentPane(panel);
		main.setVisible(true);
		Thread.sleep(10000);
	}
}
