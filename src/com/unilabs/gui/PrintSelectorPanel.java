package com.unilabs.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.unilabs.entities.Observable;
import com.unilabs.entities.Observer;

public class PrintSelectorPanel extends JPanel implements ActionListener, Observable {

	public static final int AVERAGE_DISPLAY = 1;
	public static final int PLEIN_DISPLAY = 2;
	private static final long serialVersionUID = -6292355275353691128L;
	
	private ArrayList<Observer> obs = new ArrayList<Observer>();
	private JRadioButton average = new JRadioButton("Moyenne"), plein = new JRadioButton("Pleins"); 
	private ButtonGroup group = new ButtonGroup();
	
	public PrintSelectorPanel() {
		setLayout(new FlowLayout());
		group.add(average);
		group.add(plein);
		plein.setSelected(true);
		average.addActionListener(this);
		plein.addActionListener(this);
		add(average);
		add(plein);
		notifyObservers();
	}
	
	public int getSelectedDisplay() {
		if(average.isSelected()) {
			System.out.println("Displaying average panel");
			return AVERAGE_DISPLAY;
		} else {
			System.out.println("Displaying plein panel");
			return PLEIN_DISPLAY;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		notifyObservers();
	}

	@Override
	public void addObserver(Observer o) {
		obs.add(o);
	}

	@Override
	public void notifyObservers() {
		System.out.println("Notifying observer");
		for(Observer o : obs) {
			o.update(this, (Object) null);
		}
	}

	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		
	}
}
