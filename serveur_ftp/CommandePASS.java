import java.io.*;
;import java.io.FileReader;
import java.nio.Buffer;

public class CommandePASS extends Commande {
	
	public CommandePASS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		try {
			BufferedReader file = new BufferedReader(new FileReader("users/"+CommandExecutor.user+"/pssd.txt"));
			String pssd = file.readLine();
			if(commandeArgs[0].toLowerCase().equals(pssd)) {
				CommandExecutor.pwOk = true;
				CommandExecutor.currentPath = "";
				CommandExecutor.racinePath = "users/"+CommandExecutor.user+"/racine";
				ps.println("1 Commande pass OK");
				ps.println("0 Vous êtes bien connecté sur notre serveur");
			}
			else {
				ps.println("2 Le mode de passe est faux");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
