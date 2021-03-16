import java.io.PrintStream;

public class CommandExecutor {

	public boolean userOk;
	public String user;
	public String racinePath;
	public String currentPath;
	public boolean pwOk;
	public int port;

	public CommandExecutor(int port) {
		this.userOk = false;
		this.user = null;
		this.racinePath = null;
		this.currentPath = null;
		this.pwOk = false;
		this.port = port;
	}
	
	public void executeCommande(PrintStream ps, String commande) throws Exception{
		if(userOk && pwOk) {
			switch (commande.split(" ")[0]) {
				case "cd" :
					(new CommandeCD(ps, commande)).execute(this);
					break;
				case "get" :
					(new CommandeGET(ps, commande)).execute(this);
					break;
				case "ls" :
					(new CommandeLS(ps, commande)).execute(this);
					break;
				case "pwd" :
					(new CommandePWD(ps, commande)).execute(this);
					break;
				case "stor" :
					(new CommandeSTOR(ps, commande)).execute(this);
					break;
				case "mkdir" :
					(new CommandeMKDIR(ps, commande)).execute(this);
					break;
				case "rmdir" :
					(new CommandeRMDIR(ps, commande)).execute(this);
					break;
				case "bye" :
					ps.println("0 Déconnexion");
					break;
				default:
					ps.println("2 La commande n'existe pas");
					break;
			}

		} else {

			switch (commande.split(" ")[0]) {
				case "pass":
					if(userOk) {
						(new CommandePASS(ps, commande)).execute(this);
					} else {
						ps.println("2 Login n'a pas été saisie");
					}
					break;
				case "user":
					(new CommandeUSER(ps, commande)).execute(this);
					break;
				default:
					ps.println("2 La commande n'existe pas");
					break;
			}

		}
	}
}
