package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;

/**
 * Created by cody on 2017/11/15.
 */

public class StudentAdapter extends BaseAdapter<Student> {


    public StudentAdapter(Activity appCompatActivity, List<Student> list) {
        super(appCompatActivity, list);
    }

    @Override
    public RecycleBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StudentHolder studentHolder = new StudentHolder(LayoutInflater.from(getAppCompatActivity()).inflate(R.layout.item_home_student, parent, false));
        return studentHolder;
    }


    public class StudentHolder extends RecycleBaseHolder<Student> {
        @BindView(R.id.tv_name)
        AppCompatTextView tvName;
        @BindView(R.id.tv_level)
        AppCompatTextView tvLevel;
        @BindView(R.id.tv_paid)
        AppCompatTextView tvPaid;
        @BindView(R.id.tv_parentPhoneNumber)
        AppCompatTextView tvParentPhoneNumber;
        @BindView(R.id.tv_enrolmentDate)
        AppCompatTextView tvEnrolmentDate;
        public StudentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void setData(Student student) {

            tvName.setText("Name: "+student.getName());
            tvLevel.setText("Level: "+student.getLevel());
            tvPaid.setText("Paid: " + (student.getPaid()==null?"unknow":student.getPaid()==1?"YES":"NO"));
            tvEnrolmentDate.setText("REG DATE: "+student.getEnrolmentdate());
            tvParentPhoneNumber.setText("Parent's Number: "+student.getParentphonenumber());
        }
    }

}
