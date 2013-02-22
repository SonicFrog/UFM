package com.unilabs.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Panel pour un affichage des kilomètres parcourues pour chaque plein pour une voiture
 * 
 * @see JPanel
 * @see Graphics
 * @see Plein
 * @see UnilabsCar
 * @see GraphPanel
 * @since 1.1
 * @version 1.0
 * @author Ogier
 *
 */
@SuppressWarnings("serial")
public class KilometersGraph extends GraphPanel {
	
	/**
	 * Instancie un dessinateur de graphe pour le nombre de kilomètres pour cette voiture
	 * @param data 
	 *		La voiture pour laquelle on dessine le graphe
	 */
	public KilometersGraph(UnilabsCar data) {
		super(data);
		int buf;
		for(int i = 1; i < car.getPleins().size() ; i++) {
			if((buf = car.getPleins().get(i).getRelativeKilometers(car.getPleins().get(i - 1))) > maximum) {
				maximum = buf;
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawString("Nombre de kilomètre parcourus entre chaque plein pour " + car.getPlaque(), getWidth() / 2 - 50, OFFSET /2);
	}

	@Override
	protected void drawPoints(Graphics g) {
		Plein previous = null;
		double km;
		ArrayList<Plein> e = car.getPleins();
		g.setColor(Color.RED);
		int xcoord = OFFSET;
		int placeForOne =  (getWidth() - OFFSET * 2) / e.size();
		int max = maximum;
		int maxHeight = getHeight() - OFFSET * 2;
		for(Plein p : e) {
			if(previous == null) {
				km = 0;
			}
			else {
				km = previous.getRelativeKilometers(p);
			}
			double ratio = (double) km / (double) max;
			int y = (int) Math.floor(ratio * maxHeight) + OFFSET;
			drawPoint(new Point(xcoord, y), g);
			xcoord += placeForOne;
			previous = p;
		}
	}
}
