package swimmingpool.co.uk.jesmondswimmingpool.entity.custom;

import java.util.ArrayList;

import swimmingpool.co.uk.jesmondswimmingpool.entity.Courseitem;

public class CourseItemVo {
	 ArrayList<Courseitem> items;
	 Long courseId;
	public ArrayList<Courseitem> getItems() {
		return items;
	}
	public void setItems(ArrayList<Courseitem> items) {
		this.items = items;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
}
