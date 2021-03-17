/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("Le Serveur FTP");
		int port = 2000;
		BufferedReader filePort = null;
		try {
			//initialisation valeur port coté client
			File fileD = new File("../client_ftp/port.txt");
			fileD.delete();
			fileD.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileD.getAbsoluteFile()));
			bw.write((port)+"");
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		//Boucle de reception des nouveaux clients
		while (true) {
			ServerSocket serveurFTP = new ServerSocket(port);
			Socket socket = serveurFTP.accept();

			//A chaque nouveau clients un thread lui est attribué

			AccueilClient ac = new AccueilClient(socket, serveurFTP, port);
			Thread th = new Thread(ac);
			th.start();

			//incrémentation du futur port (3 = port gestion des commande + port reception des fichiers + port envoie des fichier)
			port += 3;
		}
	}
}
