import java.io.File;
import java.io.PrintStream;

public class CommandePWD extends Commande {
	
	public CommandePWD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	//On affiche le dossier courant
	public void execute(CommandExecutor ce) {
		ps.println("0 /" + ce.currentPath);
	}

}
