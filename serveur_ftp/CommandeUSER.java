import java.io.PrintStream;
import java.io.File;


public class CommandeUSER extends Commande {

	public CommandeUSER(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {
		File dossier = new File("users/"+commandeArgs[0].toLowerCase());
		//On vérifie si il y a bien un dossier (autrement dis une session) associé a ce "user"
		if (dossier.exists() && dossier.isDirectory()){
			//Si oui on passe "userOk" a true pour pouvoir passer a la suite
			ce.userOk = true;
			ce.user = commandeArgs[0].toLowerCase();
			ps.println("0 Commande user OK");
		} else {
			//Sinon on affiche une erreur
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}
		
	}

}
