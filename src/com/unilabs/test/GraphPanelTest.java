package com.unilabs.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import org.junit.BeforeClass;
import org.junit.Test;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;
import com.unilabs.graph.GraphPanel;
import com.unilabs.graph.GraphWriter;
import com.unilabs.graph.KilometersGraph;
import com.unilabs.graph.PriceGraph;
import com.unilabs.gui.dialog.ResultDialog;
import com.unilabs.gui.dialog.SearchDialog;
import com.unilabs.io.ODSInputStream;
import com.unilabs.io.ODSReader;

/**
 * Case de test pour les graphes <br />
 * Il est possible que les tests échoué de temps en temps tant que les données sont aléatoirement générées à chaque test
 * 
 * @see GraphPanel
 * @see GraphWriter
 * @see UnilabsCar
 * @author Ogier
 *
 */
public class GraphPanelTest {

	private static UnilabsCar car;
	private static UnilabsCar[] cars;

	@BeforeClass
	public static void setUp() throws Exception {
		ODSInputStream in = new ODSInputStream(AverageCalculatorTest.TEST_FILE, null);
		ODSReader reader = new ODSReader(in);
		car = reader.getCar(reader.getNextPlaque(), UnilabsFleetManager.getInstance().getPlateChecker());
		cars = new UnilabsCar[5];
		for(int i = 0 ; i < 5 ; i++) {
			car = reader.getCar( reader.getNextPlaque(), UnilabsFleetManager.getInstance().getPlateChecker());
			cars[i] = car;
		}
		car = cars[1];
	}

	@Test
	public void testKilometersPanel() throws Exception {
		KilometersGraph g = new KilometersGraph(car);
		new GraphPreviewFrame(g);
	}

	@Test
	public void testResultDialog() throws Exception {
		new ResultDialog(cars , "Display Test");
	}

	@Test
	public void testPricePanel() throws Exception {
		PriceGraph g = new PriceGraph(car);
		new GraphPreviewFrame(g);
	}

	@Test
	public void testSearchDialog() throws Exception {
		new SearchDialog(cars);
	}

	@Test
	public void testExportImage() throws Exception {
		KilometersGraph kg = new KilometersGraph(car);
		GraphWriter gw = new GraphWriter(kg);
		File temp = File.createTempFile("UFM", ".png");
		gw.write(temp, "png");
		assertTrue("Le fichier de sortie est introuvable", temp.exists());
		assertFalse("Le fichier de sortie est vide!", temp.length() == 0);
		Desktop.getDesktop().open(temp);
	}

	@SuppressWarnings("serial")
	class GraphPreviewFrame extends JFrame {
		public GraphPreviewFrame(GraphPanel g) {
			setContentPane(g);
			setSize(new Dimension(800, 600));
			setLocationRelativeTo(null);
			setTitle("Graph preview");
			setVisible(true);
		}
	}
}
