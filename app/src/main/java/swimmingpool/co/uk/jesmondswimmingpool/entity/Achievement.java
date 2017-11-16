package swimmingpool.co.uk.jesmondswimmingpool.entity;

public class Achievement {
    private Long id;

    private Long courseitemid;

    private Long studentid;

    private String achievement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseitemid() {
        return courseitemid;
    }

    public void setCourseitemid(Long courseitemid) {
        this.courseitemid = courseitemid;
    }

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement == null ? null : achievement.trim();
    }
}