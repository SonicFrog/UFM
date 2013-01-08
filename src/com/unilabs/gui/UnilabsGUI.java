package com.unilabs.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.entities.UnilabsFleetManager;

/**
 * Fenetre principale du programme de gestion du parc automobile
 * 
 * @version 1.0
 * @see CarPanel
 * @see PleinPanel
 * @author Ogier
 *
 */
public class UnilabsGUI extends JFrame implements WindowListener{

	private static final long serialVersionUID = 4611016775839510723L;
	private static String ICON = "icon.gif";

	private Image image;
	private Dimension defaultDimension = new Dimension(900, 700);

	
	/**
	 * Le panel affichant les moyennes de chaque voiture dans la fenetre principale
	 */
	private CarScrollPane cp;
	
	private boolean built = false;

	private boolean carPaneldrawn = false;
	
	private JPanel nodata = new JPanel();
	

	public UnilabsGUI() {
		super("Gestionnaire de flotte automobile Unilabs");
		addWindowListener(this);
		setResizable(true);
		setSize(defaultDimension);
		setJMenuBar(new UnilabsMenubar());
		setLocationRelativeTo(null);
		try {
                    image = ImageIO.read(new File(ICON));
                } catch (IOException e) {
                    System.out.println("L'icone du programme est manquante !");
                }
		setIconImage(image);
		UnilabsFleetManager.getInstance().setGUI(this);
		nodata.setFont(new Font("Arial", Font.BOLD, 32));
		nodata.add(new JLabel(("Aucune donnée chargée")));
		setContentPane(nodata);
		setResizable(true);
		setVisible(true);
		built = true;
	}

	public void repaint() {
		if(UnilabsFleetManager.getInstance().getCars().length == 0 && built) {
			setContentPane(nodata);
		} else if(UnilabsFleetManager.getInstance().getCars().length > 0 && !carPaneldrawn) {
			drawCarPanel();
			carPaneldrawn = true;
		} else if(UnilabsFleetManager.getInstance().getCars().length > 0 && carPaneldrawn) {
			cp.repaint();
		}
		super.repaint();
	}
	
	/**
	 * Dessine le panneau contenant les informations de moyenne pour la flotte sur la GUI
	 */
	private synchronized void drawCarPanel() {
		cp = new CarScrollPane();
		setContentPane(cp);
	}

	public String getSelectedCar() {
		if(cp.getSelectedCarIndex() == -1)
			return null;
		return cp.getSelectedCarPlaque();
	}
	
	/**
	 * Ancienne méthode pour obtenir l'index de la voiture selectionnée dans la GUI
	 * Ne support pas le tri dans le TableModel et retourne des mauvais résultats quand le tableau
	 * a été trié par l'utilisateur
	 * @deprecated
	 * @return
	 * 		L'index dans le TableModel de la voiture
	 */
	public int getSelectedCarIndex() {
		int rowIndex = cp.getSelectedCarIndex();
		int i = 0 ;
		JTable table = cp.getTable();
		String plaque = (String) table.getValueAt(rowIndex, 1);
		UnilabsCar[] c = UnilabsFleetManager.getInstance().getCars();
		for(UnilabsCar car : c) {
			if(car.getPlaque().equals(plaque))
				return i;
			i++;
		}
		return -1;
	}

	/**
	 * Point d'entrée du programme
	 * @param args
	 * 		Argument de ligne de commande (inutile)
	 */
	public static void main(String[] args) {
		UnilabsGUI gui = null;
		UnilabsFleetManager ufm = UnilabsFleetManager.getInstance();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			gui = new UnilabsGUI();
		} catch (Exception e) {
			Message.showErrorMessage(gui, "Fatal Error", "Veuillez contacter le support technique avec les informations suivantes : \n" 
					+ e.getLocalizedMessage() + e.getClass());
			e.printStackTrace();
		}
		for(String s : args) {
			if(s.endsWith(ufm.getFileExtension())) {
				ufm.openBinary(new File(s));
			}
			else if(s.endsWith(".ods")) {
				ufm.openODS(new File(s));
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		//TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		UnilabsFleetManager.getInstance().exit();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
