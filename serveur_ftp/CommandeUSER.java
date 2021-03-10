import java.io.PrintStream;
import java.io.File;


public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		File dossier = new File("users/"+commandeArgs[0].toLowerCase());
		if (dossier.exists() && dossier.isDirectory()){
			CommandExecutor.userOk = true;
			CommandExecutor.user = "users/"+commandeArgs[0].toLowerCase();
			ps.println("0 Commande user OK");
		} else {
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}
		
	}

}
