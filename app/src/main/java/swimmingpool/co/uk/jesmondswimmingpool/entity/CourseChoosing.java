package swimmingpool.co.uk.jesmondswimmingpool.entity;

public class CourseChoosing {
    private Long id;

    private Long studentid;

    private Long courseid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    @Override
	public String toString() {
		return "CourseChoosing [id=" + id + ", studentid=" + studentid + ", courseid=" + courseid + "]";
	}

	public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }
}