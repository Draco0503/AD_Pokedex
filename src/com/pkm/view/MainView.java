package com.pkm.view;

import java.util.HashMap;
import java.util.Scanner;

import com.pkm.model.entity.Pokemon;
import com.pkm.model.entity.Tipo;

/**
 * 
 * @author ADPD
 *
 */
public class MainView {
	// Attributes
	private Scanner in;
	
	// Constructors
	public MainView() {
		in = new Scanner(System.in);
	}

	/**
	 * A menu selection which also demands the user to introduce an option
	 * 
	 * @param men option of the console menu
	 * @return the option that is going to be used in the Controller
	 */
	public int managerMenu(int men) {
		String menuMsg = "";
		if (men == 1) {
			menuMsg = """
					***************************
					**        POKEDEX        **
					***************************
					**    [1] FileManager    **
					**    [2] XMLManager     **
					**    [3] DBManager      **
					**    [4] Hibernate      **
					**    [5] JSON           **
					**    [0] SALIR          **
					***************************
					Seleccione la opcion:
					""";
			}
		else if (men == 2)
			menuMsg = """
					***************************
					**        POKEDEX        **
					***************************
					**    [1] Crear nuevo    **
					**    [2] Borrar uno     **
					**    [3] Modificar      **
					**    [4] Ver uno        **
					**    [5] Ver todos      **
					**    [6] Exportar       **
					**    [0] SALIR          **
					***************************
					Seleccione la opcion:
					""";
		else if (men == 3)
			menuMsg = """
					****************************
					**     POKEDEX-UPDATE     **
					****************************
					**      [1] NOMBRE        **
					**      [2] TIPO 1        **
					**      [3] TIPO 2        **
					**      [4] SHINY         **
					**      [5] URL           **
					**      [0] SALIR         **
					****************************
					Seleccione la opcion:
					""";
		else
			menuMsg = """
					****************************
					**     POKEDEX-EXPORT     **
					****************************
					**      [1] FILE          **
					**      [2] XML           **
					**      [3] DB            **
					**      [4] Hibernate     **
					**      [5] JSON          **
					**      [0] SALIR         **
					****************************
					Seleccione la opcion:
					""";
		int opt = -1;
		boolean valid = false;
		while (!valid) {
			try {
				System.out.print(menuMsg);
				opt = Integer.parseInt(in.nextLine());
				valid = true;
			} catch (NumberFormatException e) {
				sendErrorMsg("Introduce un numero entero valido");
			}
		}
		return opt;
	}

	/**
	 * It makes sure that the user wants to save the changes
	 * 
	 * @return boolean which means the decision of the user
	 */
	public boolean menuGuardar() {
		boolean shi = true;
		System.out.println("Desea sobreescribir la operacion? [Y/n]");
		String opt = in.nextLine();
		if (opt.equals("n") || opt.equals("N"))
			shi = false;
		return shi;
	}

	/**
	 * Demands the new ID for the pokemon
	 * 
	 * @return Integer
	 *//*
	public int menuNumID() {
		boolean valid = false;
		int id = -1;
		while (!valid) {
			try {
				System.out.println("Inserte el ID del pokemon: ");
				id = Integer.parseInt(in.nextLine());
				valid = true;
			} catch (NumberFormatException e) {
				sendErrorMsg("Introduce un ID valido");
			}
		}
		return id;
	}*/
	
	/**
	 * Demands the new ID for the pokemon
	 * 
	 * @return Integer
	 */
	public int menuNumPokedex() {
		boolean valid = false;
		int id = -1;
		while (!valid) {
			try {
				System.out.println("Inserte el Nº de la pokedex: ");
				id = Integer.parseInt(in.nextLine());
				valid = true;
			} catch (NumberFormatException e) {
				sendErrorMsg("Introduce un numero entero valido");
			}
		}
		return id;
	}

	/**
	 * Demands the new name for the pokemon
	 * 
	 * @return String
	 */
	public String menuString(int opt) {
		String str = "";
		if (opt == 0)
			System.out.println("Nueva imagen del pokemon: ");
		else
			System.out.println("Inserte el nombre del pokemon: ");
		str = in.nextLine();
		return str;
	}

	/**
	 * Demands the new type/s for the pokemon
	 * 
	 * @return Tipo
	 */
	public int menuTipo(int orden) {
		int tipo = 19;
		String msg = """
				+---------------------------------------------------------+
				|                    SELECCIONA TIPO                      |
				+-----------------+-----------------+---------------------+
				| 1. ACERO        | 8.  HADA        | 15. SINIESTRO       |
				| 2. AGUA         | 9.  HIELO       | 16. TIERRA          |
				| 3. BICHO        | 10. LUCHA       | 17. VENENO          |
				| 4. DRAGON       | 11. NORMAL      | 18. VOLADOR         |
				| 5. ELECTRICO    | 12. PLANTA      | 19. NULL (MONOTIPO) |
				| 6. FANTASMA     | 13. PSIQUICO    |                     |
				| 7. FUEGO        | 14. ROCA        |                     |
				+-----------------+-----------------+---------------------+
				""";
		do {
			try {
				
				if (orden == 2)
					System.out.println("Inserte el 2º tipo del pokemon: \n"+msg);
				else
					System.out.println("Inserte el 1º tipo del pokemon: \n"+msg);
				tipo = Integer.parseInt(in.nextLine());
				if (tipo<=0 || tipo > 19) tipo = 19;
				
			} catch (NumberFormatException e) {
				sendErrorMsg("Introduce un numero entero valido");
			}
		} while (orden == 1 && tipo == 19);

		return tipo;

	}

	/**
	 * Demands if the pokemon is or not shiny
	 * 
	 * @return boolean
	 */
	public boolean menuShiny() {
		boolean shi = false;
		System.out.println("Es shiny? [y/N]");
		String opt = in.nextLine();
		if (opt.equals("y") || opt.equals("Y"))
			shi = true;
		return shi;
	}
	/**
	 * Shows all the pokemon in the pokedex
	 * 
	 * @param pokedex
	 */
	public void readAll(HashMap<Integer, Pokemon> pokedex) {//
		System.out.println("*********************\n**     POKEDEX     **");
		pokedex.forEach((k, v) -> System.out.println(v));
	}

	/**
	 * Shows a specific pokemon
	 * 
	 * @param pkm
	 */
	public void read(Pokemon pkm) {
		System.out.println(pkm);
	}

	// TODO methods that controls malfunctions in the program, made by the user
	/**
	 * It shows an error
	 * 
	 * @param msg
	 */
	public void sendErrorMsg(String msg) {
		System.err.println("[ERROR] - " + msg);
	}

	/**
	 * It shows information
	 * 
	 * @param msg
	 */
	public void sendInfoMsg(String msg) {
		System.out.println("[INFO] - " + msg);
	}

	/**
	 * It shows a warning
	 * 
	 * @param msg
	 */
	public void sendWarnMsg(String msg) {
		System.out.println("[WARN] - " + msg);
	}

	/**
	 * It closes the Scanner
	 */
	public void close() {
		in.close();
	}
}
