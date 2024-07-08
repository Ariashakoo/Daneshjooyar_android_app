import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static String[][] passwords =new String[0][3];
    static List<Student> students = new ArrayList<Student>();
    static List<Course> courses = new ArrayList<Course>();
    static List<Teacher> teachers = new ArrayList<Teacher>();
    static List<Assignment> Assignments = new ArrayList<Assignment>();
    
    public static void main(String[] args) throws Exception {

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/UNIVERSITY", "root", "7277777772Baykal")) {
            System.out.println("Connected to the database!");

            String query = "SELECT * FROM passwords";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            int i = 0 ; 
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String pass = resultSet.getString("password");
                String id = resultSet.getString("id");
                passwords[i][0]=email;
                passwords[i][1]=pass;
                passwords[i][2]=id;
                i++;
            }
            System.out.println("passwords added successfully");


            query = "SELECT * FROM assignment";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String deadline = resultSet.getString("deadline");
                String availavle = resultSet.getString("available");
                Assignments.add(new Assignment(deadline, availavle, name));
            }
            System.out.println("assignments added successfully");


            query = "SELECT * FROM courses";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int units = resultSet.getInt("units");
                String dateofexam = resultSet.getString("date_of_exam");
                courses.add(new Course(name, units));
            }
            System.out.println("courses added successfully");


            query = "SELECT * FROM teachers";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("teacher_id");
                String fname = resultSet.getString("first_name");
                String lname = resultSet.getString("last_name");
                String teachercourses = resultSet.getString("courses");
                String projects = resultSet.getString("projects");

                Teacher T = new Teacher(fname, lname,Long.parseLong(id));
                String[] teachercoursessplit = teachercourses.split(" ");
                for(i=0;i<teachercoursessplit.length;i++){
                    for(int j = 0 ; j < courses.size() ; j++){
                        if(teachercoursessplit[i].equals(courses.get(j).getCourseName())){
                            T.addCourse(courses.get(j));
                            continue;
                        }
                    }
                }
                String[] projectssplit = projects.split(" ");
                for(i=0;i<projectssplit.length ;i++){
                    for(int j = 0 ; j < Assignments.size() ;j++){
                        if(projectssplit[i].equals(Assignments.get(j).getName())){
                            T.addProjects(Assignments.get(j));
                            continue;
                        }
                    }
                }
            }
            System.out.println("teachers added successfully");


            query = "SELECT * FROM students";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String fname = resultSet.getString("firs_name");
                String lname = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String id = resultSet.getString("stdent_id");
                String birthdate = resultSet.getString("birthdate");
                int terms = resultSet.getInt("terms");
                Student s = new Student(fname, lname,Long.parseLong( id), email);
                s.setBirthdate(birthdate);
                for(i=0;i<terms ; i++){
                    query = "SELECT * FROM students WHERE student_id="+String.valueOf(s.getSID())+", term_number="+String.valueOf(i);
                    statement = connection.prepareStatement(query);
                    resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        s.addTerms();
                        String teacher = resultSet.getString("teacher");
                        String course = resultSet.getString("course");
                        double score = resultSet.getDouble("score");
                        Course c=null ;
                        Teacher t=null;
                        for(int j = 0 ; j < courses.size() ; j++){
                            if(courses.get(j).getCourseName().equals(course)){
                                c = courses.get(j);
                                break;
                            }
                        }
                        for(int j = 0 ; j < teachers.size() ; j++){
                            if(teachers.get(j).getLast_name().equals(teacher)){
                                t = teachers.get(j);
                                break;
                            }
                        }
                        s.addCourse(c, t, score);
                    }
                }
            }
            System.out.println("students added successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }












        
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

    public static void UpdateDataBase(){
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/UNIVERSITY", "root", "7277777772Baykal")) {

            String query = "DELETE FROM assignment";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            for(int i = 0 ; i < Assignments.size() ; i++){
                query = "INSERT INTO assignment (name, deadline, available) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, Assignments.get(i).getName());
                preparedStatement.setString(2, Assignments.get(i).getDeadline());
                preparedStatement.setString(3, Assignments.get(i).getAvailable());
                preparedStatement.executeUpdate();
            }
            System.out.println("assignments updated !");

            query = "DELETE FROM courses";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            for(int i = 0 ; i < courses.size() ; i++){
                query = "INSERT INTO courses (name, units, date_of_exam) VALUES (?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, courses.get(i).getCourseName());
                preparedStatement.setInt(2, courses.get(i).getUnitsPerCourse());
                preparedStatement.setString(3, courses.get(i).getDate_Of_Exam());
                preparedStatement.executeUpdate();
            }
            System.out.println("courses updated !");

            query = "DELETE FROM teachers";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            for(int i = 0 ; i < teachers.size() ; i++){
                query = "INSERT INTO courses (teacher_id, first_name, last_name,courses,projects) VALUES (?, ?, ?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, String.valueOf(teachers.get(i).getTeacherid()));
                preparedStatement.setString(2, teachers.get(i).getFirst_Name());
                preparedStatement.setString(3, teachers.get(i).getLast_name());
                String add = "";
                List<Course> c = teachers.get(i).getCourses();
                for(int j = 0 ; j < c.size() ; j++){
                    if(j==c.size()-1){
                        add+=c.get(j).getCourseName();
                    }
                    else{
                        add+=c.get(j).getCourseName()+" ";
                    }
                }
                preparedStatement.setString(4, add);
                add = "";
                List<Assignment> a = teachers.get(i).getProjects();
                for(int j = 0 ; j < a.size() ; j++){
                    if(j==a.size()-1){
                        add+=a.get(j).getName();
                    }
                    else{
                        add+=a.get(j).getName()+" ";
                    }
                }
                preparedStatement.setString(5,add );
                preparedStatement.executeUpdate();
            }
            System.out.println("teachers updated !");

            query = "DELETE FROM terms";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            query = "DELETE FROM students";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            for(int i = 0 ; i<students.size() ; i++){
                query = "INSERT INTO students (firs_name, last_name, email,stdent_id,birthdate,terms) VALUES (?, ?, ?,?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, students.get(i).getName());
                preparedStatement.setString(2, students.get(i).getLast_Name());
                preparedStatement.setString(3, students.get(i).getEmail());
                preparedStatement.setString(4, String.valueOf(students.get(i).getSID()));
                preparedStatement.setString(5,students.get(i).getBirthdate());
                preparedStatement.setInt(6, students.get(i).getNumber_Of_Terms());
                preparedStatement.executeUpdate();
                List<Term> T = students.get(i).getTerms();
                for(int j = 0 ; j < T.size() ; j++){
                    List<SCORE> scores = new ArrayList<SCORE>();
                    List<Course> Courses_Assigned = new ArrayList<Course>();
                    List<Teacher> listOfTeachers = new ArrayList<Teacher>();                    
                    for(int k = 0 ; k < scores.size() ; k++){
                        query = "INSERT INTO terms (student_id, term_number, teacher,course,score) VALUES (?, ?, ?,?,?)";
                        preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, String.valueOf(students.get(i).getSID()));
                        preparedStatement.setInt(2, j);
                        preparedStatement.setString(3, listOfTeachers.get(k).getLast_name());
                        preparedStatement.setString(4, Courses_Assigned.get(k).getCourseName());
                        preparedStatement.setDouble(5,scores.get(k).getScore());
                        preparedStatement.executeUpdate();
                    }
                }
            }
            System.out.println("students updated !");

            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}