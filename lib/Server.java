import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.file.Files;

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
        private PrintWriter out;
        private static final String SIGNUP_FILE_PATH = "C:/users/js/desktop/Project11/signup_data.txt";
        private static final String GRADE_FILE_PATH = "C:/users/js/desktop/Project11/grade.txt";  // Adjust the file path as needed

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("CONNECTED TO THE SERVER!");
        }

        @Override
        public void run() {
            try {
                String command = in.readLine();
                System.out.println("COMMAND RECEIVED: " + command);

                // Split the command using the tilde delimiter
                String[] parts = command.split("~");
                String commandType = parts[0];

                switch (commandType) {
                    case "sign":
                        handleSignCommand(parts);
                        break;
                    case "login":
                        handleLoginCommand(parts);
                        break;
                    case "user":
                        handleUserCommand();
                        break;
                    case "change_password":
                        handleChangePasswordCommand(parts);
                        break;
                    // Add more cases here for other commands
                    default:
                        System.out.println("Unknown command: " + commandType);
                        break;
                }

                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleSignCommand(String[] parts) throws IOException {
            if (parts.length == 6) { // 6 because first element is "sign"
                String firstName = parts[1];
                String lastName = parts[2];
                String id = parts[3];
                String email = parts[4];
                String password = parts[5];

                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("ID: " + id);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);

                // Write received data to file with append mode
                try (FileWriter fw = new FileWriter(SIGNUP_FILE_PATH, true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter out = new PrintWriter(bw)) {
                    out.println(firstName + "~" + lastName + "~" + id + "~" + email + "~" + password);
                } catch (IOException e) {
                    System.out.println("An error occurred while writing to file: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid number of parts received");
            }
        }

        private void handleLoginCommand(String[] parts) throws IOException {
            if (parts.length == 6) {
                String firstName = parts[1];
                String lastName = parts[2];
                String id = parts[3];
                String email = parts[4];
                String password = parts[5];

                boolean loginSuccess = checkCredentials(firstName, lastName, id, email, password);
                if (loginSuccess) {
                    out.println("success");
                } else {
                    out.println("failure");
                }
            } else {
                System.out.println("Invalid number of parts received");
            }
        }

        private void handleUserCommand() {
            try {
                File file = new File(GRADE_FILE_PATH);
                if (file.exists()) {
                    List<String> grades = Files.readAllLines(file.toPath());
                    List<Integer> gradeValues = new ArrayList<>();
                    for (String grade : grades) {
                        gradeValues.add(Integer.parseInt(grade.trim()));
                    }
                    int sum = 0;
                    for (int grade : gradeValues) {
                        sum += grade;
                    }
                    double averageGrade = (double) sum / gradeValues.size();
                    out.println("Average grade: " + averageGrade);
                } else {
                    out.println("Grade file not found");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the grade file: " + e.getMessage());
                out.println("An error occurred");
            }
        }

        private void handleChangePasswordCommand(String[] parts) {
            if (parts.length == 3) {
                String username = parts[1];
                String newPassword = parts[2];

                try {
                    File inputFile = new File(SIGNUP_FILE_PATH);
                    File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String currentLine;
                    while ((currentLine = reader.readLine())!= null) {
                        String[] userParts = currentLine.split("~");
                        if (userParts.length == 5 && userParts[2].equals(username)) {
                            userParts[4] = newPassword;
                            writer.write(String.join("~", userParts) + System.lineSeparator());
                        } else {
                            writer.write(currentLine + System.lineSeparator());
                        }
                    }
                    writer.close();
                    reader.close();

                    if (!inputFile.delete()) {
                        System.out.println("Could not delete original file");
                        return;
                    }

                    if (!tempFile.renameTo(inputFile)) {
                        System.out.println("Could not rename temporary file");
                    }

                    out.println("Password changed successfully");
                } catch (IOException e) {
                    System.out.println("An error occurred while changing the password: " + e.getMessage());
                    out.println("Error changing password");
                }
            } else {
                System.out.println("Invalid number of parts for change_password command");
            }
        }

        private boolean checkCredentials(String firstName, String lastName, String id, String email, String password) {
            try (BufferedReader br = new BufferedReader(new FileReader(SIGNUP_FILE_PATH))) {
                String line;
                while ((line = br.readLine())!= null) {
                    String[] parts = line.split("~");
                    if (parts.length == 5 &&
                            parts[0].equals(firstName) &&
                            parts[1].equals(lastName) &&
                            parts[2].equals(id) &&
                            parts[3].equals(email) &&
                            parts[4].equals(password)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file: " + e.getMessage());
            }
            return false;
        }
    }
}