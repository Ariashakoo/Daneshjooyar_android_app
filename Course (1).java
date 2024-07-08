import java.util.*;
public class Course{
    private String Course_Name;
    private int Units_Per_Course;
    private List<Student> students = new ArrayList<Student>();
    private List<Teacher> teachers = new ArrayList<Teacher>();
    private String Date_Of_Exam ;
    private int Number_Of_Students_Assigned;
    
    Course(String courseName, int unitsPerCourse ) {
        this.Course_Name = courseName;
        this.Units_Per_Course = unitsPerCourse;
    }

    public String getCourseName() {
        return Course_Name;
    }

    public String getDate_Of_Exam(){
        return Date_Of_Exam;
    }
    public int getUnitsPerCourse() {
        return Units_Per_Course;
    }

    public List<Student> getStudents() {
        return students;
    }

    public boolean isAvailable() {
        if(teachers.size()>0){
            return true;
        }
        return false;
    }

    public int getNumberOfStudentsAssigned() {
        return Number_Of_Students_Assigned;
    }
    public void setCourseName(String courseName) {
        this.Course_Name = courseName;
    }

    public void setDate_Of_Exam(String Date_Of_Exam){
        this.Date_Of_Exam = Date_Of_Exam ;
    }
    public void setUnitsPerCourse(int unitsPerCourse) {
        this.Units_Per_Course = unitsPerCourse;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setNumberOfStudentsAssigned(int numberOfStudentsAssigned) {
        this.Number_Of_Students_Assigned = numberOfStudentsAssigned;
    }
    public void PrintStudents(){
        for (Student student : students){
            System.out.println(student.getName());
        }
    }
    public void AddStudents(Student New){
        students.add(New);
        Number_Of_Students_Assigned ++;
    }
    public void RemoveStudents(Student New){
        students.remove(New);
        Number_Of_Students_Assigned --;
    }
    public int sort(List<Integer> grades){
        Collections.sort(grades);
        int Length = grades.size();
        if (!grades.isEmpty()) {
            return grades.get(Length - 1);
        } else {
            return 0 ;
        }
    }
    public String[] DateCreator(String Date_Of_Exam){
        String[] Result = Date_Of_Exam.split("\\/");
        return Result;
    }

    public void addteacher(Teacher teacher ){
        teachers.add(teacher);
    }
}
