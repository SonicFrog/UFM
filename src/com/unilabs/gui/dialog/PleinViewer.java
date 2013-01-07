package com.unilabs.gui.dialog;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.unilabs.entities.UnilabsCar;
import com.unilabs.processing.TableCreator;

/**
 * Frame permettant de visualiser les pleins d'une voiture tels qu'ils sont contenus dans le fichier de départ
 * @version 1.2
 * @author Ogier
 *
 */
public class PleinViewer extends JFrame implements WindowListener {

	private static final long serialVersionUID = -2774268525403276618L;
	private static final Dimension DIMENSION = new Dimension(500, 500);

	public PleinViewer(UnilabsCar data) {
		setAlwaysOnTop(true);
		setTitle("Pleins pour " + data.getPlaque());
		setSize(DIMENSION);
		setContentPane(new JScrollPane(TableCreator.createPleinTable(data)));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public PleinViewer(UnilabsCar[] data) {
		setAlwaysOnTop(true);
		setTitle("Pleins pour ");
		for(UnilabsCar c : data) {
			setTitle(getTitle() + c.getPlaque() + "; ");
		}
		setSize(DIMENSION);
		setContentPane(new JScrollPane(TableCreator.createPleinTable(data)));
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
			
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
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
