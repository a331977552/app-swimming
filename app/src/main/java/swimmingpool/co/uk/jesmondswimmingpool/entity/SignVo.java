package swimmingpool.co.uk.jesmondswimmingpool.entity;

public class SignVo {

	private Long tutorId;
	private String courseDate;
	
	public Long getTutorId() {
		return tutorId;
	}
	public void setTutorId(Long tutorId) {
		this.tutorId = tutorId;
	}
	public String getCourseDate() {
		return courseDate;
	}
	public void setCourseDate(String courseDate) {
		this.courseDate = courseDate;
	}
	private Student student;
	private Long courseId;
	private Coursetimetable coursetimetable;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Coursetimetable getCoursetimetable() {
		return coursetimetable;
	}
	public void setCoursetimetable(Coursetimetable coursetimetable) {
		this.coursetimetable = coursetimetable;
	}
	
	
	
}
