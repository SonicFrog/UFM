package com.unilabs.gui.action;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.unilabs.gui.UnilabsMenubar;


public class InteroGUIAction implements GUIAction, ActionListener {

	private JDialog about;
	@Override
	public void execute() {
		JPanel p;
		about = new JDialog();
		JButton ok = new JButton("Ok");
		about.setLayout(new GridLayout(4, 1));
		p = new JPanel();
		p.add(new JLabel("Gestionnaire de flottes automobile Unilabs v0.54"));
		about.add(p);
		p = new JPanel();
		p.add(new JLabel("http://ars3nic.proteck.ch"));
		about.add(p);
		p = new JPanel();
		p.add(new JLabel("© Ogier Bouvier 2012"));
		about.add(p);
		p = new JPanel();
		p.add(ok);
		about.add(p);
		ok.addActionListener(this);
		about.setResizable(false);
		about.setSize(new Dimension(250, 140));
		about.setLocationRelativeTo(null);
		about.setTitle("A propos");
		try {
			about.setIconImage(ImageIO.read(new File(UnilabsMenubar.ABOUT_ICON)));
		} catch (IOException e) {

		}
		about.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		about.dispose();
	}
}
