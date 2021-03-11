import java.io.PrintStream;
import java.io.File;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File repertoire = new File("users/");
		String liste[] = repertoire.list();
		String res = "";

		if (liste != null) {
			for (int i = 0; i < liste.length; i++) {
				System.out.println(liste[i]);
				res += liste[i] + " \t";
			}
			ps.println("0 " + res);
		} else {
			ps.println("2 Nom de repertoire invalide");
		}
	}

}
