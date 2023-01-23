package com.pkm.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import com.pkm.model.entity.Pokemon;

/**
 * 
 * @author ADPD
 *
 */
public class MainWindow extends JFrame {
	// aqui ira la parte de GUI
	private static final long serialVersionUID = 1L;
	
	private final String[] options = new String[] {"Borrar", "Cancelar"};
	
	private final ImageIcon icon = new ImageIcon("resources/img/icon.png");
	
	private BufferedImage imgTitle;
	private JLabel lbTitle;
	
	private BackgroundPanel bgPanel;
	private ManagerPanel mgPanel;
	private CRUDPanel crudPanel;
	private DataPanel dataPanel;
	private SearchPanel searchPanel;
	
	private DataTable dataTable;
	
	private InfoLabel infoLabel;
	
	public MainWindow() throws Exception {
		super("POKEDEX");
		this.setIconImage(icon.getImage());
		initComponents();
	}
	
	private void initComponents() throws Exception {
		this.setSize(465, 950);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		infoLabel = new InfoLabel("ADPD");
		
		initBackground();
		bgPanel.add(lbTitle, BorderLayout.NORTH);
		
		initOtherPanels();

		bgPanel.add(infoLabel);		
		
		bgPanel.add(searchPanel);
		bgPanel.add(dataTable);
		bgPanel.add(dataPanel);
		
		bgPanel.add(crudPanel);
		bgPanel.add(mgPanel);
		
		
		this.setContentPane(bgPanel);
	}
	
	private void initBackground() {
		try {
			bgPanel = new BackgroundPanel();
			imgTitle = ImageIO.read(new File("./resources/img/title.png"));
		} catch (Exception e) {
			sendErrorMsg(e.getMessage());
		}
		lbTitle = new JLabel();
		lbTitle.setIcon(new ImageIcon(new ImageIcon(imgTitle).getImage().getScaledInstance(465, 95, Image.SCALE_DEFAULT)));
		lbTitle.setBorder(new EmptyBorder(50, 0, 0, 0));
	}
	
	private void initOtherPanels() {
		mgPanel = new ManagerPanel();
		crudPanel = new CRUDPanel();
		dataPanel = new DataPanel();
		searchPanel = new SearchPanel();
		dataTable = new DataTable();
	}
	
	public ManagerPanel getManagerPanel() {
		return mgPanel;
	}
	public BackgroundPanel getBackgroundPanel() {
		return bgPanel;
	}
	public CRUDPanel getCRUDPanel() {
		return crudPanel;
	}
	public DataPanel getDataPanel() {
		return dataPanel;
	}
	public SearchPanel getSearchPanel() {
		return searchPanel;
	}
	public DataTable getDataTable() {
		return dataTable;
	}
	public void setDataTableModel(ArrayList<Pokemon> pokedex) {
		DataTableModel dtm = new DataTableModel(pokedex);
		dataTable.getTable().setModel(dtm);
		dtm.updateTable();
	}
	public void setInfoLabelText(String text) {
		infoLabel.setText(text);
	}
	public void sendErrorMsg(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	public void sendInfoMsg(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}
	public int sendDeleteMsg(String msg) {
		return JOptionPane.showOptionDialog(new JFrame(), msg, "DELETE", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
	public int sendOptionMsg(String msg) {
		return JOptionPane.showOptionDialog(new JFrame(), msg, "WARNING", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}
}
