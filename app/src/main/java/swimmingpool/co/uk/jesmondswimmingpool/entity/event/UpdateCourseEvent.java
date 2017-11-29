package swimmingpool.co.uk.jesmondswimmingpool.entity.event;

import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;

/**
 * Created by cody on 2017/11/29.
 */

public class UpdateCourseEvent {
    public Course getBean() {
        return bean;
    }

    public void setBean(Course bean) {
        this.bean = bean;
    }

    private Course bean;

    public UpdateCourseEvent(Course bean) {
        this.bean = bean;
    }
}
