import java.io.PrintStream;

public class CommandePASS extends Commande {
	
	public CommandePASS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		if(!CommandExecutor.userOk){
			ps.println("2 User non saisie");
		}
		else if(commandeArgs[0].toLowerCase().equals("abcd")) {
			CommandExecutor.pwOk = true;
			ps.println("1 Commande pass OK");
			ps.println("0 Vous êtes bien connecté sur notre serveur");

		}
		else {
			ps.println("2 Le mode de passe est faux");
		}
		
	}

}
