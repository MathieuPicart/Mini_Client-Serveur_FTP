import java.io.File;
import java.io.PrintStream;

public class CommandeMKDIR extends Commande{

    public CommandeMKDIR(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute(CommandExecutor ce) {
        if(commandeArgs.length!=1){
            ps.println("2 Nombre d'arguments incorrect");
            return;
        }

        File newDossier = new File(ce.racinePath+ce.currentPath+"/"+commandeArgs[0]);
        //On vérifie que le dossier n'existe pas déjà et on vérifie que le chemin contient au moins la racine (que l'utilisateur ne cherche pas a remonter trop haut)
        if (!newDossier.exists() && (commandeArgs.length < 2) && newDossier.toPath().normalize().toString().replace("\\", "/").contains(ce.racinePath)) {
            //Si oui on le créer
            newDossier.mkdir();
            ps.println("0 Commande mkdir OK");
        } else {
            //Sinon on affiche une erreur
            ps.println("2 Vous n'avez pas les droits de crée un dossier ici");
        }
    }

}
