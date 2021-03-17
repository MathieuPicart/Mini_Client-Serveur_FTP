import java.io.*;
;import java.io.FileReader;
import java.nio.Buffer;

public class CommandePASS extends Commande {
	
	public CommandePASS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {
		if(commandeArgs.length!=1){
			ps.println("2 Nombre d'arguments incorrect");
			return;
		}

		try {
			BufferedReader file = new BufferedReader(new FileReader("users/"+ce.login+"/pssd.txt"));
			String pssd = file.readLine();
			//On vérifie si le mdp saisie par l'utilisateur est bien égale a celui stocké dans pssd.txt
			if(commandeArgs[0].toLowerCase().equals(pssd)) {
				//Si oui on passe "pwOk" a true pour pouvoir passer a la suite
				//On initialise aussi currentPath et racinePath pour cette utilisateur spécifique
				ce.pwOk = true;
				ce.currentPath = "";
				ce.racinePath = "users/"+ce.login+"/racine";
				ps.println("0 Vous êtes bien connecté sur notre serveur");
			}
			else {
				//Sinon on affiche une erreur
				ps.println("2 Le mode de passe est faux");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
