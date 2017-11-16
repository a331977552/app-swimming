package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.CourseOnGoingAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Achievement;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

public class StudentDetailActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_address)
    AppCompatTextView tvAddress;
    @BindView(R.id.tv_parentPhoneNumber)
    AppCompatTextView tvParentPhoneNumber;
    @BindView(R.id.tv_medical)
    AppCompatTextView tvMedical;
    @BindView(R.id.tv_note)
    AppCompatTextView tvNote;
    @BindView(R.id.tv_registration)
    AppCompatTextView tvRegistration;
    @BindView(R.id.tv_level)
    AppCompatTextView tvLevel;
    @BindView(R.id.tv_paid)
    AppCompatTextView tvPaid;


    @BindView(R.id.list_ongoingCourse)
    ListViewCompat listOngoingCourse;

    @BindView(R.id.tf_flowlayout)
    TagFlowLayout tfFlowlayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        ButterKnife.bind(this);
        student = (Student) getIntent().getSerializableExtra("student");
        tvName.setText(student.getName());
        tvAddress.setText(student.getAddress());
        tvParentPhoneNumber.setText(student.getParentphonenumber());
        tvMedical.setText(student.getMedicalcondition());
        tvLevel.setText(student.getLevel());
        tvNote.setText(student.getNote());
        tvRegistration.setText(student.getEnrolmentdate());
        tvPaid.setText(student.getPaid()==null?"unknown": student.getPaid()==1?"YES":"NO");

        getData();

    }

    private void getData() {
        HttpHelper.getInstance().get(UrlConstant.GET_ACHIEVEMENT + "/" + student.getId(), new HttpCallBack<CommonEntity<List<Achievement>>>() {
            @Override
            public void onSuccess(CommonEntity<List<Achievement>> listCommonEntity) {
                tfFlowlayout.setAdapter(new TagAdapter<Achievement>(listCommonEntity.getBean()) {
                    @Override
                    public View getView(FlowLayout parent, int position, Achievement achievement) {
                        TextView textView=new TextView(parent.getContext());
                        textView.setText(achievement.getAchievement());
                        return textView;
                    }
                });
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(StudentDetailActivity.this,message +": "+code);
            }

            @Override
            public void after() {

            }
        });
        HttpHelper.getInstance().get(UrlConstant.GET_COURSE_BY_STUDENT  + student.getId(), new HttpCallBack<CommonEntity<List<Course>>>() {
            @Override
            public void onSuccess(CommonEntity<List<Course>> listCommonEntity) {
                CourseOnGoingAdapter courseOnGoingAdapter = new CourseOnGoingAdapter(StudentDetailActivity.this, listCommonEntity.getBean());
                courseOnGoingAdapter.setOnChangeCourseClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Course course= (Course) v.getTag();

                    }
                });

                listOngoingCourse.setAdapter(courseOnGoingAdapter);
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(StudentDetailActivity.this,message +": "+code);
            }

            @Override
            public void after() {

            }
        });


    }
}
