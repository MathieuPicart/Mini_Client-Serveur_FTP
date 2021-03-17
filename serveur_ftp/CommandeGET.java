import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {
	
	public CommandeGET(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		ServerSocket servsock = null;
		Socket sock = null;
		String err = "";

		try {
			servsock = new ServerSocket(ce.port+1);
			ps.println("1 Nouvelle socket mise en place");
			//1s pour ce connecter sinon connexion rejeté
			servsock.setSoTimeout(1000);

			//En attente du client
			sock = servsock.accept();
			System.out.println("Accepted connection : " + sock);

			// Vérification de l'éxistance du fichier
			String filePath = ce.racinePath+ce.currentPath+"/"+commandeArgs[0];
			File myFile = new File (filePath);
			if(!myFile.exists()){
				throw new Exception("Ce fichier n'existe pas");
			}

			//paramétrage de l'envoie
			byte [] mybytearray  = new byte [(int)myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			os = sock.getOutputStream();
			os.write(mybytearray,0,mybytearray.length);
			os.flush();
			ps.println("0 Fichier téléchargé");

		} catch (Exception e) {
			ps.println("2 "+e);
		} finally {
			try {
				if (bis != null) bis.close();
				if (os != null) os.close();
				if (sock != null) sock.close();
				if (servsock != null) servsock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
