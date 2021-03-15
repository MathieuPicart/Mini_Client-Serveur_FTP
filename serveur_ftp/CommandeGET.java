import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {
	
	public CommandeGET(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute(CommandExecutor ce) {
		System.out.println("Execute get");

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		ServerSocket servsock = null;
		Socket sock = null;
		String err = "";
		try {
			servsock = new ServerSocket(ce.port+1);
			System.out.println("Waiting...");
			try {
				ps.println("1 Nouvelle socket mise en place");
				servsock.setSoTimeout(1000);
				sock = servsock.accept();
				System.out.println("Accepted connection : " + sock);
				// send file
				String filePath = ce.racinePath+ce.currentPath+"/"+commandeArgs[0];
				File myFile = new File (filePath);
				System.out.println(filePath);
				if(!myFile.exists()){
					err = "2 Ce fichier n'existe pas";
				}

				byte [] mybytearray  = new byte [(int)myFile.length()];
				fis = new FileInputStream(myFile);
				bis = new BufferedInputStream(fis);
				bis.read(mybytearray,0,mybytearray.length);
				os = sock.getOutputStream();
				System.out.println("Sending " + filePath + "(" + mybytearray.length + " bytes)");
				os.write(mybytearray,0,mybytearray.length);
				os.flush();
				System.out.println("Done.");
				ps.println("0 Fichier téléchargé");
			}
			finally {
				if (bis != null) bis.close();
				if (os != null) os.close();
				if (sock != null) sock.close();
			}
		} catch (IOException e) {
			System.out.println(err);
			ps.println(err);
		} finally {
			if (servsock != null) {
				try {
					servsock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
