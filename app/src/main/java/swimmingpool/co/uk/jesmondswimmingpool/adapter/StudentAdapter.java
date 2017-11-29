package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;

import java.util.List;

import butterknife.BindView;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;

/**
 * Created by cody on 2017/11/15.
 */

public class StudentAdapter extends DefaultAdpater<Student> {


    private final Long courseId;
    private Integer layoutId;

    public StudentAdapter(Activity appCompatActivity, List<Student> list) {
        super(appCompatActivity, list);
        courseId=null;
    }
    public StudentAdapter(Long courseId,int layoutId,Activity appCompatActivity, List<Student> list) {
        super(appCompatActivity, list);
        this.courseId = courseId;
        this.layoutId = layoutId;
    }

    @Override
    protected BaseHolder getHolder() {
        return new StudentHolder(getActivity());
    }


    public class StudentHolder extends BaseHolder<Student> {
        @BindView(R.id.tv_name)
        AppCompatTextView tvName;
        @BindView(R.id.tv_level)
        AppCompatTextView tvLevel;
        @BindView(R.id.tv_paid)
        AppCompatTextView tvPaid;
        @BindView(R.id.tv_parentPhoneNumber)
        AppCompatTextView tvParentPhoneNumber;

        public StudentHolder(Activity activity) {
            super(activity);

        }


        @Override
        public int initViewId() {
            return R.layout.item_home_student;
        }


        @Override
        protected void initData(Student student) {



            tvName.setText("Name: "+student.getName());
            tvLevel.setText("Level: "+student.getLevel());
            tvPaid.setText("Paid: " + (student.getPaid()==null?"unknow":student.getPaid()==1?"YES":"NO"));

            tvParentPhoneNumber.setText("Tel: "+student.getParentphonenumber());
        }
    }

}
