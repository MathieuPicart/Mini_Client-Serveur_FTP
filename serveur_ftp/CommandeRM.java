import java.io.File;

import java.io.PrintStream;

public class CommandeRM extends Commande {

    public CommandeRM(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute(CommandExecutor ce) {
        if(commandeArgs.length!=1){
            ps.println("2 Nombre d'arguments incorrect");
            return;
        }

        File newDossier = new File(ce.racinePath+ce.currentPath+"/"+commandeArgs[0]);
        //On vérifie que le dossier existe pas déjà et on vérifie que le chemin contient au moins la racine (que l'utilisateur ne cherche pas a remonter trop haut)
        if (newDossier.exists() && (commandeArgs.length < 2) && newDossier.toPath().normalize().toString().replace("\\", "/").contains(ce.racinePath)) {
            //On vérifie on peux le supprimer (si le dossier est vide), si oui on le supprime
            if(newDossier.delete()) {
                ps.println("0 Commande rmdir OK, fichier supprimé");
            } else {
                //Sinon on affiche une erreur
                ps.println("2 Le dossier n'a pas pu etre supprimé, Verifier s'il ne contient rien");
            }
        } else {
            ps.println("2 Vous n'avez pas les droits de supprimer ce dossier");
        }
    }
}
