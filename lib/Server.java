import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("WELCOME TO SERVER!");
        ServerSocket ss = new ServerSocket(12345);

        while (true) {
            System.out.println("WAITING FOR CLIENT...");
            Socket socket = ss.accept();
            new Thread(new ClientHandler(socket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("CONNECTED TO THE SERVER!");
        }

        @Override
        public void run() {
            try {
                String command = in.readLine();
                System.out.println("COMMAND RECEIVED: " + command);

                // Split the command using the tilde delimiter
                String[] parts = command.split("~");
                if (parts.length == 5) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String id = parts[2];
                    String email = parts[3];
                    String password = parts[4];
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);
                    System.out.println("ID: " + id);
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + password);
                } else {
                    System.out.println("Invalid number of parts received");
                }

                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}