import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 2121;

        try (Socket socket = new Socket(hostname, port)){

            System.out.println("Commandes : \n " +
                    "- CD: pour changer de répertoire courant du côté du serveur \n " +
                    "- GET: pour télécharger un fichier du serveur vers le client\n " +
                    "- LS: afficher la liste des dossiers et des fichiers du répertoire courant du serveur\n " +
                    "- PASS : pour envoyer le mot de passe PWD: pour afficher le chemin absolu du dossier courant\n " +
                    "- STOR : pour envoyer un fichier vers le dossier courant serveur\n " +
                    "- USER: pour envoyer le nom du login\n ");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            Scanner scanner = new Scanner( System.in );

            String cmd = " ";

            PrintStream ps = new PrintStream(socket.getOutputStream());

            while(!cmd.equals("DISC")) {
                String msg = reader.readLine();
                while(msg.startsWith("1")) {
                    System.out.println(msg);
                    msg = reader.readLine();
                }

                System.out.println(msg);

                System.out.println("Saisir votre cmd : ");
                cmd = scanner.nextLine();
                if(!cmd.equals("DISC")) {
                    ps.println(cmd);
                }
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
