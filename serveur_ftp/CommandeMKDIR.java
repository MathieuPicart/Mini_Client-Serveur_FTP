import java.io.File;
import java.io.PrintStream;

public class CommandeMKDIR extends Commande{

    public CommandeMKDIR(PrintStream ps, String commandeStr) {
        super(ps, commandeStr);
    }

    public void execute(CommandExecutor ce) {
        File dossier = new File(ce.racinePath+ce.currentPath);
        if (dossier.exists() && dossier.isDirectory()){
            File newDossier = new File(ce.racinePath+ce.currentPath+"/"+commandeArgs[0]);
            if (!newDossier.exists() && (commandeArgs.length < 2) && newDossier.toPath().normalize().toString().replace("\\", "/").contains(ce.racinePath)) {
                newDossier.mkdir();
                ps.println("0 Commande mkdir OK");
            } else {
                ps.println("2 Vous n'avez pas les droits de crée un dossier ici");
            }
        } else {
            ps.println("2 Erreur de merde jsp");
        }

    }

}
