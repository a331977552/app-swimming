package swimmingpool.co.uk.jesmondswimmingpool.activity;

import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;

/**
 * Created by cody on 2017/11/30.
 */

class CourseChooseEvent {
    private Course tag;

    public Course getTag() {
        return tag;
    }

    public void setTag(Course tag) {
        this.tag = tag;
    }

    public CourseChooseEvent(Course tag) {
        this.tag = tag;
    }
}
