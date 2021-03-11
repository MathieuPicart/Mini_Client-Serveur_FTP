import java.io.File;
import java.io.PrintStream;

public class CommandePWD extends Commande {
	
	public CommandePWD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		ps.println("0 /" + CommandExecutor.currentPath);
	}

}
