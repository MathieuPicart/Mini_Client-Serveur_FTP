import java.io.PrintStream;
import java.io.File;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {
		File repertoire = null;

		//On vérifie si il y a un argument
		if(commandeArgs.length != 0) {
			//si oui on ajoute le chemin spécifié en argument au chemin courant a la commande
			repertoire = new File(ce.racinePath + ce.currentPath + "/" + commandeArgs[0]);

			//On vérifie que le chemin contient au moins la racine (que l'utilisateur ne cherche pas a remonter trop haut)
			if (!repertoire.toPath().normalize().toString().replace("\\", "/").contains(ce.racinePath)) {
				ps.println("2 Vous ne pouvez pas remonter si haut");
				return;
			}
		} else {
			//sinon on effectue la commande avec uniquement le chemin courant
			repertoire = new File(ce.racinePath + ce.currentPath);
		}

		//On enregistre le nom des dossier(s)/fichier(s) dans un tableau
		String liste[] = repertoire.list();
		String res = "";

		//Puis on l'affiche
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
