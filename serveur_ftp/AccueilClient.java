import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AccueilClient implements Runnable {
    public Socket socket;
    public ServerSocket serveurFTP;
    public int port;

    public AccueilClient(Socket socket, ServerSocket serveurFTP, int port) {
        this.socket = socket;
        this.serveurFTP = serveurFTP;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream ps = new PrintStream(socket.getOutputStream());
            CommandExecutor ce = new CommandExecutor(port);

            ps.println("1 Bienvenue ! ");
            ps.println("1 Serveur FTP Personnel.");
            ps.println("0 Authentification : ");

            String commande = "";

            // Attente de reception de commandes et leur execution

            while((commande=br.readLine())!=null && !commande.equals("bye")) {
                System.out.println(">> "+commande);
                ce.executeCommande(ps, commande);
            }
            System.out.println("Déconnexion");
            serveurFTP.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
