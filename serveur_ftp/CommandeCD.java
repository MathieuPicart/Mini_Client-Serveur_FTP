import java.io.*;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {
		//On vérifie que le dossier vers lequel se déplacer est bien spécifié
		if(commandeArgs.length > 0) {
			File dossier = new File(ce.racinePath + ce.currentPath + "/" + commandeArgs[0]);
			//On vérifie le dossier spécifié existe bien
			if (dossier.exists() && dossier.isDirectory()) {
				//On vérifie que le chemin contient au moins la racine (que l'utilisateur ne cherche pas a remonter trop haut)
				if (dossier.toPath().normalize().toString().replace("\\", "/").contains(ce.racinePath)) {
					//Si oui on redéfini le currentPath
					ce.currentPath = dossier.toPath().normalize().toString().replace("\\", "/").replace(ce.racinePath,"");
					ps.println("0 Commande cd OK");
				} else {
					ps.println("2 Vous n'avez pas acces au dossier");
				}
			} else {
				ps.println("2 Le dossier " + commandeArgs[0] + " n'existe pas");
			}
		} else {
			ps.println("2 Veuillez préciser le dossier vers lequel se déplacer");
		}
	}

}
