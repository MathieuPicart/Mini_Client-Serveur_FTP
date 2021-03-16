import java.io.File;

import java.io.PrintStream;

public class CommandeRMDIR extends Commande {

    public CommandeRMDIR(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute(CommandExecutor ce) {
        File dossier = new File(ce.racinePath+ce.currentPath);
        if (dossier.exists() && dossier.isDirectory()){
            File newDossier = new File(ce.racinePath+ce.currentPath+"/"+commandeArgs[0]);
            if (newDossier.exists() && (commandeArgs.length < 2) && newDossier.toPath().normalize().toString().replace("\\", "/").contains(ce.racinePath)) {
                if(newDossier.delete()) {
                    ps.println("0 Commande rmdir OK, fichier supprimé");
                } else {
                    ps.println("2 Le dossier n'a pas pu etre supprimé, Verifier s'il ne contient rien");
                }
            } else {
                ps.println("2 Vous n'avez pas les droits de supprimer ce dossier");
            }
        } else {
            ps.println("2 Erreur de merde jsp");
        }

    }
}
