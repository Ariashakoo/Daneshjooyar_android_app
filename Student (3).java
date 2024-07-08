import java.util.*;
import javafx.scene.control.Alert.AlertType;
public class Student{
    private String name;
    private String Last_Name;
    private Long Studentid;
    private int Number_Of_Courses_Assigned;
    private int Number_Of_Units_Assigned;
    private List<Course> Courses_Assigned = new ArrayList<Course>();
    private List<Teacher> listOfTeachers = new ArrayList<Teacher>();
    private List<Term> terms = new ArrayList<Term>();
    private List<SCORE> Ongoing_Term_scores = new ArrayList<SCORE>();
    private int Number_Of_Terms = 0;
    private String Email ;
    private String birthdate;
    Student(String name, String Last_Name, Long Studentid , String Email){
        this.name =name;
        this.Last_Name = Last_Name;
        this.Studentid = Studentid;
        this.Email = Email;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    public String getBirthdate() {
        return birthdate;
    }

    public double MaxScore(){
        double maxscore=0.0;
        for(int i = 0 ; i< Ongoing_Term_scores.size();i++){
            if(Ongoing_Term_scores.get(i).getScore()>maxscore){
                maxscore = Ongoing_Term_scores.get(i).getScore();
            }
        }
        return maxscore;
    }   
    public double MinScore(){
        double minscore=20.0;
        for(int i = 0 ; i< Ongoing_Term_scores.size();i++){
            if(Ongoing_Term_scores.get(i).getScore()<minscore){
                minscore = Ongoing_Term_scores.get(i).getScore();
            }
        }
        return minscore;
    }
    public double CurrentTermAverage() {
        double average = 0.0;
        int i = 0;
        for (; i < Ongoing_Term_scores.size(); i++) {
            average += Ongoing_Term_scores.get(i).getScore();
        }
        average = average / i;
        return average;
    }
    public double TermAverage(int TermNumber){
        List<SCORE> SCORES = terms.get(TermNumber-1).getScores();
        double average = 0.0;
        int i = 0;
        for( ; i< SCORES.size();i++){
            average+=SCORES.get(i).getScore();
        }
        average = average/i;
        return average;
    }


    public void setEmail(String email) {
        Email = email;
    }
    public String getEmail() {
        return Email;
    }
    public Long getSID(){
        return Studentid;
    }
    public void setNumber_Of_Courses_Assigned(int Number_Of_Courses_Assigned) {
        this.Number_Of_Courses_Assigned = Number_Of_Courses_Assigned;
    }
    public void setLast_Name(String Last_Name){
        this.Last_Name = Last_Name;
    }
    public int getNumber_Of_Courses_Assigned() {
        return Number_Of_Courses_Assigned;
    }
    public void addTerms(){
        Number_Of_Terms ++;
        Term newterm = new Term();
        terms.add(newterm);
        Courses_Assigned = new ArrayList<Course>();
    }

    public void addCourse(Course course , Teacher teacher ){
        Ongoing_Term_scores = terms.get(Number_Of_Terms-1).getScores();
        Courses_Assigned = terms.get(Number_Of_Terms-1).getCourses_Assigned();
        listOfTeachers = terms.get(Number_Of_Terms-1).getListOfTeachers();
        teacher.addStudent(this , course);
        listOfTeachers.add(teacher);
        Courses_Assigned.add(course);
        Number_Of_Units_Assigned += course.getUnitsPerCourse();
        course.AddStudents(this);
        Number_Of_Courses_Assigned++;
        SCORE score = new SCORE(course,teacher.getTeacherid(),Number_Of_Terms);
        Ongoing_Term_scores.add(score);
        terms.get(Number_Of_Terms-1).setCourses_Assigned(Courses_Assigned);
        terms.get(Number_Of_Terms-1).setListOfTeachers(listOfTeachers);
        terms.get(Number_Of_Terms-1).setScores(Ongoing_Term_scores);
    } 

    public void addCourse(Course course , Teacher teacher , double score){
        Ongoing_Term_scores = terms.get(Number_Of_Terms-1).getScores();
        Courses_Assigned = terms.get(Number_Of_Terms-1).getCourses_Assigned();
        listOfTeachers = terms.get(Number_Of_Terms-1).getListOfTeachers();
        teacher.addStudent(this , course);
        listOfTeachers.add(teacher);
        Courses_Assigned.add(course);
        Number_Of_Units_Assigned += course.getUnitsPerCourse();
        course.AddStudents(this);
        Number_Of_Courses_Assigned++;
        SCORE score2 = new SCORE(course,teacher.getTeacherid(),Number_Of_Terms);
        score2.setScore(score);
        Ongoing_Term_scores.add(score2);
        terms.get(Number_Of_Terms-1).setCourses_Assigned(Courses_Assigned);
        terms.get(Number_Of_Terms-1).setListOfTeachers(listOfTeachers);
        terms.get(Number_Of_Terms-1).setScores(Ongoing_Term_scores);
    } 

    public void removeCourse(Course course , Teacher teacher){
        Ongoing_Term_scores = terms.get(Number_Of_Terms-1).getScores();
        Courses_Assigned = terms.get(Number_Of_Terms-1).getCourses_Assigned();
        listOfTeachers = terms.get(Number_Of_Terms-1).getListOfTeachers();
        course.RemoveStudents(this);
        if(teacher.getStudentsOfTeacher().contains(this)){
            teacher.removeStudent(this , course);
        }
        Number_Of_Courses_Assigned--;
        Courses_Assigned.remove(course);
        listOfTeachers.remove(teacher);
        terms.get(Number_Of_Terms-1).setCourses_Assigned(Courses_Assigned);
        terms.get(Number_Of_Terms-1).setListOfTeachers(listOfTeachers);
        terms.get(Number_Of_Terms-1).setScores(Ongoing_Term_scores);
    } 
    public ALPHABETSCORE GetAlphabeticalscore(Term term){
        double average = calculateAverageTermScore(term);
        if(average>=17){
            return ALPHABETSCORE.A; 
        }
        else if(average >= 14){
            return ALPHABETSCORE.B ;
        }
        else if(average > 10){
            return ALPHABETSCORE.C;
        }
        else if(average > 7){
            return ALPHABETSCORE.D;
        }
        else if(average>4){
            return ALPHABETSCORE.E;
        }
        return ALPHABETSCORE.F;
    }
    
    public void setNumber_Of_Units_Assigned(int Number_Of_Units_Assigned) {
        this.Number_Of_Units_Assigned = Number_Of_Units_Assigned;
    }
    public int getNumber_Of_Units_Assigned() {
        return Number_Of_Units_Assigned;
    }
    public void setCourses_Assigned(List<Course> Courses_Assigned) {
        this.Courses_Assigned = Courses_Assigned;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public List<Course> getCourses_Assigned() {
        return Courses_Assigned;
    }

    public String getLast_Name(){ 
        return Last_Name;
    }

    public void AddCourse(Course New){
        Courses_Assigned.add(New);
    }

    public String givescore(Course course , Teacher teacher , double score){
        Ongoing_Term_scores = terms.get(Number_Of_Terms-1).getScores();
        for(int i = 0 ; i <Ongoing_Term_scores.size() ;i++){
            if(Ongoing_Term_scores.get(i).getCourse().equals(course)){
                if(teacher.getTeacherid()==Ongoing_Term_scores.get(i).getTeacherid()){
                    Ongoing_Term_scores.get(i).setScore(score);
                    return "DONE !";
                }
            }
        }
        return "ERROR";
    }

    public int getNumber_Of_Terms() {
        return Number_Of_Terms;
    }
    
    public double calculateAverageTermScore(Term term){
        List<SCORE> listofScores = term.getScores();
        int numberofunits = 0 ;
        double sumofscores = 0.0;
        for(int i = 0 ; i <listofScores.size() ; i++){
            numberofunits += listofScores.get(i).getCourse().getUnitsPerCourse();
            sumofscores+= (listofScores.get(i).getCourse().getUnitsPerCourse()*listofScores.get(i).getScore());
        }
        sumofscores = sumofscores/numberofunits;
        return sumofscores;
    }

    public List<Term> getTerms() {
        return terms;
    }
}
enum ALPHABETSCORE {
    A,
    B,
    C,
    D,
    E,
    F
}
class SCORE{
    Course course;
    long teacherid;
    int term ;
    double score;
    SCORE(Course course , long teacherid , int term){
        this.course = course;
        this.teacherid = teacherid;
        this.term = term;
    }
    public Course getCourse() {
        return course;
    }
    public double getScore() {
        return score;
    }
    public long getTeacherid() {
        return teacherid;
    }
    public int getTerm() {
        return term;
    }
    public void setScore(double score) {
        this.score = score;
    }
}
class Term{
    private List<SCORE> scores = new ArrayList<SCORE>();
    private List<Course> Courses_Assigned = new ArrayList<Course>();
    private List<Teacher> listOfTeachers = new ArrayList<Teacher>();

    Term(){}
    public void setScores(List<SCORE> scores) {
        this.scores = scores;
    }
    public void setListOfTeachers(List<Teacher> listOfTeachers) {
        this.listOfTeachers = listOfTeachers;
    }
    public void setCourses_Assigned(List<Course> courses_Assigned) {
        Courses_Assigned = courses_Assigned;
    }
    public List<SCORE> getScores() {
        return scores;
    }
    public List<Course> getCourses_Assigned() {
        return Courses_Assigned;
    }
    public List<Teacher> getListOfTeachers() {
        return listOfTeachers;
    }
}
    