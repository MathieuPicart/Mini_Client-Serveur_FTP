import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
;import java.io.FileReader;

public class Main {

    public static int port;

    public static void downloadFile(BufferedReader reader, String fileName) {

        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            //connexion à la socket de réception
            sock = new Socket("localhost", port+1);

            //paramétrage des variables
            byte [] mybytearray  = new byte [6022386];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream("downloads/"+fileName);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            //Lecture de l'envoie
            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            String msg = reader.readLine();

            //Réception et gestion des messages d'éxécution
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

            //Écriture dans le fichier
            bos.write(mybytearray, 0 , current);
            bos.flush();
        } catch (UnknownHostException e) {
            System.out.println("Connexion avec le serveur à échoué");
            (new File("downloads/"+fileName)).delete();
        } catch (IOException e) {
            (new File("downloads/"+fileName)).delete();
            System.out.println("Une erreur est survenue : "+e.getMessage());
        } finally {
            try {
                if (fos != null) fos.close();
                if (bos != null) bos.close();
                if (sock != null) sock.close();
            } catch (IOException e) {
                e.printStackTrace();
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

            //Réception des message d'initialisation du serveur
            msg = reader.readLine();
            if (msg.startsWith("2")) {
                throw new Exception(msg);
            }
            System.out.println(msg);

            msg = reader.readLine();
            if (msg.startsWith("2")) {
                throw new Exception(msg);
            }
            System.out.println(msg);

            //Connexion à la socket d'envoie
            sock = new Socket("localhost", port+2);

            //En attente du serveur
            while (!msg.equals("1 Lecture prêt")) {
                if(msg.startsWith("2")){
                    throw new Exception(msg);
                }
                msg = reader.readLine();
                System.out.println(msg);
            }

            //Paramétrage des variables
            byte[] mybytearray = new byte[(int) myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);

            //Lecture des données du fichier
            bis.read(mybytearray, 0, mybytearray.length);
            os = sock.getOutputStream();

            //Écriture dans le fichier
            os.write(mybytearray, 0, mybytearray.length);
            os.flush();
            System.out.println(reader.readLine());
        }catch (Exception e){
            System.out.println(e.getMessage());
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
        BufferedReader filePort = null;

        //Initialisation du port + incrémentation pour prochain client
        try {
            filePort = new BufferedReader(new FileReader("port.txt"));
            String portString = filePort.readLine();
            port = Integer.parseInt(portString);
            File fileD = new File("port.txt");
            fileD.delete();
            fileD.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileD.getAbsoluteFile()));
            bw.write((port+3)+"");
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("Terminal client port : "+port);

        //Connexion au serveur
        try (Socket socket = new Socket(hostname, port)){

            System.out.println("Commandes : \n " +
                    "- user (pseudo) : pour envoyer le nom du login\n " +
                    "- pass (mot de passe) : pour envoyer le mot de passe PWD: pour afficher le chemin absolu du dossier courant\n " +
                    "- ls : afficher la liste des dossiers et des fichiers du répertoire courant du serveur\n " +
                    "- cd (chemin destination) : pour changer de répertoire courant du côté du serveur \n " +
                    "- mkdir (nom du dossier) : pour créer un répertoire du côté du serveur \n " +
                    "- rmdir (nom du dossier) : pour supprimer un répertoire du côté du serveur \n " +
                    "- stor (nom du fichier) : pour envoyer un fichier vers le dossier courant serveur\n " +
                    "- get (nom du fichier) : pour télécharger un fichier du serveur vers le client\n ");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            Scanner scanner = new Scanner( System.in );

            String cmd = "yo dab";

            PrintStream ps = new PrintStream(socket.getOutputStream());
            String msg = "";

            //Boucle d'interaction client serveur fin a commande "bye"
            while(!cmd.equals("bye")) {

                //Lecture des messages
                if(!cmd.split(" ")[0].equals("get") && !cmd.split(" ")[0].equals("stor")) {
                    msg = reader.readLine();

                    while (msg != null && msg.startsWith("1")) {
                        System.out.println(msg);
                        msg = reader.readLine();
                    }

                    if (msg != null) System.out.println(msg);
                }

                System.out.println("Saisir votre cmd : ");
                //En attente de saisie du client
                cmd = scanner.nextLine();

                //traitement des commande et des commandes spécifiques
                switch (cmd.split(" ")[0]) {
                    case "get":
                        //vérification que le fichier n'éxiste pas
                        File file = new File("downloads/"+cmd.split(" ")[1]);
                        if(file.exists()) {
                            System.out.println("Ce fichier existe déja dans votre dossier /downloads");
                            break;
                        }

                        //crétion du fichier
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            System.out.println("Problème au moment de la création du fichier");
                            break;
                        }

                        ps.println(cmd);

                        //Reception du message "prêt" du serveur
                        System.out.println(reader.readLine());

                        //Lancement de la méthode de reception
                        downloadFile(reader, cmd.split(" ")[1]);
                        break;
                    case "stor":
                        //Vérification de l'existance du fichier
                        if(!(new File("uploads/"+cmd.split(" ")[1])).exists()) {
                            System.out.println("Le fichier uploads/"+cmd.split(" ")[1]+" n'existe pas");
                            break;
                        }
                        ps.println(cmd);
                        //Lancement de la méthode d'envoie
                        uploadFile(reader, cmd.split(" ")[1]);
                        break;
                    default:
                        ps.println(cmd);
                        break;
                }
            }

            //gestion de la commande de déconnexion
            if(cmd.equals("bye")) {
                msg = reader.readLine();
                ps.println(cmd);
            }

            System.out.println("Déconnexion");

        //gestion des erreurs de connexion
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("Euh Houston on a un problème le serveur n'est pas en ligne");
        }
    }
}
