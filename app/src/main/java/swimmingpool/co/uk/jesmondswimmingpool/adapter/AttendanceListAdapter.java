package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;

/**
 * Created by cody on 2017/11/28.
 */

public class AttendanceListAdapter extends DefaultAdpater<Course> {
    SimpleDateFormat sdf;
    private final long time;

    public AttendanceListAdapter(Activity attendanceListActivity, List<Course> bean) {
        super(attendanceListActivity, bean);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today=new Date();
        time = today.getTime();
    }

    @Override
    protected BaseHolder getHolder() {
        return new AttendanceHolder(getActivity());
    }

    public class AttendanceHolder extends BaseHolder<Course> {

        @BindView(R.id.tv_name)
        AppCompatTextView tvName;
        @BindView(R.id.tv_tutorName)
        AppCompatTextView tvTutorName;
        @BindView(R.id.tv_id)
        AppCompatTextView tvId;

        public AttendanceHolder(Activity activity) {
            super(activity);
        }

        @Override
        public int initViewId() {
            return R.layout.item_attendance;
        }

        @Override
        protected void initData(Course o) {
            if(o.getStartDate().getTime()<time && o.getEndDate().getTime()>time){
                tvName.setTextColor(getActivity().getResources().getColor(R.color.black));
                tvName.setText("Course: "+o.getName());
            }else{
                tvName.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
                tvName.setText("Course: "+o.getName());
            }
            tvId.setText("ID: "+o.getId());
            tvTutorName.setText("Tutor: " + o.getTutorname());
        }
    }
}
