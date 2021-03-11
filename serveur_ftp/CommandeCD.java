import java.io.*;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File dossier = new File(CommandExecutor.racinePath + commandeArgs[0]);
		System.out.println(CommandExecutor.racinePath);
		System.out.println("Path file : " + dossier.getAbsolutePath().toString());
		if (dossier.exists() && dossier.isDirectory()) {
			if (dossier.getPath().contains(CommandExecutor.racinePath)) {
				ps.println("0 Commande cd OK");
			} else {
				ps.println("2 Vous n'avez pas acces au dossier " + commandeArgs[0]);
			}
		} else {
			ps.println("2 Le dossier " + commandeArgs[0] + " n'existe pas");
		}
	}

}
