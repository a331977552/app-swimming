package swimmingpool.co.uk.jesmondswimmingpool.entity.custom;

import java.util.List;

import swimmingpool.co.uk.jesmondswimmingpool.entity.Achievement;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Attendance;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Courseitem;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Finishstatus;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;

public class StudentVo {
	Attendance attendance;
	public Attendance getAttendance() {
		return attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}

	private List<Achievement> achievements;

	public List<Courseitem> getCourseitems() {
		return courseitems;
	}

	public void setCourseitems(List<Courseitem> courseitems) {
		this.courseitems = courseitems;
	}

	private List<Courseitem> courseitems;

	public List<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(List<Achievement> achievements) {
		this.achievements = achievements;
	}

	Student student;
	List<Course> courses;
	List<Finishstatus> finishstatus;
	public List<Finishstatus> getFinishstatus() {
		return finishstatus;
	}

	public void setFinishstatus(List<Finishstatus> finishstatus) {
		this.finishstatus = finishstatus;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	Boolean getFinished;



	public Boolean getGetFinished() {
		return getFinished;
	}

	public void setGetFinished(Boolean getFinished) {
		this.getFinished = getFinished;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}



}
