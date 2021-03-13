import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {
	
	public CommandeGET(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		System.out.println("Execute get");

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		ServerSocket servsock = null;
		Socket sock = null;
		try {
			servsock = new ServerSocket(3000);
			System.out.println(servsock);
			while (true) {
				System.out.println("Waiting...");
				try {
					System.out.println("dab1");
					System.out.println(servsock.accept());
					System.out.println("Accepted connection : " + sock);
					// send file
					String filePath = CommandExecutor.racinePath+"/"+CommandExecutor.currentPath+"/"+commandeArgs[0];
					File myFile = new File (filePath);
					byte [] mybytearray  = new byte [(int)myFile.length()];
					fis = new FileInputStream(myFile);
					bis = new BufferedInputStream(fis);
					System.out.println("dab2");
					ps.println("1 Nouvelle socket mise en place");
					bis.read(mybytearray,0,mybytearray.length);
					os = sock.getOutputStream();
					System.out.println("Sending " + filePath + "(" + mybytearray.length + " bytes)");
					os.write(mybytearray,0,mybytearray.length);
					os.flush();
					System.out.println("Done.");
				}
				finally {
					System.out.println("dab");
					if (bis != null) bis.close();
					if (os != null) os.close();
					if (sock!=null) sock.close();
				}
			}
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
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
