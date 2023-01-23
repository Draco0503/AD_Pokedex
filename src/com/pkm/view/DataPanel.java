package com.pkm.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.pkm.model.entity.Tipo;
import com.pkm.utils.RoundedBorder;

public class DataPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private String url;

	private final JLabel lbId = new JLabel("Nº Pokedex:");
	private final JLabel lbName = new JLabel("Nombre:");
	private final JLabel lbTipo1 = new JLabel("Tipo 1:");
	private final JLabel lbTipo2 = new JLabel("Tipo 2**:");
	private final JLabel lbShiny = new JLabel("Shiny?");
	private final JLabel lbURL = new JLabel("URL:");
	// private final JLabel lbNote = new JLabel("**Si es monotipo elige el mismo");
	
	private final JTextField tfName = new JTextField(20);
	
	private final SpinnerModel model = new SpinnerNumberModel(1, 1, 999, 1);
	private final JSpinner spId = new JSpinner(model);
	
	private final JComboBox<String> cbTipo1 = new JComboBox<>();
	private final JComboBox<String> cbTipo2 = new JComboBox<>();
	
	private final JCheckBox chYes = new JCheckBox("Si");

	private JButton btnFileChooser;
	
	private JButton btnSave;
	private JButton btnReset;
	private JButton btnExit;
	
	public DataPanel() {
		this.setVisible(false);
		init();
		url = "";
		this.add(lbId, new GridBagConstraints()); this.add(spId, new GridBagConstraints());
		this.add(lbName, new GridBagConstraints()); this.add(tfName, new GridBagConstraints());
		this.add(lbTipo1, new GridBagConstraints()); this.add(cbTipo1, new GridBagConstraints());
		this.add(lbTipo2, new GridBagConstraints()); this.add(cbTipo2, new GridBagConstraints());
		this.add(lbShiny, new GridBagConstraints()); this.add(chYes, new GridBagConstraints());
		this.add(lbURL, new GridBagConstraints()); this.add(btnFileChooser, new GridBagConstraints());
		this.add(btnSave, new GridBagConstraints()); this.add(btnReset, new GridBagConstraints());
		this.add(btnExit, new GridBagConstraints()); // this.add(lbNote, new GridBagConstraints());
	}

	private void init() {
		this.setOpaque(false);
		this.setBorder(new EmptyBorder(225, 50, 0, 0));
		this.setLayout(new GridLayout(9, 2, 20, 20));
		this.setSize(400, 700);
		initButtons();
		chYes.setOpaque(false);
		chYes.setSelected(false);
		// lbNote.setFont(new Font("sanserif", Font.BOLD, 9));
	}
	public void initComboBoxes(HashMap<Integer, Tipo> tipos) {
		tipos.forEach((k, v) -> {
			cbTipo1.addItem(v.getNombre());
			cbTipo2.addItem(v.getNombre());
		});
	}
	
	private void initButtons() {
		btnFileChooser = new JButton("Abrir...");
		btnFileChooser.setBorder(new RoundedBorder(10));
		btnFileChooser.addActionListener((e) -> {
			fileChooser();
		});
		
		btnSave = new JButton("Guardar");
		btnSave.setBorder(new RoundedBorder(10));
		btnSave.setFont(new Font("sansserif", Font.PLAIN, 24));
		
		btnReset = new JButton("Reset");
		btnReset.setBorder(new RoundedBorder(10));
		btnReset.setFont(new Font("sansserif", Font.PLAIN, 24));
		btnReset.addActionListener((e) -> {
			reset();
		});
		
		btnExit = new JButton("SALIR");
		btnExit.setBorder(new RoundedBorder(10));
		btnExit.setFont(new Font("sansserif", Font.BOLD, 24));
	}
	
	private void reset() {
		spId.setValue(1);
		tfName.setText("");
		cbTipo1.setSelectedItem(0);
		cbTipo2.setSelectedItem(0);
		chYes.setSelected(false);
		url = "";
	}
	
	public void resetListeners() {
		for (ActionListener al : btnSave.getActionListeners())
			btnSave.removeActionListener(al);
		for (ActionListener al : btnExit.getActionListeners())
			btnExit.removeActionListener(al);
	}
	
	private void fileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("Only png files", "png"));
		int value = fc.showOpenDialog(this);
		if (value == JFileChooser.APPROVE_OPTION) {
			url = fc.getSelectedFile().getAbsolutePath();
			if (url == null) url = "URL";
		} else {
			url = "URL";
			JOptionPane.showMessageDialog(new JFrame(), "File not valid", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setSaveListenter(ActionListener al) {
		btnSave.addActionListener(al);
	}
	public void setExitListenter(ActionListener al) {
		btnExit.addActionListener(al);
	}
	public int getNumPokedex() {
		return (int) spId.getValue();
	}
	public String getText() {
		return tfName.getText();
	}
	public Tipo getTipo(int opt) {
		Tipo t = null;
		if (opt == 0) {
			int id = cbTipo1.getSelectedIndex() + 1;
			String nombre = (String) cbTipo1.getSelectedItem();
			t = new Tipo(id, nombre);
			
		} else {
			int id = cbTipo2.getSelectedIndex() + 1;
			String nombre = (String) cbTipo2.getSelectedItem();
			t = new Tipo(id, nombre);
		}
		return t;
	}
	public boolean isShiny() {
		return chYes.isSelected();		
	}
	public String getUrl() {
		return url;
	}
}
