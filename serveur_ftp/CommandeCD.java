import java.io.*;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File dossier = new File(CommandExecutor.racinePath + commandeArgs[0]);
		if (dossier.exists() && dossier.isDirectory()) {
			if (dossier.toPath().normalize().toString().replace("\\", "/").contains(CommandExecutor.racinePath)) {
				CommandExecutor.currentPath = dossier.toPath().normalize().toString().replace("\\", "/").replace(CommandExecutor.racinePath,"");
				ps.println("0 Commande cd OK");
			} else {
				ps.println("2 Vous n'avez pas acces au dossier");
			}
		} else {
			ps.println("2 Le dossier " + commandeArgs[0] + " n'existe pas");
		}
	}

}
