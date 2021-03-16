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
		int port = 0;
		BufferedReader filePort = null;
		try {
			filePort = new BufferedReader(new FileReader("../client_ftp/port.txt"));
			String portString = filePort.readLine();
			port = Integer.parseInt(portString);
			File fileD = new File("port.txt");
			fileD.delete();
			fileD.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileD.getAbsoluteFile()));
			bw.write((2000)+"");
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (true) {
			ServerSocket serveurFTP = new ServerSocket(port);
			Socket socket = serveurFTP.accept();
			AccueilClient ac = new AccueilClient(socket, serveurFTP, port);
			Thread th = new Thread(ac);
			th.start();
			port += 3;
		}
	}
}
