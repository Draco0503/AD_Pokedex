package com.pkm.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pkm.utils.RoundedBorder;

public class ManagerPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnFile;
	private JButton btnXML;
	private JButton btnDB;
	private JButton btnHB;
	private JButton btnExit;
	
	public ManagerPanel() {
		this.setVisible(true);
		init();
	}
	
	private void init() {
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(75, 50, 450, 50));
		this.setLayout(new GridLayout(5, 1, 15, 25));
		// this.setSize(200, 600);
		initButtons();
		
		this.add(btnFile, new GridBagConstraints());
		this.add(btnXML, new GridBagConstraints());
		this.add(btnDB, new GridBagConstraints());
		this.add(btnHB, new GridBagConstraints());
		this.add(btnExit, new GridBagConstraints());
	}
	private void initButtons() {
		btnFile = new JButton("FILE MANAGER");
		btnFile.setAlignmentX(CENTER_ALIGNMENT);
		btnFile.setBorder(new RoundedBorder(10));
		btnFile.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnXML = new JButton("XML MANAGER");
		btnXML.setAlignmentX(CENTER_ALIGNMENT);
		btnXML.setBorder(new RoundedBorder(10));
		btnXML.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnDB = new JButton("DB MANAGER");
		btnDB.setAlignmentX(CENTER_ALIGNMENT);
		btnDB.setBorder(new RoundedBorder(10));
		btnDB.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnHB = new JButton("HIBERNATE");
		btnHB.setAlignmentX(CENTER_ALIGNMENT);
		btnHB.setBorder(new RoundedBorder(10));
		btnHB.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnExit = new JButton("SALIR");
		btnExit.setAlignmentX(CENTER_ALIGNMENT);
		btnExit.setBorder(new RoundedBorder(10));
		btnExit.setFont(new Font("sansserif", Font.BOLD, 24));
	}
	
	public void resetListeners() {
		for (ActionListener al : btnFile.getActionListeners())
			btnFile.removeActionListener(al);
		for (ActionListener al : btnXML.getActionListeners())
			btnXML.removeActionListener(al);
		for (ActionListener al : btnDB.getActionListeners())
			btnDB.removeActionListener(al);
		for (ActionListener al : btnExit.getActionListeners())
			btnExit.removeActionListener(al);
	}
	
	public void setFileListener(ActionListener al) {
		btnFile.addActionListener(al);
	}
	public void setXMLListener(ActionListener al) {
		btnXML.addActionListener(al);
	}
	public void setDBListener(ActionListener al) {
		btnDB.addActionListener(al);
	}
	public void setHBListener(ActionListener al) {
		btnHB.addActionListener(al);
	}
	public void setExitListener(ActionListener al) {
		btnExit.addActionListener(al);
	}
}
