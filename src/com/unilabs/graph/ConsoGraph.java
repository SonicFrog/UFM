package com.unilabs.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

public class ConsoGraph extends GraphPanel {

	private static final long serialVersionUID = 9120109065495915103L;

	/**
	 * Instancie un dessinateur de graph qui représente la consommation
	 * @param data 
	 *		La voiture pour laquelle on dessine un graphe
	 */
	public ConsoGraph(UnilabsCar data) {
		super(data);
		for(Plein p : data.getPleins()) {
			if(p.getFuelAmount() > maximum) {
				maximum = (int) Math.ceil(p.getFuelAmount());
			}
		}
	}
	
	@Override
	protected void drawPoints(Graphics g) {
		ArrayList<Plein> e = car.getPleins();
		g.setColor(Color.RED);
		int xcoord = OFFSET;
		int placeForOne =  (getWidth() - OFFSET * 2) / e.size();
		int maxHeight = getHeight() - OFFSET * 2;
		for(Plein p : e) {
			double ratio = p.getFuelAmount() / (double) maximum;
			int y = (int) Math.floor(ratio * maxHeight) + OFFSET;
			drawPoint(new Point(xcoord, y), g);
			xcoord += placeForOne;
		}
	}
}