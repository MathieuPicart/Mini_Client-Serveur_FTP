import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void reciveFile(String fileName) {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket("localhost", 2122);
            System.out.println("Connecting...");

            // receive file
            byte [] mybytearray  = new byte [6022386];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream("/downlods/"+fileName);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + fileName
                    + " downloaded (" + current + " bytes read)");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sock != null) {
                try {
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 2121;

        try (Socket socket = new Socket(hostname, port)){

            System.out.println("Commandes : \n " +
                    "- USER: pour envoyer le nom du login\n " +
                    "- PASS : pour envoyer le mot de passe PWD: pour afficher le chemin absolu du dossier courant\n " +
                    "- LS: afficher la liste des dossiers et des fichiers du répertoire courant du serveur\n " +
                    "- CD: pour changer de répertoire courant du côté du serveur \n " +
                    "- STOR : pour envoyer un fichier vers le dossier courant serveur\n " +
                    "- GET: pour télécharger un fichier du serveur vers le client\n ");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            Scanner scanner = new Scanner( System.in );

            String cmd = " ";

            PrintStream ps = new PrintStream(socket.getOutputStream());

            while(!cmd.equals("bye")) {
                String msg = reader.readLine();
                while(msg.startsWith("1")) {
                    System.out.println(msg);
                    msg = reader.readLine();
                }

                System.out.println(msg);

                System.out.println("Saisir votre cmd : ");
                cmd = scanner.nextLine();

                switch (cmd.split(" ")[0]) {
                    case "get":
                        reciveFile(cmd.split(" ")[1]);
                        break;
                    default:
                        ps.println(cmd);
                        break;
                }
            }

            if(cmd.equals("bye")) {
                String msg = reader.readLine();
                ps.println(cmd);
            }

            System.out.println("Déconnexion");

        } catch (UnknownHostException e) {
            System.out.println(e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
