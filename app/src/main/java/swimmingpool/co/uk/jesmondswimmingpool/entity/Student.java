package swimmingpool.co.uk.jesmondswimmingpool.entity;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable{
    private Long id;

    private String name;

    private String level;

    private String startdate;

    private Integer max;

    private Integer min;

    private Integer current;

    private String weeks;

    private String address;

    private String phonenumber;

    private String parentphonenumber;

    private String parentname;

    private String medicalcondition;

    private Integer paid;

    private String note;

    private Date createdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks == null ? null : weeks.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public String getParentphonenumber() {
        return parentphonenumber;
    }

    public void setParentphonenumber(String parentphonenumber) {
        this.parentphonenumber = parentphonenumber == null ? null : parentphonenumber.trim();
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname == null ? null : parentname.trim();
    }

    public String getMedicalcondition() {
        return medicalcondition;
    }

    public void setMedicalcondition(String medicalcondition) {
        this.medicalcondition = medicalcondition == null ? null : medicalcondition.trim();
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}