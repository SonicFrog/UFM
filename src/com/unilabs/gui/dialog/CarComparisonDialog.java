package com.unilabs.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.processing.AverageCalculator;

/**
 * Dialogue de comparaison entre deux voitures
 * @version 2.0
 * @author Ogier
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CarComparisonDialog extends JFrame implements ActionListener {

	private static final long serialVersionUID = 3388040397040668012L;

	private static final String[] CAR_COLUMS = { "Plaque", "Numéro client", "Consommation pour 100km" , "Kilomètres par plein" , "Prix du litre" , "Prix par plein" };

	private Vector dataUn = new Vector();

	private Vector dataDeux = new Vector();

	private JPanel dataPanel = new JPanel();

	private JPanel boutonPanel = new JPanel();

	private JButton ok = new JButton("OK");


	public CarComparisonDialog(UnilabsCar un, UnilabsCar deux) {
		super("Comparaison : " + un.getPlaque() + " -" + deux.getPlaque());
		dataUn = generateVector(un);
		dataDeux = generateVector(deux);
		if(dataUn.size() != CAR_COLUMS.length || dataDeux.size() != dataUn.size())
			throw new IllegalArgumentException();
		buildDataPanel();
		buildBoutonPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(dataPanel, BorderLayout.CENTER);
		getContentPane().add(boutonPanel, BorderLayout.SOUTH);
		setSize(new Dimension(500, 450));
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	private void buildDataPanel() {
		JPanel p;
		String buf;
		double value1 = 0.0, value2 = 0.0;
		Color un = Color.CYAN, deux = Color.CYAN;
		dataPanel.setLayout(new GridLayout(dataUn.size(), 3));
		for(int i = 0 ; i < dataUn.size() ; i++) {
			try {
				value1 = (Double) dataUn.get(i);
				value2 = (Double) dataDeux.get(i);
				if(value1 > value2) {
					un = Color.ORANGE;
					deux = Color.green;
				} else if(value1 < value2){
					un = Color.green;
					deux = Color.ORANGE;
				} else {
					un = deux = Color.CYAN;
				}
			} catch(ClassCastException e) {

			}
			p = new BorderPanel();
			p.add(new JLabel(CAR_COLUMS[i]));
			dataPanel.add(p);
			p = new BorderPanel();
			p.setBackground(un);
			buf = String.valueOf(dataUn.get(i));
			if(buf.length() > 10)
				buf = buf.substring(0, 10);
			p.add(new JLabel(buf));
			dataPanel.add(p);
			p = new BorderPanel();
			p.setBackground(deux);
			buf = String.valueOf(dataDeux.get(i));
			if(buf.length() > 10)
				buf = buf.substring(0, 10);
			p.add(new JLabel(buf));
			dataPanel.add(p);
		}
	}

	private void buildBoutonPanel() {
		ok.addActionListener(this);
		boutonPanel = new JPanel();
		boutonPanel.add(ok);
	}

	private Vector generateVector(UnilabsCar c) {
		Vector vec = new Vector();
		AverageCalculator ce = new AverageCalculator(c);
		vec.add(c.getPlaque());
		vec.add(c.getNumeroClient());
		vec.add(ce.averageConso());
		vec.add(ce.averageKilometers());
		vec.add(ce.averageLiterPrice());
		vec.add(ce.averagePrice());
		return vec;
	}

	public void actionPerformed(ActionEvent e) {
		dispose();
	}

	private class BorderPanel extends JPanel {
		private static final long serialVersionUID = 8729860792670986398L;

		public void paint(Graphics g) {
			super.paint(g);
			g.setColor(Color.black);
			g.drawLine(0, getSize().height, getSize().width, getSize().height);
			g.drawLine(getSize().width, 0, getSize().width, getSize().height);
			g.drawLine(0, 0, getSize().width, 0);
			g.drawLine(0, 0, 0, getSize().height);
		}
	}
}
