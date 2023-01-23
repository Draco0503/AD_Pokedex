package com.pkm.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pkm.utils.RoundedBorder;

public class CRUDPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton btnCreate;
	private JButton btnRead;
	private JButton btnReadAll;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnExport;
	private JButton btnExit;
	
	
	public CRUDPanel() {
		this.setVisible(false);
		init();
	}
	
	private void init() {
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(225, 50, 50, 0));
		this.setLayout(new GridLayout(4, 2, 20, 25));
		this.setSize(400, 600);
		initButtons();
		
		this.add(btnCreate, new GridBagConstraints());
		this.add(btnRead, new GridBagConstraints());
		this.add(btnReadAll, new GridBagConstraints());
		this.add(btnUpdate, new GridBagConstraints());
		this.add(btnDelete, new GridBagConstraints());
		this.add(btnExport, new GridBagConstraints());
		this.add(btnExit, new GridBagConstraints());
	}

	private void initButtons() {
		btnCreate = new JButton("Nuevo");
		btnCreate.setBorder(new RoundedBorder(10));
		btnCreate.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnRead = new JButton("Ver uno");
		btnRead.setBorder(new RoundedBorder(10));
		btnRead.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnReadAll = new JButton("Ver todos");
		btnReadAll.setBorder(new RoundedBorder(10));
		btnReadAll.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnUpdate = new JButton("Modificar");
		btnUpdate.setBorder(new RoundedBorder(10));
		btnUpdate.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnDelete = new JButton("Borrar");
		btnDelete.setBorder(new RoundedBorder(10));
		btnDelete.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnExport = new JButton("Exportar");
		btnExport.setBorder(new RoundedBorder(10));
		btnExport.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnExit = new JButton("SALIR");
		btnExit.setBorder(new RoundedBorder(10));
		btnExit.setFont(new Font("sansserif", Font.BOLD, 24));

	}
	
	public void setCreateListener(ActionListener al) {
		btnCreate.addActionListener(al);
	}
	public void setReadListener(ActionListener al) {
		btnRead.addActionListener(al);
	}
	public void setReadAllListener(ActionListener al) {
		btnReadAll.addActionListener(al);
	}
	public void setUpdateListener(ActionListener al) {
		btnUpdate.addActionListener(al);
	}
	public void setDeleteListener(ActionListener al) {
		btnDelete.addActionListener(al);
	}
	public void setExportListener(ActionListener al) {
		btnExport.addActionListener(al);
	}
	public void setExitListener(ActionListener al) {
		btnExit.addActionListener(al);
	}
	
}
