package com.pkm.view;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.pkm.utils.OpaqueHeader;
import com.pkm.utils.RoundedBorder;

public class DataTable extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnExit;
	private JTable table;
	
	public DataTable() {
		this.setVisible(false);
		init();
	}
	
	private void init() {
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(225, 50, 50, 0));
		this.setLayout(new GridLayout(2, 0, 20, 20));
		this.setSize(400, 600);
		
		table = new JTable();
		table.setVisible(true);
		table.setModel(new DataTableModel());
		// table.getTableHeader().setOpaque(false);
		table.getTableHeader().setDefaultRenderer(new OpaqueHeader());
		// table.getTableHeader().setFont(new Font("sansserif", Font.PLAIN, 16));
		table.setOpaque(false);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(51, 204, 255));
		table.setBackground(new Color(0,0,0,0));
		table.setFont(new Font("sansserif", Font.PLAIN, 14));
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		btnExit = new JButton("SALIR");
		btnExit.setBorder(new RoundedBorder(10));
		btnExit.setFont(new Font("sansserif", Font.BOLD, 24));
		
		this.add(scrollPane, new GridBagConstraints());
		this.add(btnExit, new GridBagConstraints());
	}

	public void setExitListener(ActionListener al) {
		btnExit.addActionListener(al);
	}
	
	public JTable getTable() {
		return table;
	}
	
}
