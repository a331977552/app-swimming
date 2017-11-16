package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;

/**
 * Created by cody on 2017/11/15.
 */

public class CourseOnGoingAdapter extends DefaultAdpater<Course> {
    private View.OnClickListener onChangeCourseClickListener;

    public CourseOnGoingAdapter(Activity activity, List<Course> bean) {
        super(activity, bean);
    }

    @Override
    protected BaseHolder getHolder() {
        return new CourseOnGoingHolder(getActivity());
    }

    public void setOnChangeCourseClickListener(View.OnClickListener onChangeCourseClickListener){

        this.onChangeCourseClickListener = onChangeCourseClickListener;
    }
    public class CourseOnGoingHolder extends BaseHolder<Course> {


        @BindView(R.id.tv_coursename)
        AppCompatTextView tvCoursename;
        @BindView(R.id.tv_change)
        AppCompatTextView tvChange;

        public CourseOnGoingHolder(Activity activity) {
            super(activity);
            ButterKnife.bind(this, getRootView());


        }

        @Override
        public int initViewId() {
            return R.layout.item_ongoing_course;
        }

        @Override
        protected void initData(Course data) {
            tvCoursename.setText(data.getName());
            tvChange.setTag(data);
            if(onChangeCourseClickListener!=null){
                tvChange.setOnClickListener(onChangeCourseClickListener);
            }
        }
    }
}
