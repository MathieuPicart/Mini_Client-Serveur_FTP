import java.io.PrintStream;
import java.io.File;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		/*File repertoire = new File(CommandExecutor.currentPath);
		String liste[] = repertoire.list();

		if (liste != null) {
			for (int i = 0; i < liste.length; i++) {
				System.out.println(liste[i]);
			}
		} else {
			System.err.println("Nom de repertoire invalide");
		}*/
	}

}
