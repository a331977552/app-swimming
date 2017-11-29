package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;

import java.util.List;

import butterknife.BindView;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.CourseListActivity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;

/**
 * Created by cody on 2017/11/28.
 */

public class CourseListAdapter extends DefaultAdpater<Course> {
    public CourseListAdapter(CourseListActivity courseListActivity, List<Course> bean) {
        super(courseListActivity, bean);
    }

    @Override
    protected BaseHolder getHolder() {
        return new CourseHolder(getActivity());
    }

    public class CourseHolder extends BaseHolder<Course> {

        @BindView(R.id.tv_title)
        AppCompatTextView tvTitle;
        @BindView(R.id.tv_node)
        AppCompatTextView tvNode;
        @BindView(R.id.tv_tutorName)
        AppCompatTextView tv_tutorName;

        public CourseHolder(Activity activity) {
            super(activity);
        }

        @Override
        public int initViewId() {
            return R.layout.item_course;
        }

        @Override
        protected void initData(Course course) {
            tvTitle.setText(course.getName());
            tvNode.setText(course.getNote());
            tv_tutorName.setText("tutor: " +course.getTutorname());
        }
    }
}
