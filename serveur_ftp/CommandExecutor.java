import java.io.PrintStream;

public class CommandExecutor {
	
	public static boolean userOk = false ;
	public static String user = null;
	public static String currentPath = null;
	public static boolean pwOk = false ;
	
	public static void executeCommande(PrintStream ps, String commande) {
		if(userOk && pwOk) {
			switch (commande.split(" ")[0]) {
				case "cd" :
					(new CommandeCD(ps, commande)).execute();
					break;
				case "get" :
					(new CommandeGET(ps, commande)).execute();
					break;
				case "ls" :
					(new CommandeLS(ps, commande)).execute();
					break;
				case "pwd" :
					(new CommandePWD(ps, commande)).execute();
					break;
				case "stor" :
					(new CommandeSTOR(ps, commande)).execute();
					break;
				default:
					ps.println("2 La commande n'existe pas");
					break;
			}

		} else {

			switch (commande.split(" ")[0]) {
				case "pass":
					if(userOk) {
						(new CommandePASS(ps, commande)).execute();
					} else {
						ps.println("2 Login n'a pas été saisie");
					}
					break;
				case "user":
					(new CommandeUSER(ps, commande)).execute();
					break;
				default:
					ps.println("2 La commande n'existe pas");
					break;
			}

		}
	}

}
