package com.unilabs.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Classe gérant l'affichage d'un graphique avec les prix par pleins pour une voiture sur une période en particulier
 * 
 * @version 1.0
 * @see GraphPanel
 * @author Ogier
 *
 */
@SuppressWarnings("serial")
public class PriceGraph extends GraphPanel {

	public PriceGraph(UnilabsCar data) {
		super(data);
		for(Plein p : data.getPleins()) {
			if(p.getPrice() > maximum)
				maximum = (int) Math.ceil(p.getPrice());
		}
	}

	@Override
	protected void drawPoints(Graphics g) {
		ArrayList<Plein> e = car.getPleins();
		g.setColor(Color.RED);
		int xcoord = OFFSET;
		int placeForOne =  (getWidth() - OFFSET * 2) / e.size();
		int max = maximum;
		int maxHeight = getHeight() - OFFSET * 2;
		for(Plein p : e) {
			double ratio = (double) p.getPrice() / (double) max;
			int y = (int) Math.floor(ratio * maxHeight) + OFFSET;
			drawPoint(new Point(xcoord, y), g);
			xcoord += placeForOne;
		}
	}
}
