import java.io.*;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File dossier = new File("users/"+commandeArgs[0].toLowerCase());
		if (dossier.exists() && dossier.isDirectory()){
			ps.println("0 Commande user OK");
		} else {
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}
	}

}
