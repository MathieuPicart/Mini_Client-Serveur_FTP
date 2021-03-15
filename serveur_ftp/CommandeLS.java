import java.io.PrintStream;
import java.io.File;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {
		File repertoire = null;
		if(commandeArgs.length != 0) {
			repertoire = new File(ce.racinePath + ce.currentPath + "/" + commandeArgs[0]);

			if (!repertoire.toPath().normalize().toString().replace("\\", "/").contains(ce.racinePath)) {
				ps.println("2 Vous ne pouvez pas remonter si haut");
				return;
			}
		} else {
			repertoire = new File(ce.racinePath + ce.currentPath);
		}

		String liste[] = repertoire.list();
		String res = "";

		if (liste != null) {
			for (int i = 0; i < liste.length; i++) {
				res += liste[i] + " \t";
			}
			ps.println("0 " + res);
			return;
		} else {
			ps.println("2 Nom de repertoire invalide");
			return;
		}
	}

}
