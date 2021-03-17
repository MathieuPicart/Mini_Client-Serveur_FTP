import java.io.PrintStream;

public class CommandExecutor {

	public boolean userOk;
	public String login;
	public String racinePath;
	public String currentPath;
	public boolean pwOk;
	public int port;

	public CommandExecutor(int port) {
		//On initialise les variables
		this.userOk = false;
		this.login = null;
		this.racinePath = null;
		this.currentPath = null;
		this.pwOk = false;
		this.port = port;
	}
	
	public void executeCommande(PrintStream ps, String commande) throws Exception{
		//On vérifie si l'utilisateur est bien connecté
		if(userOk && pwOk) {
			switch (commande.split(" ")[0]) {
				//Si la commande est définie (et autorisée) on l'exécute, sinon on affiche une erreur
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
				case "rm" :
					(new CommandeRM(ps, commande)).execute(this);
					break;
				case "bye" :
					ps.println("0 Déconnexion");
					break;
				default:
					ps.println("2 La commande n'existe pas");
					break;
			}
		} else {
			//Si il n'est pas connecté on vérifie si la commande est définie (et autorisée) on l'exécute, sinon on affiche une erreur
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
