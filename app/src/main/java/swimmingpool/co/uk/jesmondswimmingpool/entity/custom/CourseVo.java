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
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	private PageBean pageBean;


	public PageBean getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	private Course course;
	private List<Courseitem> courseitems;
	public List<Courseitem> getCourseitems() {
		return courseitems;
	}
	private Boolean includingChose;
	public Boolean getIncludingFishished() {
		return includingFishished;
	}
	public Boolean isIncludingChose() {
		return includingChose;
	}
	public Boolean getIncludingChose() {
		return includingChose;
	}
	public void setIncludingChose(Boolean includingChose) {
		this.includingChose = includingChose;
	}

	private Boolean includingFishished;
	public Boolean isIncludingFishished() {
		return includingFishished;
	}
	public void setIncludingFishished(Boolean includingFishished) {
		this.includingFishished = includingFishished;
	}
	public void setCourseitems(List<Courseitem> courseitems) {
		this.courseitems = courseitems;
	}
	private Date day;
	private List<Course> courseList;
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
	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;

	}
	public List<Course> getCourseList() {
		return courseList;
	}



}
