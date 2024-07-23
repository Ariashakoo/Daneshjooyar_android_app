import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.file.Files;

public class Server {
    private ServerSocket server;

    public Server(ServerSocket server) {
        this.server = server;
    }

    public void startServer() {
        try {
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("A new student has connected");
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeServer();
        }
    }

    public void closeServer() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(12345);
        Server server = new Server(serverSocket);
        server.startServer();
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private static final String SIGNUP_FILE_PATH = "C:/users/js/desktop/Project11/signup_data.txt";
        private static final String GRADE_FILE_PATH = "C:/users/js/desktop/Project11/grade.txt";
        private static final String ASSIGN_FILE_PATH = "C:/users/js/desktop/Project11/assign.txt";
        private static final String COURSE_FILE_PATH = "C:/users/js/desktop/Project11/course.txt";
        private static final String Birthday_FILE_PATH = "C:/users/js/desktop/Project11/Birthday.txt";
        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("CONNECTED TO THE SERVER!");
        }

        @Override
        public void run() {
            try {
                String command;
                while ((command = in.readLine()) != null) {
                    System.out.println("COMMAND RECEIVED: " + command);
                    String[] commandParts = command.split("~");

                    switch (commandParts[0]) {
                        case "sara":
                            handleSaraCommand();
                            break;
                        case "sign":
                            handleSignCommand(commandParts);
                            break;
                        case "login":
                            handleLoginCommand(commandParts);
                            break;
                        case "user":
                            handleUserCommand();
                            break;
                        case "change_password":
                            handleChangePasswordCommand(commandParts);
                            break;
                        case "tamrin":
                            handleTamrinCommand();
                            break;
                        case "course":
                            handleCourseCommand();
                            break;
                        default:
                            System.out.println("Unknown command: " + command);
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleSaraCommand() {
            try {
                File file = new File(GRADE_FILE_PATH);
                if (file.exists()) {
                    List<String> grades = Files.readAllLines(file.toPath());
                    List<Integer> gradeValues = new ArrayList<>();
                    for (String grade : grades) {
                        gradeValues.add(Integer.parseInt(grade.trim()));
                    }
                    int maxGrade = Collections.max(gradeValues);
                    int minGrade = Collections.min(gradeValues);
                    out.println(maxGrade + "," + minGrade);
                } else {
                    out.println("Grade file not found");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the grade file: " + e.getMessage());
                out.println("An error occurred");
            }
        }
        private void handleBirthdayCommand() {
            try {
                File file = new File(Birthday_FILE_PATH);
                if (file.exists()) {
                    List<String> birthdays = Files.readAllLines(file.toPath());
                    boolean hasBirthday = false;
                    String today = new java.text.SimpleDateFormat("MM/dd").format(new java.util.Date());

                    for (String birthday : birthdays) {
                        if (birthday.contains(today)) {
                            hasBirthday = true;
                            break;
                        }
                    }

                    if (hasBirthday) {
                        out.println("We have a birthday!");
                    } else {
                        out.println("No birthdays today.");
                    }
                } else {
                    out.println("Birthday file not found.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the birthday file: " + e.getMessage());
                out.println("An error occurred");
            }
        }
        private void handleCourseCommand() {
            try {
                File file = new File(COURSE_FILE_PATH);
                if (file.exists()) {
                    List<String> courseData = Files.readAllLines(file.toPath());
                    for (String line : courseData) {
                        out.println(line);
                    }
                    out.println("END_OF_COURSES"); // Ensure a proper end to the message
                } else {
                    out.println("Course file not found");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the course file: " + e.getMessage());
                out.println("An error occurred");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleSignCommand(String[] parts) throws IOException {
            if (parts.length == 6) {
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

        private void handleTamrinCommand() {
            try {
                File file = new File(ASSIGN_FILE_PATH);
                if (file.exists()) {
                    System.out.println("Assignment file found");
                    List<String> assignData = Files.readAllLines(file.toPath());
                    for (String line : assignData) {
                        System.out.println("Sending: " + line); // Debug statement
                        out.println(line);
                    }
                    out.println("END_ASSIGNMENTS");
                    System.out.println("All assignments sent");
                } else {
                    System.out.println("Assignment file not found");
                    out.println("Assignment file not found");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the assignment file: " + e.getMessage());
                out.println("An error occurred");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                out.println("Invalid request");
            }
        }

        private boolean checkCredentials(String firstName, String lastName, String id, String email, String password) {
            try (BufferedReader br = new BufferedReader(new FileReader(SIGNUP_FILE_PATH))) {
                String line;
                while ((line = br.readLine()) != null) {
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
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
            return false;
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
                    while ((currentLine = reader.readLine()) != null) {
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
                    } else {
                        out.println("Password changed successfully");
                    }

                } catch (IOException e) {
                    System.out.println("An error occurred while changing the password: " + e.getMessage());
                    out.println("Error changing password");
                }
            } else {
                System.out.println("Invalid number of parts for change_password command");
            }
        }
    }
}
