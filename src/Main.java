import javax.swing.SwingUtilities;

import com.pkm.controller.Controller;
import com.pkm.model.entity.Pokemon;
import com.pkm.view.MainView;
import com.pkm.view.MainWindow;

/**
 * 
 * @author ADPD
 *
 */
public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * We create a Controller object which has the essential methods to run the main
			 * program
			 */
			@Override
			public void run() {
				Pokemon pkmn = new Pokemon();
				MainView console = new MainView();
				
				try {
					MainWindow gui = new MainWindow();
					int opt = Integer.parseInt(args[0]);
					Controller ctrl = new Controller(pkmn, console, gui, opt);
					ctrl.run();

				} catch (NumberFormatException e) {
					System.err.println("[ERROR]: Number is required");
				} catch (Exception e) {
					System.err.println(e.getMessage());
					System.err.println("Usage: 0 for batch mode, 1 for GUI mode (On going)");
				}

			} // run()
		});
	}
}
