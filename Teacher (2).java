import java.util.*;

public class Teacher {
    private String First_Name;
    private String Last_name;
    private int Number_of_lessons;
    private Long Teacher_id;
    private List<Course> courses;
    private List<Student> studentsOfTeacher;
    private List<Assignment> Projects=new ArrayList<>();
    Teacher(String First_Name, String Last_name, long Teacher_id) {
        this.First_Name = First_Name;
        this.Last_name = Last_name;
        this.Teacher_id = Teacher_id;
    }

    public long getTeacherid(){
        return Teacher_id;
    }

    public void setFirst_Name(String first_Name) {
        this.First_Name = first_Name;
    }

    public void addCourse(Course course){
        courses.add(course);
        course.addteacher(this);
    }

    public void setLast_name(String last_name) {
        this.Last_name = last_name;
    }

    public void setNumber_of_lessons(int number_of_lessons) {
        this.Number_of_lessons = number_of_lessons;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void ChangeDeadLine(Assignment newAssign , String Newtime){
        newAssign.setDeadline(Newtime);
    }
    public void setStudentsOfTeacher(List<Student> studentsOfTeacher) {
        this.studentsOfTeacher = studentsOfTeacher;
    }

    public void setProjects(List<Assignment> Projects) {
        this.Projects = Projects;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public int getNumber_of_lessons() {
        return Number_of_lessons;
    }

    public List<Course> getCourses() {
        return courses;
    }
    public List<Student> getStudentsOfTeacher() {
        return studentsOfTeacher;
    }
    public List<Assignment> getProjects() {
        return Projects;
    }
    public void addStudent(Student student , Course course){
        studentsOfTeacher.add(student);
        if(student.getCourses_Assigned().contains(course)){
        }
        else{
            student.addCourse(course, this);
        }
    }

    public void removeStudent(Student student , Course course){
        studentsOfTeacher.remove(student);
        if(student.getCourses_Assigned().contains(course)){
            student.removeCourse(course, this);
        }

    }

    public void addProjects(Assignment x){
        Projects.add(x);
    }
    public void removeProjects(Assignment x){
        Projects.remove(x);
    }
}
