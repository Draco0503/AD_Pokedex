package com.pkm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.pkm.model.entity.Pokemon;
import com.pkm.model.entity.Tipo;
import com.pkm.model.manager.DAOManager;
import com.pkm.model.manager.DBPokemon;
import com.pkm.model.manager.DBTipo;
import com.pkm.model.manager.FilePokemon;
import com.pkm.model.manager.FileTipo;
import com.pkm.model.manager.HBPokemon;
import com.pkm.model.manager.HBTipo;
import com.pkm.model.manager.JSONPokemon;
import com.pkm.model.manager.JSONTipo;
import com.pkm.model.manager.XMLPokemon;
import com.pkm.model.manager.XMLTipo;
import com.pkm.utils.LeerProperties;
import com.pkm.view.MainView;
import com.pkm.view.MainWindow;

/**
 * 
 * @author ADPD
 *
 */

public class Controller {

	private final String FILE_PROPERTIES = "resources/config/project.properties";

	// Attributes
	private Pokemon pkm;
	private MainView console;
	private MainWindow gui;
	private int opt;
	private DAOManager<Pokemon, Integer> daoManager;
	private DAOManager<Tipo, Integer> daoManagerc2;
	private String label;
	private LeerProperties prop;

	// Constructors
	public Controller(Pokemon pkm, MainView console, MainWindow gui, int opt) {

		try {

			this.pkm = pkm;
			this.console = console;
			this.gui = gui;
			this.opt = opt;
			label = "";
			prop = new LeerProperties(FILE_PROPERTIES);

		} catch (IOException e) {
			console.sendErrorMsg("fichero .properties no encontrado, se fue a por tabaco");
		}
	}

	/**
	 * Due o the option, we run the Console mode or the GUI mode
	 * 
	 * @throws Exception
	 */
	public void run() throws Exception {
		if (opt == 0)
			runConsoleMode();
		else if (opt == 1)
			runGUIMode();
		else
			console.sendErrorMsg("[RUN-ERROR]: Wrong option");
	}

	// Interface mode
	private void runGUIMode() {
		// TODO
		try {
			gui.setVisible(true);
			setManagerListeners(0);
			setCRUDListeners();
			setDataTableListener();
		} catch (Exception e) {
			gui.sendErrorMsg(e.getMessage());
		}
	}

	/**
	 * Console mode Menu where we can choose where are we going to work
	 * 
	 * @throws Exception
	 */
	private void runConsoleMode() throws Exception {

		Scanner in = new Scanner(System.in);
		int o = -1;

		// FILE/XML/DB
		while (o != 0) {
			o = console.managerMenu(1);
			try {
				switch (o) {
				case 1: // FILE MANAGER
					String ffile = getProperty("fm.file_name");
					String ffile2 = getProperty("fm.file_name2");
					daoManagerc2 = new FileTipo(ffile2);
					daoManager = new FilePokemon(ffile, daoManagerc2);
					crudMenu(in);
					break;
				case 2: // XML MANAGER
					String xmlfile = getProperty("xml.file_name");
					String xmlfile2 = getProperty("xml.file_name2");
					daoManagerc2 = new XMLTipo(xmlfile2);
					daoManager = new XMLPokemon(xmlfile, daoManagerc2);
					crudMenu(in);
					break;
				case 3: // DB MANAGER
					String driver = getProperty("db.driver");
					String dbname = getProperty("db.dbname");
					String hostname = getProperty("db.hostname");
					String port = getProperty("db.port");
					String user = getProperty("db.username");
					String pwd = getProperty("db.password");
					daoManagerc2 = new DBTipo(user, pwd, driver, hostname, dbname, port);
					daoManager = new DBPokemon(user, pwd, driver, hostname, dbname, port, daoManagerc2);
					crudMenu(in);
					break;
				case 4: // HB MANAGER
					daoManagerc2 = new HBTipo();
					daoManager = new HBPokemon();
					crudMenu(in);
					break;
				case 5: // JSON MANAGER
					String server_path = getProperty("json.server_path");
					String model_path_tipo = getProperty("json.model_path_tipo");
					String model_path_pokemon = getProperty("json.model_path_pokemon");
					daoManagerc2 = new JSONTipo(server_path, model_path_tipo);
					daoManager = new JSONPokemon(server_path, model_path_pokemon, daoManagerc2);
					crudMenu(in);
					break;
				case 0: // EXIT
					in.close();
					console.sendInfoMsg("Gracias por usar este programa, adios (^-^(ºwº(^w^(ºoº)");
					break;
				default: // NOT A VALID OPTION
					console.sendWarnMsg("Opcion no valida");
				}
			} catch (Exception e) {
				console.sendErrorMsg(e.getMessage());

			}

		}

	}

	/**
	 * Menu where we can choose which operation CRUD we want
	 * 
	 * @param in         Scanner object
	 * @param daoManager Interface object
	 */
	private void crudMenu(Scanner in) {

		int num_pokedex;
		String nombre, url = "URL"; // This is will be more important in the GUI
		Tipo tipo1, tipo2;
		boolean shiny;
		HashMap<Integer, Pokemon> pokedex;
		pkm = null;
		int o = -1;
		while (o != 0) {
			o = console.managerMenu(2);
			try {
				switch (o) {
				case 1: // CREATE
					num_pokedex = console.menuNumPokedex();
					nombre = console.menuString(1);

					tipo1 = daoManagerc2.read(console.menuTipo(1));
					tipo2 = daoManagerc2.read(console.menuTipo(2));
					shiny = console.menuShiny();
					if (daoManager.create(new Pokemon(0, num_pokedex, nombre, tipo1, tipo2, shiny, url)) == -1)
						console.sendWarnMsg("Pokemon ya existente por lo que no se ha creado");
					else
						console.sendInfoMsg("Pokemon creado con exito");
					break;
				case 2: // DELETE
					num_pokedex = console.menuNumPokedex();
					if (daoManager.delete(num_pokedex) == -1)
						console.sendWarnMsg("Pokemon no existente por lo que no se ha borrado");
					else
						console.sendInfoMsg("Pokemon borrado con exito");
					break;
				case 3: // UPDATE
					num_pokedex = console.menuNumPokedex();
					pkm = daoManager.read(num_pokedex);
					if (pkm == null)
						console.sendWarnMsg("ID no encontrado");
					else {
						console.read(pkm);
						int opt = -1;
						do {
							opt = console.managerMenu(3);
							switch (opt) {
							case 1:
								pkm.setNombre(console.menuString(2));
								break;
							case 2:
								pkm.setTipo1(daoManagerc2.read(console.menuTipo(1)));
								break;
							case 3:
								pkm.setTipo2(daoManagerc2.read(console.menuTipo(2)));
								break;
							case 4:
								pkm.setShiny(console.menuShiny());
								break;
							case 5:
								pkm.setUrl(console.menuString(1));
								break;
							case 0:
								if (daoManager.update(pkm, pkm.getNum_pokedex()) == -1)
									console.sendErrorMsg("No se ha podido modificar");
								else
									console.sendInfoMsg("Pokemon modificado con exito");
								break;
							default:
								console.sendWarnMsg("Opcion no valida ceporro");
							}
						} while (opt != 0);
					}
					break;
				case 4: // READ
					num_pokedex = console.menuNumPokedex();
					pkm = daoManager.read(num_pokedex);
					if (pkm == null)
						console.sendWarnMsg("Pokemon no encontrado");
					else
						console.read(pkm);
					break;
				case 5: // READ ALL
					pokedex = daoManager.readAll();
					if (pokedex.isEmpty())
						console.sendWarnMsg("No hay datos en la pokedex");
					else
						console.readAll(pokedex);
					break;
				case 6: // EXPORT
					pokedex = daoManager.readAll();
					if (pokedex.isEmpty())
						console.sendWarnMsg("No hay datos en la pokedex");
					else {
						int opt = -1;
						boolean valid = false;
						DAOManager<Pokemon, Integer> exportacion = null;
						do {
							opt = console.managerMenu(4);
							switch (opt) {
							case 1: // to .txt
								String ffile = getProperty("fm.file_name");
								exportacion = new FilePokemon(ffile, pokedex);
								valid = true;
								break;
							case 2: // to .xml
								String xmlfile = getProperty("xml.file_name");
								exportacion = new XMLPokemon(xmlfile, pokedex);
								valid = true;
								break;
							case 3: // to db
								String driver = getProperty("db.driver");
								String dbname = getProperty("db.dbname");
								String hostname = getProperty("db.hostname");
								String port = getProperty("db.port");
								String user = getProperty("db.username");
								String pwd = getProperty("db.password");
								exportacion = new DBPokemon(user, pwd, driver, hostname, dbname, port, pokedex);
								valid = true;
								break;
							case 4: // to Hibernate
								exportacion = new HBPokemon(pokedex);
								valid = true;
								break;
							case 5: // to JSON
								String server_path = getProperty("json.server_path");
								String model_path_pokemon = getProperty("json.model_path_pokemon");
								exportacion = new JSONPokemon(server_path, model_path_pokemon, pokedex);
								valid = true;
								break;
							case 0:
								console.sendInfoMsg("Saliendo de la operacion, nada que guardar");
								break;
							default:
								console.sendWarnMsg("Opcion no valida ceporro");
							}
							if (valid && console.menuGuardar()) {
								exportacion.guardar();
								console.sendInfoMsg("Informacion exportada con exito");
								opt = 0;
							}
						} while (opt != 0);
					}
					;
					break;
				case 0: // EXIT
					daoManager.guardar();
					console.sendInfoMsg("SALIENDO DEL MANAGER");
					break;
				default:// NOT A VALID OPTION
					console.sendWarnMsg("Opcion no valida");
				}
			} catch (Exception e) {
				console.sendErrorMsg(e.getMessage());
			}
		}
	}

	private void crudMenu(int opt) {
		try {
			if (opt == 0) {
				String ffile = getProperty("fm.file_name");
				String ffile2 = getProperty("fm.file_name2");
				daoManagerc2 = new FileTipo(ffile2);
				daoManager = new FilePokemon(ffile, daoManagerc2);

			} else if (opt == 1) {
				String xmlfile = getProperty("xml.file_name");
				String xmlfile2 = getProperty("xml.file_name2");
				daoManagerc2 = new XMLTipo(xmlfile2);
				daoManager = new XMLPokemon(xmlfile, daoManagerc2);

			} else if (opt == 2) {
				String driver = getProperty("db.driver");
				String dbname = getProperty("db.dbname");
				String hostname = getProperty("db.hostname");
				String port = getProperty("db.port");
				String user = getProperty("db.username");
				String pwd = getProperty("db.password");
				daoManagerc2 = new DBTipo(user, pwd, driver, hostname, dbname, port);
				daoManager = new DBPokemon(user, pwd, driver, hostname, dbname, port, daoManagerc2);

			} else if (opt == 3) {

				daoManagerc2 = new HBTipo();
				daoManager = new HBPokemon();

			} else {
				gui.sendErrorMsg("Opcion no valida");
			}
			gui.getDataPanel().initComboBoxes(daoManagerc2.readAll());
			gui.getManagerPanel().setVisible(false);
			gui.getCRUDPanel().setVisible(true);

		} catch (Exception e) {
			gui.sendErrorMsg(e.getMessage());
			gui.getManagerPanel().setVisible(true);
		}
	}

	private void setManagerListeners(int opt) {
		gui.getManagerPanel().setExitListener((e) -> {
			console.sendInfoMsg("MANAGER_PANEL-EXIT_" + opt + "-BUTTON clicked");
			if (opt == 0)
				System.exit(0);
			else if (opt == 1) {
				gui.setInfoLabelText(label);
				gui.getManagerPanel().setVisible(false);
				gui.getCRUDPanel().setVisible(true);
				gui.getManagerPanel().resetListeners();
				setManagerListeners(0);
			}
		});
		gui.getManagerPanel().setFileListener((e) -> {
			console.sendInfoMsg("MANAGER_PANEL-FILE_" + opt + "-BUTTON clicked");
			if (opt == 0) {
				label = "FILE";
				gui.setInfoLabelText(label);
				crudMenu(0);
			} else if (opt == 1) {
				try {
					String ffile = getProperty("fm.file_name");
					FilePokemon fm = new FilePokemon(ffile, daoManager.readAll());
					int confirm = gui.sendOptionMsg("¿Desea sobreescribir los datos?");
					if (confirm == 0) {
						fm.guardar();
						gui.sendInfoMsg("La operacion se ha realizado con exito");
					} else {
						gui.sendInfoMsg("Operacion cancelada");
					}

				} catch (Exception e1) {
					gui.sendErrorMsg(e1.getMessage());

				}
			}

		});
		gui.getManagerPanel().setXMLListener((e) -> {
			console.sendInfoMsg("MANAGER_PANEL-XML_" + opt + "-BUTTON clicked");
			if (opt == 0) {
				label = "XML";
				gui.setInfoLabelText(label);
				crudMenu(1);
			} else if (opt == 1) {
				try {
					String xmlfile = getProperty("xml.file_name");
					XMLPokemon xml = new XMLPokemon(xmlfile, daoManager.readAll());
					int confirm = gui.sendOptionMsg("¿Desea sobreescribir los datos?");
					if (confirm == 0) {

						xml.guardar();

						gui.sendInfoMsg("La operacion se ha realizado con exito");
					} else {
						gui.sendInfoMsg("Operacion cancelada");
					}
				} catch (Exception e1) {
					gui.sendErrorMsg(e1.getMessage());
				}
			}
		});
		gui.getManagerPanel().setDBListener((e) -> {
			console.sendInfoMsg("MANAGER_PANEL-DB_" + opt + "-BUTTON clicked");
			// gui.sendInfoMsg("Opcion en desarrollo");
			if (opt == 0) {
				label = "DB";
				gui.setInfoLabelText(label);
				crudMenu(2);
			} else if (opt == 1) {
				try {
					String driver = getProperty("db.driver");
					String dbname = getProperty("db.dbname");
					String hostname = getProperty("db.hostname");
					String port = getProperty("db.port");
					String user = getProperty("db.username");
					String pwd = getProperty("db.password");

					DBPokemon dbm = new DBPokemon(user, pwd, driver, hostname, dbname, port);
					int confirm = gui.sendOptionMsg("¿Desea sobreescribir los datos?");
					if (confirm == 0) {
						dbm.guardar();
						gui.sendInfoMsg("La operacion se ha realizado con exito");
					} else {
						gui.sendInfoMsg("Operacion cancelada");
					}

				} catch (Exception e1) {
					gui.sendErrorMsg(e1.getMessage());
				}
			}
		});
		gui.getManagerPanel().setHBListener((e) -> {
			console.sendInfoMsg("MANAGER_PANEL-HIBERNATE_" + opt + "-BUTTON clicked");
			if (opt == 0) {
				label = "HB";
				gui.setInfoLabelText(label);
				crudMenu(3);
			} else if (opt == 1) {
				try {
					HBPokemon hb = new HBPokemon(daoManager.readAll());
					int confirm = gui.sendOptionMsg("¿Desea sobreescribir los datos?");
					if (confirm == 0) {

						hb.guardar();

						gui.sendInfoMsg("La operacion se ha realizado con exito");
					} else {
						gui.sendInfoMsg("Operacion cancelada");
					}
				} catch (Exception e1) {
					gui.sendErrorMsg(e1.getMessage());
				}
			}
		});
	}

	private void setCRUDListeners() {
		try {
			
			gui.getCRUDPanel().setCreateListener((e) -> {
				console.sendInfoMsg("CRUD_PANEL-CREATE-BUTTON clicked");
				gui.setInfoLabelText("CREATE");
				gui.getCRUDPanel().setVisible(false);
				gui.getDataPanel().setVisible(true);
				gui.getDataPanel().resetListeners();
				setDataListeners(0);
			});
			gui.getCRUDPanel().setReadListener((e) -> {
				console.sendInfoMsg("CRUD_PANEL-READ-BUTTON clicked");
				gui.setInfoLabelText("READ");
				try {
					setSearchListeners(0);
					gui.getCRUDPanel().setVisible(false);
					gui.getSearchPanel().setVisible(true);
				} catch (Exception e1) {
					gui.sendErrorMsg(e1.getMessage());
				}
			});
			gui.getCRUDPanel().setReadAllListener((e) -> {
				console.sendInfoMsg("CRUD_PANEL-READALL-BUTTON clicked");
				gui.setInfoLabelText("READ ALL");
				gui.getCRUDPanel().setVisible(false);
				try {
					ArrayList<Pokemon> al = new ArrayList<>();
					daoManager.readAll().forEach((k, v) -> {
						al.add(v);
					});
					gui.setDataTableModel(al);
					gui.getDataTable().setVisible(true);
				} catch (Exception e1) {
					gui.sendErrorMsg(e1.getMessage());

				}
			});
			gui.getCRUDPanel().setUpdateListener((e) -> {
				console.sendInfoMsg("CRUD_PANEL-UPDATE-BUTTON clicked");
				gui.setInfoLabelText("UPDATE");
				gui.getCRUDPanel().setVisible(false);
				gui.getDataPanel().setVisible(true);
				gui.getDataPanel().resetListeners();
				setDataListeners(1);
			});
			gui.getCRUDPanel().setDeleteListener((e) -> {
				console.sendInfoMsg("CRUD_PANEL-DELETE-BUTTON clicked");
				gui.setInfoLabelText("DELETE");
				try {
					setSearchListeners(1);
					gui.getCRUDPanel().setVisible(false);
					gui.getSearchPanel().setVisible(true);
				} catch (Exception e1) {
					gui.sendErrorMsg(e1.getMessage());

				}
			});
			gui.getCRUDPanel().setExportListener((e) -> {
				console.sendInfoMsg("CRUD_PANEL-EXPORT-BUTTON clicked");
				gui.setInfoLabelText("EXPORT");
				gui.getManagerPanel().resetListeners();
				setManagerListeners(1);
				gui.getCRUDPanel().setVisible(false);
				gui.getManagerPanel().setVisible(true);
			});
			gui.getCRUDPanel().setExitListener((e) -> {
				console.sendInfoMsg("CRUD_PANEL-EXIT-BUTTON clicked");
				gui.setInfoLabelText("ADPD");
				try {
					daoManager.guardar();
					gui.getManagerPanel().setVisible(true);
					gui.getCRUDPanel().setVisible(false);
				} catch (Exception e1) {
					gui.sendErrorMsg(e1.getMessage());

				}
			});
		} catch (Exception e) {
			gui.sendErrorMsg(e.getMessage());
		}
	}

	private void setDataListeners(int opt) {
		
		gui.getDataPanel().setSaveListenter((e) -> {
			console.sendInfoMsg("DATA_PANEL-SAVE_" + opt + "-BUTTON clicked");
			int num_pokedex = gui.getDataPanel().getNumPokedex();
			String nombre = gui.getDataPanel().getText();
			Tipo tipo1 = gui.getDataPanel().getTipo(0);
			Tipo tipo2 = gui.getDataPanel().getTipo(1);
			boolean shiny = gui.getDataPanel().isShiny();
			String url = gui.getDataPanel().getUrl();
			if (url == null || url.equals(""))
				url = "URL";
			try {
				if (opt == 0) {
					pkm = new Pokemon(num_pokedex, nombre, tipo1, tipo2, shiny, url);
					if (daoManager.create(pkm) == 0) {
						gui.sendInfoMsg("Pokemon creado con exito");
					} else {
						gui.sendErrorMsg("No se ha podido crear");
					}
				} else if (opt == 1) {
					pkm = daoManager.read(num_pokedex);
					if (pkm == null) {
						gui.sendErrorMsg("Ese pokemon no existe");
					} else {
						if (!nombre.equals(pkm.getNombre()))
							pkm.setNombre(nombre);
						if (!tipo1.equals(pkm.getTipo1()) && tipo1 != null)
							pkm.setTipo1(tipo1);
						if (!tipo2.equals(pkm.getTipo2()))
							pkm.setTipo2(tipo2);
						else if (pkm.getTipo1().equals(tipo2))
							pkm.setTipo2(null);
						pkm.setShiny(shiny);
						if (!url.equals(pkm.getUrl()))
							pkm.setUrl(url);

						daoManager.update(pkm, num_pokedex);
						gui.sendInfoMsg("Pokemon modificado con exito");
					}
				}
			} catch (Exception e1) {
				gui.sendErrorMsg(e1.getMessage());
			}
		});
		gui.getDataPanel().setExitListenter((e) -> {
			console.sendInfoMsg("DATA_PANEL-EXIT_" + opt + "-BUTTON clicked");
			gui.getDataPanel().resetListeners();
			gui.setInfoLabelText(label);
			gui.getDataPanel().setVisible(false);
			gui.getCRUDPanel().setVisible(true);
		});
	}

	private void setSearchListeners(int opt) {
		gui.getSearchPanel().setSearchListener((e) -> {
			console.sendInfoMsg("SEARCH_PANEL-SEARCH_" + opt + "-BUTTON clicked");
			try {
				int id = gui.getSearchPanel().getId();
				pkm = daoManager.read(id);
				if (pkm == null) {
					gui.sendErrorMsg("Pokemon no encontrado");
				} else {
					if (opt == 0) {
						gui.sendInfoMsg(pkm.toString());
					} else if (opt == 1) {
						int outOpt = gui.sendDeleteMsg(pkm.toString());
						if (outOpt == 0) {
							if (daoManager.delete(id) == 0) {
								gui.sendInfoMsg("Se ha borrado con exito");
							} else {
								gui.sendErrorMsg("No se ha podido borrar");
							}
						}
					}
				}
			} catch (Exception e1) {
				gui.sendErrorMsg(e1.getMessage());
			}
		});
		gui.getSearchPanel().setExitListener((e) -> {
			console.sendInfoMsg("SEARCH_PANEL-EXIT_" + opt + "-BUTTON clicked");
			gui.getSearchPanel().resetListeners();
			gui.setInfoLabelText(label);
			gui.getSearchPanel().setVisible(false);
			gui.getCRUDPanel().setVisible(true);
		});
	}

	private void setDataTableListener() {
		gui.getDataTable().setExitListener((e) -> {
			console.sendInfoMsg("DATA-TABLE_PANEL-EXIT-BUTTON clicked");
			gui.setInfoLabelText(label);
			gui.getDataTable().setVisible(false);
			gui.getCRUDPanel().setVisible(true);
		});
	}

	/**
	 * Returns the the value of the propertie chosen by its key
	 * 
	 * @param opt
	 * @return String
	 */
	private String getProperty(String propiedad) {
		return prop.getCollector().get(propiedad);
	}

}
