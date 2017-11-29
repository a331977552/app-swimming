package swimmingpool.co.uk.jesmondswimmingpool.entity.custom;

import java.util.Date;
import java.util.List;

import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Courseitem;

public class CourseVo {
	private Long studentId;
	public Long getStudentId() {
		return studentId;
	}
	private Boolean includingChose;
	public Boolean getIncludingChose() {
		return includingChose;
	}
	public void setIncludingChose(Boolean includingChose) {
		this.includingChose = includingChose;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	private Boolean includingFishished;
	public Boolean isIncludingFishished() {
		return includingFishished;
	}
	public void setIncludingFishished(Boolean includingFishished) {
		this.includingFishished = includingFishished;
	}
	private Course course;
	private List<Courseitem> courseitems;
	public List<Courseitem> getCourseitems() {
		return courseitems;
	}
	public void setCourseitems(List<Courseitem> courseitems) {
		this.courseitems = courseitems;
	}
	private Date day;
	public Course getCourse() {
		return course;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
