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

		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;
		ServerSocket servsock = null;

		String filePath = ce.racinePath+ce.currentPath+"/"+commandeArgs[0];

		try {
			File file = new File(filePath);
			if (file.exists()) {
				ps.println("2 Le fichier existe deja");
			} else {
				file.createNewFile();
				ps.println("1 Fichier créé");

			}

			servsock = new ServerSocket(ce.port + 2);
			ps.println("1 Ecoute en place");

			servsock.setSoTimeout(1000);

			sock = servsock.accept();


			byte[] mybytearray = new byte[6022386];

			InputStream is = null;
			is = sock.getInputStream();

			fos = new FileOutputStream(filePath);

			bos = new BufferedOutputStream(fos);
			bytesRead = 0;
			ps.println("1 Lecture prêt");
			bytesRead = is.read(mybytearray, 0, mybytearray.length);

			current = bytesRead;

			bos.write(mybytearray, 0, current);

			bos.flush();
			
		}catch (Exception e){
			System.out.println(e);
			ps.println("2 "+e);
		}finally {
			try {
				if (fos != null) fos.close();
				if (bos != null) bos.close();
				if (sock != null) sock.close();
				servsock.close();
			} catch (IOException e) {
				System.out.println(e);
				ps.println("2 "+e);
				return;
			}
			System.out.println("dab");
			ps.println("0 Fichier uploadé");
		}

	}

}
