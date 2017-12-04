package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;

import java.util.List;

import butterknife.BindView;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;

/**
 * Created by cody on 2017/11/28.
 */

public class CourseListAdapter extends DefaultAdpater<Course> {
    public CourseListAdapter(Activity courseListActivity, List<Course> bean) {
        super(courseListActivity, bean);
    }

    @Override
    protected BaseHolder getHolder() {
        return new CourseHolder(getActivity());
    }

    public class CourseHolder extends BaseHolder<Course> {

      /*  @BindView(R.id.tv_title2)
        AppCompatTextView tvTitle;
        @BindView(R.id.tv_note2)
        AppCompatTextView tvNode;
        @BindView(R.id.tv_tutorName2)
        AppCompatTextView tv_tutorName;*/
        @BindView(R.id.tv_name)
        AppCompatTextView tvName;
        @BindView(R.id.tv_id)
        AppCompatTextView tvId;
        @BindView(R.id.tv_tutorName)
        AppCompatTextView tvTutorName;

        public CourseHolder(Activity activity) {
            super(activity);
        }

        @Override
        public int initViewId() {
            return R.layout.item_attendance;
        }

        @Override
        protected void initData(Course course) {
            tvName.setText(course.getName());
            tvTutorName.setText(course.getNote());
            tvId.setText("tutor: " + course.getTutorname());
        }
    }
}
