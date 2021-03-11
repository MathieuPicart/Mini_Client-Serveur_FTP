import java.io.*;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File dossier = new File(CommandExecutor.racinePath + commandeArgs);
		if (dossier.exists() && dossier.isDirectory()){
			ps.println("0 Commande cd OK");
		} else {
			ps.println("2 Le dossier " + commandeArgs[0] + " n'existe pas");
		}
	}

}
