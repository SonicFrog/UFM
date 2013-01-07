package com.unilabs.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.unilabs.processing.Calculator;

/**
 * Dialogue d'information permettant d'afficher à l'utilisateur les informations contenues dans un Calculator
 * @author Ogier
 * @version 1.0
 *
 */
public class FleetInformationDialog extends JDialog implements ActionListener {

	private static int HEIGHT_PER_ROW = 60;
	private static int ROW_WIDTH = 500;
	private static int BOTTOM_PANEL_HEIGHT = 50;
	
	private static final long serialVersionUID = 1347953184947453798L;
	private Calculator calculator;
	private JPanel contentPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JButton ok = new JButton("OK");
	private String currency;
	
	private JPanel panel;

	public FleetInformationDialog(JFrame parent, Calculator c, String currency) {
		super(parent);
		this.currency = currency;
		calculator = c;		
		init();
		initButtons();
		setResizable(false);
		setModal(true);
		setVisible(true);
	}
	
	private void init() {
		JLabel label;
		setTitle(calculator.getName());
		setLayout(new BorderLayout());
		contentPanel.setLayout(new GridLayout(calculator.getInformationsCount() + 2, 2));
		for(int i = 0 ; i < calculator.getInformationsCount() ; i++) {
			panel = new JPanel();
			label = new JLabel(calculator.getNextInformationText());
			panel.add(label);
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			contentPanel.add(panel);
			panel = new JPanel();
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			label = new JLabel(calculator.getNextInformation() + ((calculator.isNextInformationPrice()) ? " " + currency : ""));
			panel.add(label);
			contentPanel.add(panel);
		}
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(ROW_WIDTH, calculator.getInformationsCount() * HEIGHT_PER_ROW + BOTTOM_PANEL_HEIGHT));
		add(contentPanel, BorderLayout.CENTER);
	}
	
	private void initButtons() {
		ok.addActionListener(this);
		buttonPanel.add(ok);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}
