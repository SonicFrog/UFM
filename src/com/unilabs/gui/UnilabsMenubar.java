package com.unilabs.gui;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;

import com.unilabs.gui.action.AddGUIAction;
import com.unilabs.gui.action.ClientAverageDropdownAction;
import com.unilabs.gui.action.CloseGUIAction;
import com.unilabs.gui.action.CompareGUIAction;
import com.unilabs.gui.action.ConsoGraphAction;
import com.unilabs.gui.action.DeleteGUIAction;
import com.unilabs.gui.action.ExitAction;
import com.unilabs.gui.action.ExportGUIAction;
import com.unilabs.gui.action.FleetAverageAction;
import com.unilabs.gui.action.FleetTotalsAction;
import com.unilabs.gui.action.HelpGUIAction;
import com.unilabs.gui.action.InteroGUIAction;
import com.unilabs.gui.action.KiloGraphAction;
import com.unilabs.gui.action.ModifyGUIAction;
import com.unilabs.gui.action.NewGUIAction;
import com.unilabs.gui.action.OpenBinaryGUIAction;
import com.unilabs.gui.action.OpenODSGUIAction;
import com.unilabs.gui.action.OptionAction;
import com.unilabs.gui.action.PriceGraphAction;
import com.unilabs.gui.action.RedoGUIAction;
import com.unilabs.gui.action.SaveAsGUIAction;
import com.unilabs.gui.action.SaveGUIAction;
import com.unilabs.gui.action.SearchGUIAction;
import com.unilabs.gui.action.SingleCarAverageAction;
import com.unilabs.gui.action.UndoGUIAction;

/**
 * Barre de menu pour la fenêtre principale du gestionnaire de consommation
 * 
 * @author Ogier
 * @see JMenuActionItem
 * @version 1.0
 *
 */
public class UnilabsMenubar extends JMenuBar {

	private static final long serialVersionUID = -3883626757381366262L;
	
	public static final String ICON_FOLDER = "img";

	public static final String SAVE_ICON = ICON_FOLDER + File.separator + "save.png";
	public static final String NEW_ICON = ICON_FOLDER + File.separator + "new.png";
	public static final String OPEN_ICON = ICON_FOLDER + File.separator + "open.png";
	public static final String CLOSE_ICON = ICON_FOLDER + File.separator + "close.png";
	public static final String QUIT_ICON = ICON_FOLDER + File.separator + "quit.png";

	public static final String REDO_ICON = ICON_FOLDER + File.separator + "redo.png";
	public static final String UNDO_ICON = ICON_FOLDER + File.separator + "undo.png";
	
	public static final String ADD_ICON = ICON_FOLDER + File.separator + "add.png";
	public static final String DELETE_ICON = ICON_FOLDER + File.separator + "delete.png";
	public static final String SEARCH_ICON = ICON_FOLDER + File.separator + "search.png";
	public static final String COMPARE_ICON = ICON_FOLDER + File.separator + "compare.png";
	public static final String EXPORT_ICON = ICON_FOLDER + File.separator + "export.png";
	public static final String EDIT_ICON = ICON_FOLDER + File.separator + "edit.png";

	public static final String HELP_ICON = ICON_FOLDER + File.separator + "help.png";
	public static final String ABOUT_ICON = ICON_FOLDER + File.separator + "about.png";

	private JMenu file = new JMenu("Fichier");
	private JMenu edit = new JMenu("Edition");
	private JMenu car = new JMenu("Voiture");
	private JMenu graph = new JMenu("Graph");
	private JMenu test = new JMenu("Test");
	private JMenu about = new JMenu("?");

	private JMenuActionItem New = new JMenuActionItem("Nouveau ...", new NewGUIAction());
	private JMenuActionItem open = new JMenuActionItem("Ajouter des données ...", new OpenODSGUIAction());
	private JMenuActionItem load = new JMenuActionItem("Ouvrir ...", new OpenBinaryGUIAction());
	private JMenuActionItem save = new JMenuActionItem("Enregistrer", new SaveGUIAction());
	private JMenuActionItem saveas = new JMenuActionItem("Enregistrer sous ...", new SaveAsGUIAction());
	private JMenuActionItem close = new JMenuActionItem("Fermer", new CloseGUIAction());
	private JMenuActionItem exit = new JMenuActionItem("Quitter", new ExitAction());

	private JMenuActionItem add = new JMenuActionItem("Ajouter ...", new AddGUIAction());
	private JMenuActionItem search = new JMenuActionItem("Rechercher ...", new SearchGUIAction());
	private JMenuActionItem export = new JMenuActionItem("Exporter ...", new ExportGUIAction());
	private JMenuActionItem modify = new JMenuActionItem("Modifier ...", new ModifyGUIAction());
	private JMenuActionItem delete = new JMenuActionItem("Supprimer ...", new DeleteGUIAction());
	private JMenuActionItem comparison = new JMenuActionItem("Comparer ...", new CompareGUIAction());
	
	private JMenuActionItem kgraph = new JMenuActionItem("Kilomètres", new KiloGraphAction());
	private JMenuActionItem pgraph = new JMenuActionItem("Prix", new PriceGraphAction());
	private JMenuActionItem cgraph = new JMenuActionItem("Consommation", new ConsoGraphAction());
	
	private JMenuActionItem mFleet = new JMenuActionItem("Moyenne pour la flotte", new FleetAverageAction());
	private JMenuActionItem mTotal = new JMenuActionItem("Totaux pour la flotte", new FleetTotalsAction());
	private JMenuActionItem mClient = new JMenuActionItem("Moyenne pour un client", new ClientAverageDropdownAction());
	private JMenuActionItem mAvg = new JMenuActionItem("Moyenne pour cette voiture", new SingleCarAverageAction());
	private JMenuActionItem mOption = new JMenuActionItem("Options", new OptionAction());
	
	private JMenuActionItem undo = new JMenuActionItem("Annuler", new UndoGUIAction());
	private JMenuActionItem redo = new JMenuActionItem("Refaire", new RedoGUIAction());

	private JMenuActionItem help = new JMenuActionItem("Aide", new HelpGUIAction());
	private JMenuActionItem intero = new JMenuActionItem("?", new InteroGUIAction());

	/**
	 * Instancie une barre de menu pour la fenêtre principale
	 */
	public UnilabsMenubar() {
		file.setMnemonic('F');
		edit.setMnemonic('E');
		car.setMnemonic('V');
		test.setMnemonic('T');
		about.setMnemonic('?');

		try {
			New.setIcon(new ImageIcon(ImageIO.read(new File(NEW_ICON))));
			load.setIcon(new ImageIcon(ImageIO.read(new File(OPEN_ICON))));
			save.setIcon(new ImageIcon(ImageIO.read(new File(SAVE_ICON))));
			close.setIcon(new ImageIcon(ImageIO.read(new File(CLOSE_ICON))));
			exit.setIcon(new ImageIcon(ImageIO.read(new File(QUIT_ICON))));
			undo.setIcon(new ImageIcon(ImageIO.read(new File(UNDO_ICON))));
			redo.setIcon(new ImageIcon(ImageIO.read(new File(REDO_ICON))));
			add.setIcon(new ImageIcon(ImageIO.read(new File(ADD_ICON))));
			comparison.setIcon(new ImageIcon(ImageIO.read(new File(COMPARE_ICON))));
			modify.setIcon(new ImageIcon(ImageIO.read(new File(EDIT_ICON))));
			export.setIcon(new ImageIcon(ImageIO.read(new File(EXPORT_ICON))));
			search.setIcon(new ImageIcon(ImageIO.read(new File(SEARCH_ICON))));
			delete.setIcon(new ImageIcon(ImageIO.read(new File(DELETE_ICON))));
			help.setIcon(new ImageIcon(ImageIO.read(new File(HELP_ICON))));
			intero.setIcon(new ImageIcon(ImageIO.read(new File(ABOUT_ICON))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		saveas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));

		add.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
		modify.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK));
		search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		export.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		
		mFleet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
		mTotal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
		
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK));

		file.add(New);
		file.add(load);
		file.add(open);
		file.add(save);
		file.add(saveas);
		file.add(close);
		file.add(exit);

		car.add(add);
		car.add(comparison);
		car.add(modify);
		car.add(search);
		car.add(delete);
		car.add(export);
		
		edit.add(undo);
		edit.add(redo);
		
		graph.add(kgraph);
		graph.add(cgraph);
		graph.add(pgraph);
		
		test.add(mFleet);
		test.add(mTotal);
		test.add(mClient);
		test.add(mAvg);
		test.add(mOption);

		about.add(help);
		about.add(intero);

		
		add(file);
		add(car);
		add(graph);
		add(test);
		add(edit);
		add(about);
	}
}
