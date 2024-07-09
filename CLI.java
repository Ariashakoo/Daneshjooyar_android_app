import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CLI {
    static String[][] passwords =new String[0][5];
    static List<Student> students = new ArrayList<Student>();
    static List<Course> courses = new ArrayList<Course>();
    static List<Teacher> teachers = new ArrayList<Teacher>();
    static List<Assignment> Assignments = new ArrayList<Assignment>();
    public static void main(String[] args){
        
        try{
            RandomAccessFile passwordsraf = new RandomAccessFile("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\PASSWORDS.txt", "rw");
            RandomAccessFile studentsraf = new RandomAccessFile("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\studentsinfo.txt", "rw");
            RandomAccessFile teachersraf = new RandomAccessFile("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\teachersinfo.txt", "rw");
            RandomAccessFile coursesraf = new RandomAccessFile("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\courses.txt", "rw");
            RandomAccessFile assignmentsraf = new RandomAccessFile("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\assignments.txt", "rw");
            

            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\PASSWORDS.txt"));
            int lines = 0;
            String line;
            String[] parts ;
            while (reader.readLine() != null) lines++;
            reader.close();
            passwords = new String[lines][4];
            for(int i = 0 ; i < lines ; i++){
                line = passwordsraf.readLine();
                parts = line.split("~");
                passwords[i][0] = parts[0];
                passwords[i][1] = parts[1];
                passwords[i][2] = parts[2];
                passwords[i][3] = parts[3];
                passwords[i][4] = parts[4];
            }
            
            reader = new BufferedReader(new FileReader("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\assignments.txt"));
            lines = 0;
            while (reader.readLine() != null) lines++;
            reader.close();
            for(int i = 0 ; i < lines ;i++){
                line = assignmentsraf.readLine();
                parts = line.split("~");
                Assignment newassignment = new Assignment( parts[2],Boolean.parseBoolean(parts[1]) ,parts[0],parts[3],parts[4]);
                Assignments.add(newassignment);
            }
            
            reader = new BufferedReader(new FileReader("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\courses.txt"));
            lines = 0;
            while (reader.readLine() != null) lines++;
            reader.close();
            for(int i = 0 ; i < lines ;i++){
                line = coursesraf.readLine();
                parts = line.split("~");
                Course newcourse = new Course(parts[0],Integer.parseInt(parts[1]) );
                newcourse.setDate_Of_Exam(parts[4]);
                courses.add(newcourse);
            }


            reader = new BufferedReader(new FileReader("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\teachersinfo.txt"));
            lines = 0;
            while (reader.readLine() != null) lines++;
            reader.close();
            for(int i = 0 ; i < lines ;i++){
                line = teachersraf.readLine();
                parts = line.split("~");
                Teacher newteacher = new Teacher(parts[1],parts[2], Long.parseLong(parts[0]));
                String[] projects = parts[4].split("-");
                for(int j = 0 ; j < projects.length ; j++){
                    for(int k = 0 ;k < Assignments.size() ; k++){
                        if(Assignments.get(k).getName().equals(projects[j])){
                            newteacher.addProjects(Assignments.get(k));
                        }
                    }
                }
                for(int j=0;j<Assignments.size();j++){
                    if(Assignments.get(j).getTeacherid().equals(parts[0])){
                        newteacher.addProjects(Assignments.get(j));
                    }
                }
                String[] teachercourses = parts[3].split("-");
                for(int j = 0 ; j < teachercourses.length ; j++){
                    for(int k = 0 ;k < courses.size() ; k++){
                        if(courses.get(k).getCourseName().equals(teachercourses[j])){
                            newteacher.addCourse(courses.get(k));
                        }
                    }
                }
            }
            
            reader = new BufferedReader(new FileReader("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\studentsinfo.txt"));
            lines = 0;
            while (reader.readLine() != null) lines++;
            reader.close();
            for(int i = 0 ; i < lines ; i++){
                line = studentsraf.readLine();
                parts = line.split("~");
                Student newstudent = new Student(parts[1],parts[2], Long.parseLong(parts[0]),parts[3]);
                String[] terms = parts[4].split("/");
                for(int j = 0 ; j < terms.length;j++){
                    newstudent.addTerms();
                    String[] studentcourses = terms[j].split("-");
                    for(int k = 0 ; k <studentcourses.length;k++){
                        String[] courseandteacherandscore = studentcourses[k].split(":");
                        for(int m = 0 ; m<courses.size() ; m++){
                            if(courseandteacherandscore[0].equals(courses.get(m).getCourseName())){
                                for(int n = 0 ; n < teachers.size() ; n++){
                                    if(teachers.get(n).getTeacherid()==Long.parseLong(courseandteacherandscore[1])){
                                        newstudent.addCourse(courses.get(m),teachers.get(n));
                                        newstudent.givescore(courses.get(m) ,teachers.get(n) , Double.parseDouble(courseandteacherandscore[2]));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            passwordsraf.close();
            studentsraf.close();
            teachersraf.close();
            coursesraf.close();
            assignmentsraf.close();
            reader.close();
        }catch(Exception e){}
            


        try{
            Scanner sc = new  Scanner(System.in);
            int number ;
            boolean EXIT = true;
            String RESET = "\u001B[0m";
            String RED = "\u001B[31m";
            String GREEN = "\u001B[32m";
            String YELLOW = "\u001B[33m";
            String BLUE = "\u001B[34m";
            String PURPLE = "\u001B[35m";
            String CYAN = "\u001B[36m";
            String WHITE = "\u001B[37m";        
            String BLACK = "\u001B[30m";
            String BLACK_BACKGROUND = "\u001B[40m";
            String RED_BACKGROUND = "\u001B[41m";
            String GREEN_BACKGROUND = "\u001B[42m";
            String ELLOW_BACKGROUND = "\u001B[43m";
            String BLUE_BACKGROUND = "\u001B[44m";
            String PURPLE_BACKGROUND = "\u001B[45m";
            String CYAN_BACKGROUND = "\u001B[46m";
            String WHITE_BACKGROUND = "\u001B[47m";
    
            while(EXIT){
                    System.out.print("\033[H\033[2J");
                    System.out.print(BLACK+"ENTER NUMBER YOUT WANT : \n");
                    System.out.print(GREEN+"1_ENTER AS ADMIN\n");
                    System.out.print("2_ENTER AS TEACHER \n");
                    System.out.print("3_EXIT\n"+WHITE);      
                    try{
                        number = sc.nextInt();
                        sc.nextLine();
                        if(number == 1){
                            boolean CORRECT = true;
                            while(CORRECT){
                            System.out.print("\033[H\033[2J");  
                            System.out.println("ENTER PASSWORD : "+BLUE+"(ENTER 0 TO EXIT)"+RESET);
                            String PASSWORD = "ADMINBEHESHTI";
                            String ENTERD = sc.nextLine();
                            if(ENTERD.equals("0")){
                                CORRECT = false;
                                System.out.print("\033[H\033[2J");  
                                break;
                            }
                            if(ENTERD.equals(PASSWORD)){
                                System.out.print("\033[H\033[2J");  
                                System.out.println(GREEN+"CORRECT PASSWORD!    \nWELCOME ADMIN :)");
                                TimeUnit.SECONDS.sleep(5);
                                ADMIN();
                                CORRECT = false;
                            }
                            else{
                                System.out.println(RED+"WRONG PASSWORD , TRY AGAIN OR 0 FOR EXIT"+RESET);
                                TimeUnit.SECONDS.sleep(5);
                            }
                        }
    
                        }
                        else if(number == 2){
                            TEACHER();
                        }
                        else if(number == 3){
                            System.out.print("\033[H\033[2J");
                            EXIT = false;
                        }
                        else{
                            throw new Exception();
                        }
                    }  catch(Exception e){
                        System.out.println(RED+"ENTER A NUBER BETWEEN 1-3 !"+RESET);
                        try{
                            TimeUnit.SECONDS.sleep(3);
                        }catch(Exception a){}
                        sc.nextLine();
                    }
            }
        }finally{
            try{
                FileWriter writer = new FileWriter("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\PASSWORDS.txt");
                for(int i = 0 ; i < passwords.length ; i++){
                    if(i==passwords.length-1){
                        writer.write(String.valueOf(passwords[i][0])+"~"+passwords[i][1]+"~"+passwords[i][2]+"~"+passwords[i][3]+"~"+passwords[i][4]);
                    }
                    else{
                        writer.write(String.valueOf(passwords[i][0])+"~"+passwords[i][1]+"~"+passwords[i][2]+"~"+passwords[i][3]+"~"+passwords[i][4]+"\n");
                    }
                }
                writer.flush();
                writer.close();

                writer = new FileWriter("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\studentsinfo.txt");
                for(int i = 0 ; i<students.size() ;i++){
                    Student s = students.get(i);
                    String info ="";
                    info+=s.getSID().toString()+"~";
                    info+=s.getName()+"~"+s.getLast_Name()+"~"+s.getEmail()+"~";
                    List<Term> terms = s.getTerms();
                    for(int j = 0 ; j < terms.size() ;j++){
                        List<SCORE> scores = terms.get(i).getScores();
                        List<Course> Courses_Assigned =terms.get(i).getCourses_Assigned();
                        List<Teacher> listOfTeachers =terms.get(i).getListOfTeachers();
                        for(int k = 0 ; k<scores.size();k++){
                            info+=Courses_Assigned.get(k).getCourseName()+":"+listOfTeachers.get(k).getFirst_Name()+":"+String.valueOf(scores.get(k).getScore());
                            if(k!=scores.size()-1){
                                info+="-";
                            }
                        }
                        if(j!=terms.size()-1){
                            info+="/";
                        }
                    }
                    info+="~"+s.getBirthdate();
                    if(i!=passwords.length-1){
                        info+="\n";
                    }
                    writer.write(info);
                }
                writer.flush();
                writer.close();

                writer = new FileWriter("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\assignments.txt");
                for(int i = 0 ; i <Assignments.size() ; i++){
                    writer.write(Assignments.get(i).getName()+"~"+Assignments.get(i).getDeadline()+"~"+Boolean.toString(Assignments.get(i).isAvailable())+"~"+Assignments.get(i).getCoursename()+"~"+Assignments.get(i).getTeacherid());
                    if(i!=Assignments.size()-1){
                        writer.write("\n");
                    }
                }
                writer.flush();
                writer.close();

                writer = new FileWriter("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\teachersinfo.txt");
                for(int i =0 ; i<teachers.size();i++){
                    writer.write(String.valueOf(teachers.get(i).getTeacherid())+"~"+teachers.get(i).getFirst_Name()+"~"+teachers.get(i).getLast_name()+"~");
                    List<Course> courses =teachers.get(i).getCourses();
                    for(int j = 0 ; j <courses.size();j++ ){
                        writer.write(courses.get(i).getCourseName());
                        if(j!=courses.size()-1){
                            writer.write("-");
                        }
                    }
                    if(i!=teachers.size()-1){
                        writer.write("\n");
                    }
                }
                writer.flush();
                writer.close();

                writer = new FileWriter("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\courses.txt");
                for(int i = 0 ; i < courses.size();i++){
                    writer.write(courses.get(i).getCourseName()+"~"+String.valueOf(courses.get(i).getUnitsPerCourse())+"~"+courses.get(i).getDate_Of_Exam());
                    if(i!=courses.size()-1){
                        writer.write("\n");
                    }
                }
                writer.flush();
                writer.close();

                writer = new FileWriter("C:\\Users\\NoteBook\\Desktop\\mini\\mini-project\\data\\birthdate.txt");
                for(int i = 0 ; i < students.size();i++){
                    writer.write(students.get(i).getName()+"~"+students.get(i).getLast_Name()+"~"+students.get(i).getBirthdate());
                    if(i!=courses.size()-1){
                        writer.write("\n");
                    }
                }
                writer.flush();
                writer.close();
            }catch(Exception e){}
        }
    }
    public static void ADMIN(){
        Boolean CORRECT = true;
        Scanner sc = new Scanner(System.in);
        while(CORRECT){
            System.out.print("\033[H\033[2J");
            System.out.print("\u001B[37m"+"ENTER WHAT YOU WANT : \n");
            System.out.print("\u001B[34m"+"1_ ADD / REMOVE STUDENTS \n");
            System.out.print("2_ CHANGE / GET STUDENT INFO \n");
            System.out.print("3_ ADD / REMOVE TEACHERS \n");
            System.out.print("4_ CHANGE / GET TEACHER INFO \n");
            System.out.print("\u001B[35m"+"ENTER 0 TO EXIT \n"+"\u001B[0m");
            try{
                int vorodi = sc.nextInt();
                if(vorodi == 0){
                    CORRECT = false;
                }
                else if(vorodi == 1){
                    int vorodiadmin ;
                    boolean CORRECT1= true;
                    while(CORRECT1){
                        System.out.print("\033[H\033[2J");
                        System.out.print("\u001B[33m"+"ENTER WHAT YOU WANT :\n");
                        System.out.print("1_ ADD STUDENT \n");
                        System.out.print("2_ REMOVE STUDENT \n");
                        System.out.print("\u001B[32m"+"ENTER 0 TO EXIT\n"+"\u001B[0m");
                        try{
                            vorodiadmin = sc.nextInt();
                            if(vorodiadmin==0){
                                CORRECT1 = false;
                            }
                            else if(vorodiadmin == 1 ){
                                addstudent();
                            }
                            else if(vorodiadmin == 2){
                                boolean EXIT = true;
                                while(EXIT){
                                    System.out.print("\033[H\033[2J");
                                    System.out.print("\u001B[30m"+"ENTER 0 TO CANCLE !\n");
                                    System.out.print("\u001B[35m"+"ENTER STUDENT ID : \n"+"\u001B[0m");
                                    try{
                                        long id = sc.nextInt();
                                        if(id==0){
                                            EXIT = false;
                                        }
                                        else{
                                            boolean is = true;
                                            for(int i = 0 ; i<passwords.length ; i++){
                                                if(Long.parseLong(passwords[i][0])==id){
                                                    if(passwords[1][3].equals("t")){
                                                        throw new IOException("THE ID YOU ENTERED BELONG TO A TEACHER !");
                                                    }
                                                    else{
                                                        String[][] newpassword= new String[passwords.length-1][4];
                                                        for(int j = 0 ; j < i ; j++){
                                                            newpassword[j][0] = passwords[j][0];
                                                            newpassword[j][1] = passwords[j][1];
                                                            newpassword[j][2] = passwords[j][2];
                                                            newpassword[j][3] = passwords[j][3];
                                                        }
                                                        for(int j = i+1 ; j<passwords.length ; j++){
                                                            newpassword[j][0] = passwords[j][0];
                                                            newpassword[j][1] = passwords[j][1];
                                                            newpassword[j][2] = passwords[j][2];
                                                            newpassword[j][3] = passwords[j][3];
                                                        }
                                                        passwords = newpassword;
                                                    }
                                                }
                                            }
                                            for(int i = 0 ; i < students.size() ; i++){
                                                if(students.get(i).getSID()==id){
                                                    is = false;
                                                    students.remove(i);
                                                    break;
                                                }
                                            }
                                            if(is){
                                                throw new Exception();
                                            }
                                            else{
                                                System.out.println("\u001B[32m"+"STUDENT SUCCESSFULLY DELETED ! "+"\u001B[0m");
                                            }
                                        }
                                    }catch(InputMismatchException e){
                                        System.out.println("\u001B[31m"+"ENTER ID (NUMBER)"+"\u001B[0m");
                                        sc.nextLine();
                                        try{
                                            TimeUnit.SECONDS.sleep(3);
                                        }catch(Exception a){}
                                    }catch(IOException z){
                                        System.out.println("\u001B[31m"+z.getMessage()+"\u001B[0m");
                                        try{
                                            TimeUnit.SECONDS.sleep(3);
                                        }catch(Exception a){}
                                    }catch(Exception A){
                                        System.out.println("\u001B[31m"+"ID NOT FOUND !"+"\u001B[0m");
                                        try{
                                            TimeUnit.SECONDS.sleep(3);
                                        }catch(Exception a){}
                                    }
                                }
                            }
                            else{
                                throw new Exception();
                            }
                        }catch(Exception e){
                            System.out.println("\u001B[31m"+"ENTER 1 , 2 OR 0 TO EXIT");
                            sc.nextLine();
                            try{
                                TimeUnit.SECONDS.sleep(3);
                            }catch(Exception a){}
                        }
                    }

                }

                else if (vorodi == 2){
                    System.out.print("\033[H\033[2J");
                    System.out.println("\u001B[35m"+"ENTER STUDENT ID : "+"\u001B[0m");
                    long id ;
                    boolean CORRECT2 = true;
                    while(CORRECT2){
                        try{
                            id = sc.nextInt();
                            boolean is = true ;
                            for(int i = 0 ; i<students.size();i++){
                                if(students.get(i).getSID()==id){
                                    Student s = students.get(i);
                                    System.out.println("\u001B[35m"+"name = "+"\u001B[34m"+s.getName());
                                    System.out.println("\u001B[35m"+"last name = "+"\u001B[34m"+s.getLast_Name());
                                    System.out.println("\u001B[35m"+"id = "+"\u001B[34m"+s.getSID());
                                    System.out.println("\u001B[35m"+"number of terms ="+"\u001B[34m"+s.getNumber_Of_Terms());
                                    try{
                                        TimeUnit.SECONDS.sleep(15);
                                    }catch(Exception a){}
                                    CORRECT2 = false;
                                    is = false;
                                }
                            }
                            if(is){
                                System.out.println("\u001B[31m"+"ID NOT FOUND ! "+"\u001B[9m");
                            }
                        }catch(Exception e){
                            System.out.println("\u001B[31m"+"ENTER AN ID (NUMBER) !"+"\u001B[0m");
                            sc.nextLine();
                            try{
                                TimeUnit.SECONDS.sleep(3);
                                System.out.print("\033[H\033[2J");
                                System.out.println("\u001B[35m"+"ENTER STUDENT ID : "+"\u001B[0m");
                            }catch(Exception a){}
                        }
                    }
                }

                else if(vorodi == 3){
                    addteacher();
                }

                else if(vorodi == 4){
                    System.out.print("\033[H\033[2J");
                    System.out.println("\u001B[35m"+"ENTER TEACHER ID : "+"\u001B[0m");
                    long id ;
                    boolean CORRECT2 = true;
                    while(CORRECT2){
                        try{
                            id = sc.nextInt();
                            CORRECT2 = false;
                            boolean is = true ;
                            for(int i = 0 ; i<teachers.size();i++){
                                if(students.get(i).getSID()==id){
                                    Teacher t = teachers.get(i);
                                    System.out.println("\u001B[35m"+"name = "+"\u001B[34m"+t.getFirst_Name());
                                    System.out.println("\u001B[35m"+"last name = "+"\u001B[34m"+t.getLast_name());
                                    System.out.println("\u001B[35m"+"id = "+"\u001B[34m"+t.getTeacherid());
                                    System.out.println("\u001B[35m"+"number of courses ="+"\u001B[34m"+t.getNumber_of_lessons());
                                    try{
                                        TimeUnit.SECONDS.sleep(15);
                                    }catch(Exception a){}
                                    CORRECT2 = false;
                                    is = false;
                                }
                            }
                            if(is){
                                System.out.println("\u001B[31m"+"ID NOT FOUND ! "+"\u001B[9m");
                            }                        
                        }catch(Exception e){
                            System.out.println("\u001B[31m"+"ENTER AN ID (NUMBER) !"+"\u001B[0m");
                            sc.nextLine();
                            try{
                                TimeUnit.SECONDS.sleep(3);
                                System.out.print("\033[H\033[2J");
                                System.out.println("\u001B[35m"+"ENTER TEACHER ID : "+"\u001B[0m");
                            }catch(Exception a){}
                        }
                    }
                }

                else{
                    throw new Exception();
                }
                
            }catch(Exception e){
                System.out.println("\u001B[31m"+"ENTER A NUMBER BETWEEN 1-4 OR 0 TO EXIT ! ");
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(Exception a){}
                sc.nextLine();
            }
        }
    }

    public static int addstudent(){
        Scanner sc = new Scanner(System.in);
        boolean valid = true;
        String username="";
        while (valid){
            System.out.print("\033[H\033[2J");
            System.out.println("\u001B[30m"+"ENTER USERNAME : ");
            System.out.println("\u001B[34m"+"IT SHOULD BE 6 OR MORE CHARACTER OR NUMBER");
            System.out.println("\u001B[35m"+"ENTER 0 TO CANCLE"+"\u001B[0m");
            username = sc.nextLine();
            if(Pattern.matches("[A-Z,a-z,0-9]{6,}",username)){
                for(int i = 0 ; i < passwords.length ; i++){
                    if(passwords[i][1].equals(username)){
                        System.out.println("\u001B[31m"+"USERNAME ALREADY EXIST ! "+"\u001B[0m");
                        try{
                            TimeUnit.SECONDS.sleep(3);
                            return 0;
                        }catch(Exception a){}
                    }
                    
                }
                valid = false;
            }
            else{
                if(username.equals("0")){
                    valid = false;
                    return 0;
                }
                System.out.println("\u001B[31m"+"ENTER A VALID USERNAME !"+"\u001B[0m");
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(Exception a){}
            }
        }
        String password ;
        valid = true;
        while (valid){
            System.out.print("\033[H\033[2J");
            System.out.println("\u001B[35m"+"ENTER PASSWORD : ");
            System.out.println("\u001B[30m"+"IT SHOULD BE AT LEAST 8 CHARACTER\nAT LEAST 1 LETTER 1 NUMBER AND [!@#$%^&*()]");
            System.out.println("\u001B[33m"+"ENTER 0 TO CANCLE !"+"\u001B[0m");
            password = sc.nextLine();
            if(Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",password)){
                System.out.print("\033[H\033[2J");
                System.out.println("\u001B[35m"+"ENTER NAME : "+"\u001B[0m");
                String name = sc.nextLine();
                System.out.println("\u001B[35m"+"ENTER LAST NAME : "+"\u001B[0m");
                String lastname = sc.nextLine();
                System.out.println("\u001B[35m"+"ENTER ID : "+"\u001B[0m");
                long id = 0;
                while(true){
                    try{
                        id = sc.nextLong();
                        break;
                    }catch(Exception e){
                        System.out.println("\u001B[31m"+"ENTER A VALID ID ! "+"\u001B[0m");
                        sc.nextLine();
                        try{
                            TimeUnit.SECONDS.sleep(3);
                            System.out.println("\u001B[35m"+"ENTER AGAIN : "+"\u001B[0m");
                        }catch(Exception a){}
                    }
                }
                String Email;
                System.out.print("\033[H\033[2J");
                System.out.println("\u001B[35m"+"ENTER EMAIL : "+"\u001B[0m");
                sc.nextLine();
                Email = sc.nextLine();
                Student newstudent = new Student(name, lastname, id,Email);
                students.add(newstudent);
                String[][] newpassword = new String[passwords.length+1][4];
                for(int i = 0 ; i < passwords.length ; i++){
                    newpassword[i][0] = passwords[i][0];
                    newpassword[i][1] = passwords[i][1];
                    newpassword[i][2] = passwords[i][2];
                    newpassword[i][3] = passwords[i][3];
                }
                newpassword[passwords.length][0]=Long.toString(id);
                newpassword[passwords.length][1]=username;
                newpassword[passwords.length][2]=password;
                newpassword[passwords.length][3]="s";
                try{
                    System.out.print("\033[H\033[2J");
                    System.out.println("\u001B[32m"+"STUDENT SUCCESSFULLY CREATED !"+"\u001B[0m");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("\u001B[35m"+"ENTER AGAIN : "+"\u001B[0m");
                }catch(Exception a){}
                valid = false;
            }
            else{
                if(password.equals("0")){
                    valid=false;
                    return 0;
                }
                else{
                    System.out.println("\u001B[31m"+"ENTER A VALID PASSWORD !"+"\u001B[0m");
                    try{
                        TimeUnit.SECONDS.sleep(3);
                    }catch(Exception a){}
                }
            }
        }
        return 0 ;
    }
    


    public static int addteacher(){
        Scanner sc = new Scanner(System.in);
        boolean valid = true;
        String username="";
        while (valid){
            System.out.print("\033[H\033[2J");
            System.out.println("\u001B[30m"+"ENTER USERNAME : ");
            System.out.println("\u001B[34m"+"IT SHOULD BE 6 OR MORE CHARACTER OR NUMBER");
            System.out.println("\u001B[35m"+"ENTER 0 TO CANCLE"+"\u001B[0m");
            username = sc.nextLine();
            if(Pattern.matches("[A-Z,a-z,0-9]{6,}",username)){
                for(int i = 0 ; i < passwords.length ; i++){
                    if(passwords[i][1].equals(username)){
                        System.out.println("\u001B[31m"+"USERNAME ALREADY EXIST ! "+"\u001B[0m");
                        try{
                            TimeUnit.SECONDS.sleep(3);
                            return 0;
                        }catch(Exception a){}
                    }
                    
                }
                valid = false;
            }
            else{
                if(username.equals("0")){
                    valid = false;
                    return 0;
                }
                System.out.println("\u001B[31m"+"ENTER A VALID USERNAME !"+"\u001B[0m");
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(Exception a){}
            }
        }
        String password ;
        valid = true;
        while (valid){
            System.out.print("\033[H\033[2J");
            System.out.println("\u001B[35m"+"ENTER PASSWORD : ");
            System.out.println("\u001B[30m"+"IT SHOULD BE AT LEAST 8 CHARACTER\nAT LEAST 1 LETTER 1 NUMBER AND [!@#$%^&*()]");
            System.out.println("\u001B[33m"+"ENTER 0 TO CANCLE !"+"\u001B[0m");
            password = sc.nextLine();
            if(Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",password)){
                System.out.print("\033[H\033[2J");
                System.out.println("\u001B[35m"+"ENTER NAME : "+"\u001B[0m");
                String name = sc.nextLine();
                System.out.println("\u001B[35m"+"ENTER LAST NAME : "+"\u001B[0m");
                String lastname = sc.nextLine();
                System.out.println("\u001B[35m"+"ENTER ID : "+"\u001B[0m");
                long id = 0;
                while(true){
                    try{
                        id = sc.nextLong();
                        break;
                    }catch(Exception e){
                        System.out.println("\u001B[31m"+"ENTER A VALID ID ! "+"\u001B[0m");
                        sc.nextLine();
                        try{
                            TimeUnit.SECONDS.sleep(3);
                        }catch(Exception a){}
                    }
                }

                Teacher newteacher = new Teacher(name , lastname,id);
                teachers.add(newteacher);
                String[][] newpassword = new String[passwords.length+1][4];
                for(int i = 0 ; i < passwords.length ; i++){
                    newpassword[i][0] = passwords[i][0];
                    newpassword[i][1] = passwords[i][1];
                    newpassword[i][2] = passwords[i][2];
                    newpassword[i][3] = passwords[i][3];
                }
                newpassword[passwords.length][0]=Long.toString(id);
                newpassword[passwords.length][1]=username;
                newpassword[passwords.length][2]=password;
                newpassword[passwords.length][3]="t";
                try{
                    System.out.print("\033[H\033[2J");
                    System.out.println("\u001B[32m"+"TEACHER SUCCESSFULLY CREATED !"+"\u001B[0m");
                    TimeUnit.SECONDS.sleep(3);
                }catch(Exception a){}
                valid = false;
            }
            else{
                if(password.equals("0")){
                    valid=false;
                    return 0;
                }
                else{
                    System.out.println("\u001B[31m"+"ENTER A VALID PASSWORD !"+"\u001B[0m");
                    try{
                        TimeUnit.SECONDS.sleep(3);
                    }catch(Exception a){}
                }
            }
        }
        return 0 ;
    }

    public static int TEACHER(){
        Scanner sc = new Scanner(System.in);
        String username;
        int number =0;
        boolean CORRECT=true ;
        Teacher t = null;
        while(CORRECT){
            System.out.print("\033[H\033[2J");
            System.out.println("\u001B[30m"+"ENTER USERNAME : ");
            System.out.println("\u001B[35m"+"ENTER 0 TO EXIT"+"\u001B[0m");
            username = sc.nextLine();
            if(username.equals("0")){
                return 0;
            }

            for(int i = 0 ; i < passwords.length ;i++){
                if(passwords[i][1].equals(username)){
                    number = i;
                    CORRECT  = false;
                    for(int j = 0 ; j <teachers.size();j++){
                        if(teachers.get(j).getTeacherid()==Long.parseLong(passwords[i][0])){
                            t = teachers.get(j);
                        }
                    }
                }
            }
            if(CORRECT){
                System.out.println("\u001B[31m"+"USERNAME NOT FOUND ! TRY AGAIN ."+"\u001B[0m");
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(Exception a){}
                System.out.print("\033[H\033[2J");
            }
        }

        String password ;
        CORRECT = true;
        while(CORRECT){
            System.out.print("\033[H\033[2J");
            System.out.println("\u001B[30m"+"ENTER PASSWORD :");
            System.out.println("\u001B[35m"+"ENTER 0 TO EXIT"+"\u001B[0m");
            password = sc.nextLine();
            if(password.equals("0")){
                return 0 ;
            }
            if(password.equals(passwords[number][2])){
                CORRECT = false;
                System.out.print("\033[H\033[2J");
                System.out.println("\u001B[32m"+"CORRECT PASSWORD !"+"\u001B[0m");
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(Exception a){}
                System.out.print("\033[H\033[2J");
                System.out.print("\u001B[37m"+"ENTER WHAT YOU WANT : \n");
                System.out.print("\u001B[34m"+"1_ SHOW STUDENTS \n");
                System.out.print("2_ ADD STUDENT TO COURSE \n");
                System.out.print("3_ REMOVE STUDENT \n");
                System.out.print("4_ MY INFO \n");
                System.out.print("\u001B[35m"+"ENTER 0 TO EXIT \n"+"\u001B[0m");
                int voroditeacher  ;
                try{    
                    voroditeacher=sc.nextInt();
                    if(voroditeacher==1){
                        List<Student> s = t.getStudentsOfTeacher();
                        for(int i = 0 ; i<s.size();i++){
                            System.out.println(String.valueOf(i+1)+"_"+s.get(i).getName()+s.get(i).getLast_Name());
                        }
                    }
                    else if(voroditeacher==2){
                        System.out.println("ENTER STUDENT ID : ");
                        long addstudent;
                        Student s =null;
                        try{
                            addstudent = sc.nextLong();
                            boolean is = true;
                            for(int i = 0 ; i<students.size();i++){
                                if(students.get(i).getSID()==addstudent){
                                    is = false;
                                    s = students.get(i);
                                    break;
                                }
                            }
                            if(is){
                                System.out.println("ID NOT FOUND !");
                                try{
                                    TimeUnit.SECONDS.sleep(3);
                                }catch(Exception a){}
                            }
                            else{
                                System.out.println("ENTER COURSE NAME :");
                                String coursename = sc.nextLine();
                                Course c = null;
                                boolean courseis = true;
                                for(int i = 0 ; i < courses.size();i++){
                                    if(courses.get(i).getCourseName().equals(coursename)){
                                        c = courses.get(i);
                                        courseis = false;
                                    }
                                }
                                if(courseis){
                                    System.out.println("COURSE NOT FOUND ! ");
                                    try{
                                        TimeUnit.SECONDS.sleep(3);
                                    }catch(Exception a){}
                                    return 0;
                                }
                                t.addStudent(s,c);
                                System.out.println("\u001B[32m"+"DONE !"+"\u001B[0m");
                                try{
                                    TimeUnit.SECONDS.sleep(3);
                                }catch(Exception a){}
                            }
                        }catch(Exception e){
                            System.out.println("\u001B[31m"+"ENTER AN ID(NUMBER) !"+"\u001B[0m");
                            try{
                                TimeUnit.SECONDS.sleep(3);
                            }catch(Exception a){}
                        }
                    }
                    else if(voroditeacher == 3){
                        System.out.println("ENTER STUDENT ID : ");
                        long removestudent;
                        Student s =null;
                        try{
                            removestudent = sc.nextLong();
                            boolean is = true;
                            for(int i = 0 ; i<students.size();i++){
                                if(students.get(i).getSID()==removestudent){
                                    is = false;
                                    s = students.get(i);
                                    break;
                                }
                            }
                            if(is){
                                System.out.println("ID NOT FOUND !");
                                try{
                                    TimeUnit.SECONDS.sleep(3);
                                }catch(Exception a){}
                            }
                            else{
                                System.out.println("ENTER COURSE NAME :");
                                String coursename = sc.nextLine();
                                Course c = null;
                                boolean courseis = true;
                                for(int i = 0 ; i < courses.size();i++){
                                    if(courses.get(i).getCourseName().equals(coursename)){
                                        c = courses.get(i);
                                        courseis = false;
                                    }
                                }
                                if(courseis){
                                    System.out.println("COURSE NOT FOUND ! ");
                                    try{
                                        TimeUnit.SECONDS.sleep(3);
                                    }catch(Exception a){}
                                    return 0;
                                }
                                t.removeStudent(s,c);
                                System.out.println("\u001B[32m"+"DONE !"+"\u001B[0m");
                                try{
                                    TimeUnit.SECONDS.sleep(3);
                                }catch(Exception a){}
                            }
                        }catch(Exception e){
                            System.out.println("\u001B[31m"+"ENTER AN ID(NUMBER) !"+"\u001B[0m");
                            try{
                                TimeUnit.SECONDS.sleep(3);
                            }catch(Exception a){}
                        }
                    }
                    else if(voroditeacher==4){
                        System.out.print("\033[H\033[2J");
                        System.out.println("\u001B[35m"+"name = "+"\u001B[34m"+t.getFirst_Name());
                        System.out.println("\u001B[35m"+"last name = "+"\u001B[34m"+t.getLast_name());
                        System.out.println("\u001B[35m"+"id = "+"\u001B[34m"+t.getTeacherid());
                        System.out.println("\u001B[35m"+"number of courses ="+"\u001B[34m"+t.getNumber_of_lessons());
                        try{
                            TimeUnit.SECONDS.sleep(15);
                        }catch(Exception a){}
                    }
                    else if(voroditeacher==0){
                        return 0;
                    }
                    else if(voroditeacher>4){
                        throw new Exception();
                    }
                    else if(voroditeacher<0){
                        throw new Exception();
                    }
                }catch(Exception e){
                    System.out.println("\u001B[31m"+"ENTER A NUMBER BETWEET 0-4 ! "+"\u001B[0m");
                }
            }
        }
        return 0;
    }
    public void COURSE (){

    }
    public void ASSIGNMENT(){

    }
}