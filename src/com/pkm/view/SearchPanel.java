package com.pkm.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.pkm.utils.RoundedBorder;

public class SearchPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final JLabel lbSearch = new JLabel("Buscar:");
	
	
	private final SpinnerModel model = new SpinnerNumberModel(1, 1, 999, 1);
	private final JSpinner tfSearch = new JSpinner(model);
	
	private JButton btnSearch;
	private JButton btnExit;
	
	public SearchPanel() {
		this.setVisible(false);
		init();
	}
	private void init() {
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(225, 50, 50, 0));
		this.setLayout(new GridLayout(3, 2, 20, 20));
		this.setSize(400, 400);
		initButtons();
		this.add(lbSearch, new GridBagConstraints()); this.add(tfSearch, new GridBagConstraints());
		this.add(btnSearch, new GridBagConstraints()); this.add(btnExit, new GridBagConstraints());
		//this.add(lbNote, new GridBagConstraints());
	}
	private void initButtons() {
		btnExit = new JButton("SALIR");
		btnExit.setBorder(new RoundedBorder(10));
		btnExit.setFont(new Font("sansserif", Font.BOLD, 24));
		
		btnSearch = new JButton("Buscar");
		btnSearch.setBorder(new RoundedBorder(10));
		btnSearch.setFont(new Font("sansserif", Font.PLAIN, 24));
	}
	
	public void resetListeners() {
		for (ActionListener al : btnSearch.getActionListeners())
			btnSearch.removeActionListener(al);
		for (ActionListener al : btnExit.getActionListeners())
			btnExit.removeActionListener(al);
	}
	
	public void setSearchListener(ActionListener al) {
		btnSearch.addActionListener(al);
	}
	public void setExitListener(ActionListener al) {
		btnExit.addActionListener(al);
	}
	public int getId() {
		return (int) tfSearch.getValue();
	}
}
