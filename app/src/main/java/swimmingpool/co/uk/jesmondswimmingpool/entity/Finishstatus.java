package swimmingpool.co.uk.jesmondswimmingpool.entity;

import java.io.Serializable;

public class Finishstatus implements Serializable{
    private Long studentid;

    private Long courseid;

    private Integer status;

    public Long getStudentid() {
        return studentid;
    }

    public void setStudentid(Long studentid) {
        this.studentid = studentid;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}