import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class CommandeSTOR extends Commande {
	
	public CommandeSTOR(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {

		if(commandeArgs.length!=1){
			ps.println("2 Nombre d'arguments incorrect");
			return;
		}

		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;
		ServerSocket servsock = null;

		String filePath = ce.racinePath+ce.currentPath+"/"+commandeArgs[0];

		try {
			//Vérification que le fichier n'existe pas et création
			File file = new File(filePath);
			if (file.exists()) {
				throw new Exception("Le fichier existe deja");
			} else {
				file.createNewFile();
				ps.println("1 Fichier créé");
			}

			servsock = new ServerSocket(ce.port + 2);
			ps.println("1 Ecoute en place");

			servsock.setSoTimeout(1000);
			//Attente de connexion du client
			sock = servsock.accept();

			//Parmétrage des éléments
			byte[] mybytearray = new byte[6022386];

			InputStream is = null;
			is = sock.getInputStream();

			fos = new FileOutputStream(filePath);

			bos = new BufferedOutputStream(fos);
			bytesRead = 0;

			//informe le client que le serveur est prêt
			ps.println("1 Lecture prêt");
			bytesRead = is.read(mybytearray, 0, mybytearray.length);

			current = bytesRead;
			//Ecriture dans le fichier
			bos.write(mybytearray, 0, current);

			bos.flush();
			ps.println("0 Fichier uploadé");
		}catch (Exception e){
			ps.println("2 "+e);
		}finally {
			try {
				if (fos != null) fos.close();
				if (bos != null) bos.close();
				if (sock != null) sock.close();
				if (servsock != null) servsock.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		return;
	}

}
