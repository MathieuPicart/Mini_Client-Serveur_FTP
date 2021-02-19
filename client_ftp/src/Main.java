import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 2121;

        try (Socket socket = new Socket(hostname, port)) {

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String bienvenue = reader.readLine();

            System.out.println(bienvenue);

            String msg = reader.readLine();

            System.out.println(msg);

            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println("USER personne");

        } catch (UnknownHostException e) {
            System.out.println(e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
