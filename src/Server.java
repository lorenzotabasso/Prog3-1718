import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public String status;

    public static void main(String[] args) {

        // INITIALIZING SOCKET
        try (ServerSocket listener = new ServerSocket(9000)) {
            while (true) {
                try (Socket socket = listener.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Connected to the server");

                    System.out.println("Connected to the client");
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); }
    }

}
