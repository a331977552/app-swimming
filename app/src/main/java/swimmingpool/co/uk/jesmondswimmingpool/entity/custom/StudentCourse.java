package swimmingpool.co.uk.jesmondswimmingpool.entity.custom;

import java.io.Serializable;

import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Finishstatus;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;

/**
 * Created by cody on 2017/11/27.
 */

public class StudentCourse implements Serializable{
    Course course;
    Finishstatus status;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Finishstatus getStatus() {
        return status;
    }

    public void setStatus(Finishstatus status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    Student student;

    @Override
    public String toString() {
        return "StudentCourse{" +
                "course=" + course +
                ", status=" + status +
                ", student=" + student +
                '}';
    }
}
