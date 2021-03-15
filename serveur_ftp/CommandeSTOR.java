import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommandeSTOR extends Commande {
	
	public CommandeSTOR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {

		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;

		ServerSocket servsock = null;
		try {
			servsock = new ServerSocket(ce.port+2);
			ps.println("1 Ecoute en place");
		} catch (IOException e) {
			e.printStackTrace();
			ps.println("2 Probleme d'ecoute sur ce port");
			return;
		}

		try {
			sock = servsock.accept();
		} catch (IOException e) {
			System.out.println("Connexion n'a pas pu etre accepter");
		}


		byte [] mybytearray  = new byte [6022386];

		InputStream is = null;
		try {
			is = sock.getInputStream();
		} catch (IOException e) {
			System.out.println("Socket inutilisable");
		}

		String filePath = ce.racinePath+ce.currentPath+"/"+commandeArgs[0];

		File file = new File(filePath);
		if(file.exists()){
			ps.println("2 Le fichier existe déja");
			return;
		}else{
			try {
				file.createNewFile();
				ps.println("1 Fichier créé");
			} catch (IOException e) {
				ps.println("2 Le fichier n'a pas pu etre créé");
				return;
			}
		}

		try {
			fos = new FileOutputStream(filePath);
			ps.println("1 Fichier prêt");
		} catch (FileNotFoundException e) {
			ps.println("2 Problème d'ouverture de fichier");
			return;
		}

		bos = new BufferedOutputStream(fos);
		bytesRead = 0;
		try {
			ps.println("1 Lecture prêt");
			bytesRead = is.read(mybytearray,0,mybytearray.length);
		} catch (IOException e) {
			ps.println("2 Problème de mise ne place de lecture");
			return;
		}

		current = bytesRead;

		do {
			try {
				bytesRead =
						is.read(mybytearray, current, (mybytearray.length-current));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(bytesRead >= 0) current += bytesRead;
		} while(bytesRead > -1);

		try {
			bos.write(mybytearray, 0 , current);
			ps.println("1 Ecriture reussie");
		} catch (IOException e) {
			ps.println("2 Probleme au moment de l'ecriture");
			return;
		}

		try {
			bos.flush();
			ps.println("0 Upload réussi");
		} catch (IOException e) {
			e.printStackTrace();
			ps.println("2 Flush a échoué");
		}

		try {
			if (fos != null) fos.close();
			if (bos != null) bos.close();
			if (sock != null) sock.close();
			servsock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
