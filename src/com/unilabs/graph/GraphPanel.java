package com.unilabs.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.unilabs.entities.Plein;
import com.unilabs.entities.UnilabsCar;

/**
 * Classe abstraite représentant un panel contenant un graphique <br />
 * Doit être etendue pour representer un graphe particulier
 * 
 * @see Graphics
 * @version 1.0
 * @since 1.0
 * @author Ogier
 *
 */
@SuppressWarnings("serial")
public abstract class GraphPanel extends JPanel {
	/**
	 * Le décalage des dessins par rapport à la bordure le fenêtre
	 */
	public static final int OFFSET = 35;
	
	/**
	 * Le format de date utilsé pour l'affichage
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
	
	/**
	 * La voiture pour laquelle on dessine le graphe
	 */
	protected UnilabsCar car;
	
	/**
	 * La valeur maximum en ordonnée des points pour définir l'échelle du dessin verticalement
	 */
	protected int maximum;
	
	/**
	 * La liste des points dessiné
	 */
	private ArrayList<Point> points = new ArrayList<>();
	
	public GraphPanel(UnilabsCar data) {
		car = data;
		setMinimumSize(new Dimension(550, 550));
		setSize(new Dimension(800, 600));
	}
	
	@Override
	public void paint(Graphics g) {
		drawBorders(g);
		drawScale(g);
		drawPoints(g);
	}
	
	/**
	 * Dessine les bordures du graph
	 * @param g
	 */
	protected void drawBorders(Graphics g) {
		ArrayList<Plein> p = car.getPleins();
		int hsize = getHeight() - OFFSET;
		int wsize = getWidth() - OFFSET;
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth(), getHeight());
		g.drawLine(OFFSET, hsize, wsize, hsize);
		g.drawLine(OFFSET, hsize, OFFSET, OFFSET);
		g.drawString(String.valueOf(maximum), 0 + OFFSET - 25, OFFSET);
		g.drawString(String.valueOf(0), OFFSET - 10, hsize + 5);
		g.drawString(sdf.format(p.get(0).getDate()), OFFSET - 15, hsize + 15);
		g.drawString(sdf.format(p.get(p.size() - 1).getDate()), wsize - 15, hsize + 15);
	}
	
	/**
	 * Dessine l'échelle du graphe
	 * @param g
	 */
	protected void drawScale(Graphics g) {
		int height = getHeight() - OFFSET * 2;
		int heightFor100 = 100 * maximum / height;
		g.setColor(Color.BLACK);
		for(int i = 1 ; i < Math.ceil((double)maximum/(double)100) ; i++) {
			System.out.println("Drawing line for " + i *100 + " at " + i*heightFor100);
			g.drawLine(OFFSET, i * heightFor100 + OFFSET, getWidth() - OFFSET, i * heightFor100 + OFFSET);
		}
	}
	
	/**
	 * Dessine un point sur l'objet graphics fourni et l'ajoute dans la liste des points
	 * @param p
	 * 		Le point à dessiner
	 * @param g
	 * 		L'objet graphics sur lequel on dessine
	 */
	protected void drawPoint(Point p, Graphics g) {
		Point previous;
		points.add(p);
		if(points.size() == 1) {
			return;
		}
		previous = points.get(points.size() - 2);
		g.setColor(Color.red);
		g.drawLine((int) previous.getX(), (int) previous.getY(), (int) p.getX(), (int) p.getY());
		((Graphics2D) g).fillOval((int) p.getX() - 2, (int) p.getY() - 2, 5, 5);
	}
	
	/**
	 * Dessine les points sur le graph
	 * @param g
	 * 		L'objet graphisme sur lequel on dessine
	 */
	protected abstract void drawPoints(Graphics g);
}
