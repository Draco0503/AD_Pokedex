package com.pkm.view;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import com.pkm.model.entity.Pokemon;

public class DataTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private int rows;
	private final int cols = 5;
	
	private List<Pokemon> pokedex;
	
	public DataTableModel() {
		pokedex = new ArrayList<>();
	}
	
	public DataTableModel(ArrayList<Pokemon> pokedex) {
		this.pokedex = pokedex;
		rows = pokedex.size();
	}
	
	@Override
	public int getRowCount() {
		return rows;
	}

	@Override
	public int getColumnCount() {
		return cols;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0: return pokedex.get(rowIndex).getNum_pokedex();
			case 1: return pokedex.get(rowIndex).getNombre();
			case 2: return pokedex.get(rowIndex).getTipo1().getNombre();
			case 3: if (!pokedex.get(rowIndex).getTipo2().getNombre().equals("NULL")) return pokedex.get(rowIndex).getTipo2().getNombre();
					else return "";
			case 4: return pokedex.get(rowIndex).isShiny();
			default: return null;
		}
	}
	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0: return "#";
			case 1: return "Nombre";
			case 2: return "Tipo1";
			case 3: return "Tipo2";
			case 4: return "Shiny";
			default: return null;
		}
	}
	// habria que hacer algo para actualizar la tabla a tiempo real
	public void updateTable() {
		SwingUtilities.invokeLater(this::fireTableStructureChanged);
	}
}
