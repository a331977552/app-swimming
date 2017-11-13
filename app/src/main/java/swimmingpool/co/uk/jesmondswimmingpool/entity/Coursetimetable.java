package swimmingpool.co.uk.jesmondswimmingpool.entity;

public class Coursetimetable {
    private Long id;

    private Long courseid;

    private String coursedate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public String getCoursedate() {
        return coursedate;
    }

    public void setCoursedate(String coursedate) {
        this.coursedate = coursedate == null ? null : coursedate.trim();
    }
}