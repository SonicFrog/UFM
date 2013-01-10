package com.unilabs.options;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialogue pour modifier les options du programme
 * @version 1.0
 * @author ogier
 *
 */
public class OptionDialog extends JDialog implements ActionListener {

	protected OptionStorage os;
	protected JTextField reader = new JTextField();
	protected JTextField writer = new JTextField();
	protected JTextField currency = new JTextField();
	
	protected JButton ok = new JButton("Ok"), cancel = new JButton("Annuler");
	
	protected JPanel valuePane = new JPanel();
	protected JPanel buttonPane = new JPanel();
	
	public OptionDialog(JFrame parent, OptionStorage os) {
		super(parent, "Options");
		this.os = os;
		setSize(new Dimension(600, 400));
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setResizable(false);
		init();
		add(valuePane, BorderLayout.NORTH);
		add(buttonPane, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private void init() {
		reader.setText(os.getReaderValue());
		reader.setSize(new Dimension(100, (int) reader.getSize().getHeight()));
		writer.setText(os.getWriterValue());
		currency.setText(os.getCurrency());
		valuePane.setLayout(new GridLayout(3, 2));
		valuePane.add(new JLabel("Monnaie : "));
		valuePane.add(currency);
		valuePane.add(new JLabel("Format d'enregistrement des fichiers :"));
		valuePane.add(reader);
		valuePane.add(new JLabel("Format d'ouverture des fichiers : "));
		valuePane.add(writer);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		buttonPane.add(ok);
		buttonPane.add(cancel);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok) {
			os.setValue(OptionStorage.READER_CLASS_KEY, reader.getText());
			os.setValue(OptionStorage.WRITER_CLASS_KEY, writer.getText());
			os.setValue(OptionStorage.CURRENCY_KEY, currency.getText());
		}
		dispose();
	}
}
