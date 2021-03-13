import java.io.PrintStream;
import java.io.File;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File repertoire = null;
		if(commandeArgs.length != 0) {
			repertoire = new File(CommandExecutor.racinePath + CommandExecutor.currentPath + "/" + commandeArgs[0]);

			if (!repertoire.toPath().normalize().toString().replace("\\", "/").contains(CommandExecutor.racinePath)) {
				ps.println("2 Vous ne pouvez pas remonter si haut");
				return;
			}
		} else {
			repertoire = new File(CommandExecutor.racinePath + CommandExecutor.currentPath);
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
