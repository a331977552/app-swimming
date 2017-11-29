package swimmingpool.co.uk.jesmondswimmingpool.entity.event;

import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;

/**
 * Created by cody on 2017/11/29.
 */

public class UpdateStudentEvent {
    private Student bean;

    public Student getBean() {
        return bean;
    }

    public void setBean(Student bean) {
        this.bean = bean;
    }

    public UpdateStudentEvent(Student bean) {
        this.bean = bean;
    }
}
