import java.io.*;
import java.net.ServerSocket;
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

            bos.write(mybytearray, 0 , current);
            bos.flush();
        } catch (UnknownHostException e) {
            System.out.println("Connexion avec le serveur à échoué");
            return;
        } catch (IOException e) {
            System.out.println(e);
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

    public static void uploadFile(BufferedReader reader, String fileName){
        String filePath = "uploads/"+fileName;
        File myFile = new File (filePath);
        if(!myFile.exists()) {
            System.out.println("Le fichier " + filePath + " n'existe pas");
            return;
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket sock = null;
        String msg = null;
        try {
            msg = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(msg==null){
            try {
                msg = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(msg);

        try {
            sock = new Socket("localhost", 2123);
        } catch (IOException e) {
            System.out.println("Problème au moment de la connexion au serveur");
        }

        while(!msg.equals("1 Lecture prêt")){
            try {
                msg = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(msg!=null) {
                System.out.println(msg);
            }
        }

        byte [] mybytearray  = new byte [(int)myFile.length()];
        try {
            fis = new FileInputStream(myFile);
        } catch (FileNotFoundException e) {
            System.out.println("Problème de gestion de fichier");
            return;
        }

        bis = new BufferedInputStream(fis);

        try {
            bis.read(mybytearray,0,mybytearray.length);
        } catch (IOException e) {
            System.out.println("Problème de lecture du fichier");
        }

        try {
            os = sock.getOutputStream();
        } catch (IOException e) {
            System.out.println("Problème au niveau de la socket");
            return;
        }

        try {
            os.write(mybytearray,0,mybytearray.length);
        } catch (IOException e) {
            System.out.println("Problème d'écriture au moment de l'envoie");
            return;
        }

        try {
            os.flush();
        } catch (IOException e) {
            System.out.println("Problème au moment de l'envoie");
            return;
        }

        try {
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (sock != null) sock.close();
        } catch (IOException e) {
            System.out.println("Problème au moment de la fermeture");
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
                        reciveFile(cmd.split(" ")[1]);
                        break;
                    case "stor":
                        ps.println(cmd);
                        uploadFile(reader, cmd.split(" ")[1]);
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
            System.out.println("euh");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Euh Houston on a un problème le serveur n'est pas en ligne");
        }
    }
}
