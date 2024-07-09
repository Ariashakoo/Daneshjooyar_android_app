import java.util.*;
public class Assignment {
    String name ;
    private String deadline;
    private boolean available;
    private String coursename;
    private String teacherid;
    Assignment(String deadline, boolean available , String name ,String coursename , String teacherid) {
        this.deadline = deadline;
        this.available = available;
        this.name = name;
        this.coursename=coursename;
    }
    public String getTeacherid() {
        return teacherid;
    }
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
    public String getCoursename() {
        return coursename;
    }
    Assignment(String deadline, String available , String name){
        this.deadline= deadline;
        this.name=name;
        if(available.equals("true")){
            this.available=true;
        }
        else{
            this.available = false;
        }
    }
    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public String getName() {
        return name;
    }
}

