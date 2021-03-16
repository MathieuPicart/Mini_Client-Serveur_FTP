import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

    public static void reciveFile(BufferedReader reader, String fileName) {

        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket("localhost", 2001);
            // receive file
            byte [] mybytearray  = new byte [6022386];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream("downloads/"+fileName);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            String msg = reader.readLine();

            while(msg!=null){
                if (msg.split(" ")[0].equals("2")) {
                    throw new IOException(msg);
                }
                System.out.println(msg);

                if(msg.startsWith("0")){
                    break;
                }

                msg = reader.readLine();
            }

            bos.write(mybytearray, 0 , current);
            bos.flush();
        } catch (UnknownHostException e) {
            System.out.println("Connexion avec le serveur à échoué");
            (new File("downloads/"+fileName)).delete();
            return;
        } catch (IOException e) {
            (new File("downloads/"+fileName)).delete();
            System.out.println("Une erreur est survenue : "+e);
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

    public static void uploadFile(BufferedReader reader, String fileName){
        String filePath = "uploads/"+fileName;
        File myFile = new File (filePath);

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket sock = null;
        String msg = null;

        try {
            msg = reader.readLine();
            System.out.println(msg);
            if (msg.startsWith("2")) {
                throw new Exception(msg);
            }

            msg = reader.readLine();
            System.out.println(msg);
            if (msg.startsWith("2")) {
                throw new Exception(msg);
            }

            sock = new Socket("localhost", 2002);

            while (!msg.equals("1 Lecture prêt")) {
                if(msg.startsWith("2")){
                    throw new Exception(msg);
                }
                msg = reader.readLine();
                System.out.println(msg);
            }

            byte[] mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray, 0, mybytearray.length);
            os = sock.getOutputStream();
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            System.out.println(reader.readLine());
        }catch (Exception e){
            System.out.println("Probleme au moment de l'execution : "+e);
        } finally {
            try {
                if (bis != null) bis.close();
                if (os != null) os.close();
                if (sock != null) sock.close();
            }catch (IOException e){
                System.out.println("Probleme au moment de la fermeture des sockets");
            }
        }

    }

    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 2000;

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

            String cmd = "yo dab";

            PrintStream ps = new PrintStream(socket.getOutputStream());
            String msg = "";

            while(!cmd.equals("bye")) {
                if(!cmd.split(" ")[0].equals("get") && !cmd.split(" ")[0].equals("stor")) {
                    msg = reader.readLine();

                    while (msg != null && msg.startsWith("1")) {
                        System.out.println(msg);
                        msg = reader.readLine();
                    }

                    if (msg != null) System.out.println(msg);
                }

                System.out.println("Saisir votre cmd : ");
                cmd = scanner.nextLine();

                switch (cmd.split(" ")[0]) {
                    case "get":
                        File file = new File("downloads/"+cmd.split(" ")[1]);
                        if(file.exists()) {
                            System.out.println("Ce fichier existe déja dans votre dossier /downloads");
                            break;
                        }

                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            System.out.println("Problème au moment de la création du fichier");
                            break;
                        }

                        ps.println(cmd);
                        System.out.println(reader.readLine());
                        reciveFile(reader, cmd.split(" ")[1]);
                        break;
                    case "stor":
                        if(!(new File("uploads/"+cmd.split(" ")[1])).exists()) {
                            System.out.println("Le fichier uploads/"+cmd.split(" ")[1]+" n'existe pas");
                            break;
                        }
                        ps.println(cmd);
                        uploadFile(reader, cmd.split(" ")[1]);
                        break;
                    default:
                        ps.println(cmd);
                        break;
                }
            }

            if(cmd.equals("bye")) {
                msg = reader.readLine();
                ps.println(cmd);
            }

            System.out.println("Déconnexion");

        } catch (UnknownHostException e) {
            System.out.println("euh");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Euh Houston on a un problème le serveur n'est pas en ligne");
        }
    }
}
