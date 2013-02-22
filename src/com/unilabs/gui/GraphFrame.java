package com.unilabs.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.unilabs.graph.GraphPanel;
import com.unilabs.graph.GraphWriter;
import com.unilabs.gui.action.PrintGUIAction;

/**
 * Fenêtre d'affichage d'un graphe <br />
 * Ajoute un menu pour exporter le graphe en tant qu'image
 * @version 1.0
 * @see GraphPanel
 * @author Ogier
 *
 */
public class GraphFrame extends JFrame implements ActionListener {

	/**
	 * File contenant le dernier répertoire où une image a été sauvegardée
	 */
	private static File last_dir;
	private static final long serialVersionUID = 7772467237249510599L;

	/**
	 * Le GraphPanel qu'on enregistre dans une image
	 */
	private GraphPanel panel;

	/**
	 * Barre de menu contenant le menu d'export
	 */
	private JMenuBar menu = new JMenuBar();


	/**
	 * Menu d'exportation
	 */
	private JMenuItem exportI = new JMenuItem("Enregistrer sous ...");

	/**
	 * Le menu permettant d'exporter l'image
	 */
	private JMenu export = new JMenu("Fichier");
	private JMenuActionItem print;

	/**
	 * Fichier temporaire utilisé pour l'impression
	 */
	private File printFile;

	/**
	 * Instancie une fenêtre d'affichage de graphe pour le GraphPanel spécifié
	 * @param panel 
	 */
	public GraphFrame(GraphPanel panel) {
		this.panel = panel;
		initPrint();
		buildMenu();
		buildFrame();
	}

	/**
	 * Construit la fenêtre
	 */
	private void buildFrame() {
		setJMenuBar(menu);
		setContentPane(panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(panel.getSize());
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Initialise le sous système d'impression
	 */
	private void initPrint() {
		GraphWriter gw = new GraphWriter(panel);
		try {
			gw.write(printFile = File.createTempFile("ufm", ".png"), "png");
			print = new JMenuActionItem("Imprimer ...", new PrintGUIAction(printFile));
			print.addActionListener(new MenuListener());
		} catch (IOException e) {
			Message.showErrorMessage("Erreur", e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Construit le menu
	 */
	private void buildMenu() {
		menu.add(export);
		exportI.addActionListener(this);
		export.add(exportI);
		export.add(print);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		GraphWriter gw;
		jfc.setMultiSelectionEnabled(false);
		jfc.setCurrentDirectory(last_dir);
		jfc.setToolTipText("Selectionnez le fichier de sortie");
		jfc.setFileFilter(new FileNameExtensionFilter("Image au format PNG", "png"));
		jfc.showSaveDialog(this);
		gw = new GraphWriter(panel);
		try {
			gw.write(jfc.getSelectedFile(), "png");
			last_dir = jfc.getSelectedFile();
		} catch (IOException e1) {
			Message.showErrorMessage(this, "Une erreur s'est produite lors de la sauvegarde de l'image", e1.getLocalizedMessage());
			dispose();
		}
	}
}
