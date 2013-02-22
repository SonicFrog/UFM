package com.unilabs.graph;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Classe permettant l'export d'un panel de graph dans une image extérieure au programme
 * @version 1.0
 * @see BufferedImage
 * @see GraphPanel
 * @see Graphics
 * @see GraphPanel#paint(Graphics)
 * @author Ogier
 *
 */
public class GraphWriter {

	private GraphPanel panel;
	
	/**
	 * Instancie un exporteur de graphe
	 * @param p
	 *		Le graphe que l'on souhaite exporter avec cette instance
	 */
	public GraphWriter(GraphPanel p) {
		panel = p;
	}
	
	/**
	 * Exporte l'image du panel donnée à l'initialisation dans un fichier
	 * 
	 * @see BufferedImage
	 * @see ImageIO
	 * @see Graphics
	 * @param output
	 * 		Le fichier dans lequel on écrit l'image
	 * @param format
	 * 		Le format de l'image à sauvegarder
	 * @throws IOException
	 */
	public void write(File output, String format) throws IOException {
		BufferedImage bi = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		panel.paint(g);
		ImageIO.write(bi, format, output);
	}
}
