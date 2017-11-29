package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.CourseOnGoingAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Achievement;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Finishstatus;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentCourse;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentVo;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.CourseChangeEvent;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.UpdateStudentEvent;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.AppBus;
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

    @BindView(R.id.ll_courses)
    LinearLayoutCompat ll_courses;


    @BindView(R.id.tf_flowlayout)
    TagFlowLayout tfFlowlayout;
    @BindView(R.id.btn_edit)
    Button btnEditCourse;
    @BindView(R.id.ll_content)
    LinearLayoutCompat llContent;
    @BindView(R.id.onGoingCourse)
    TextView onGoingCourse;
    @BindView(R.id.achievement)
    TextView achievement;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        ButterKnife.bind(this);
        AppBus.getInstance().register(this);
        student = (Student) getIntent().getSerializableExtra("student");
        tvName.setText(student.getName());
        tvAddress.setText(student.getAddress());
        tvParentPhoneNumber.setText(student.getParentphonenumber());
        tvMedical.setText(student.getMedicalcondition());
        tvLevel.setText(student.getLevel());
        tvNote.setText(student.getNote());
        tvPaid.setText(student.getPaid() == null ? "unknown" : student.getPaid() == 1 ? "YES" : "NO");
        getData();

    }


    @Override
    protected void onDestroy() {
        AppBus.getInstance().unregister(this);
        super.onDestroy();
    }

    private void getData() {


        HttpHelper.getInstance().get(UrlConstant.GET_ACHIEVEMENT + "/" + student.getId(), new HttpCallBack<CommonEntity<List<Achievement>>>() {
            @Override
            public void onSuccess(CommonEntity<List<Achievement>> listCommonEntity) {

                tfFlowlayout.setAdapter(new TagAdapter<Achievement>(listCommonEntity.getBean()) {

                    @Override
                    public View getView(FlowLayout parent, int position, Achievement achievement) {
                        CardView inflate = (CardView) LayoutInflater.from(StudentDetailActivity.this).inflate(R.layout.item_achievement, null);
                        TextView textView = inflate.findViewById(R.id.tv_ach);
                        ColorM colorM = randomColor();

                        inflate.setBackgroundColor(colorM.color);
                        textView.setText(achievement.getAchievement());
                        return inflate;
                    }
                });
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(StudentDetailActivity.this, message + ": " + code);
            }

            @Override
            public void after() {

            }
        });


        getCourseByStudent();


    }

    private void getCourseByStudent() {
        HttpHelper.getInstance().get(UrlConstant.GET_COURSE_BY_STUDENT + student.getId(), new HttpCallBack<CommonEntity<StudentVo>>() {
            @Override
            public void onSuccess(CommonEntity<StudentVo> listCommonEntity) {
                ll_courses.removeAllViews();
                for (Course c : listCommonEntity.getBean().getCourses()) {
                    StudentCourse studentCourse = new StudentCourse();
                    long id = c.getId().longValue();
                    for (Finishstatus f : listCommonEntity.getBean().getFinishstatus()) {

                        if (id == f.getCourseid().longValue() && student.getId().longValue() == f.getStudentid().longValue()) {
                            studentCourse.setStatus(f);

                        }
                    }
                    studentCourse.setCourse(c);
                    studentCourse.setStudent(student);
                    CourseOnGoingAdapter.CourseOnGoingHolder courseOnGoingHolder = new CourseOnGoingAdapter.CourseOnGoingHolder(StudentDetailActivity.this);
                    courseOnGoingHolder.setData(studentCourse);
                    ll_courses.addView(courseOnGoingHolder.getRootView());
                }
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(StudentDetailActivity.this, message + ": " + code);
            }

            @Override
            public void after() {

            }
        });
    }

    public ColorM randomColor() {
        Random random = new Random();
        String r, g, b, rr, gg, bb;
        int a = random.nextInt(256);
        int b1 = random.nextInt(256);
        int c = random.nextInt(256);
        r = Integer.toHexString(a).toUpperCase();
        g = Integer.toHexString(b1).toUpperCase();
        b = Integer.toHexString(c).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        ColorM colorM = new ColorM();
        colorM.color = Color.parseColor("#" + r + g + b);


        return colorM;
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onChosenCourseChange(CourseChangeEvent courseChangeEvent) {
        getCourseByStudent();

    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onStudentChange(UpdateStudentEvent courseChangeEvent) {
        student=courseChangeEvent.getBean();
        tvPaid.setText(student.getPaid()==0?"NO":"YES");
        tvNote.setText(student.getNote());
        tvName.setText(student.getName());
        tvMedical.setText(student.getMedicalcondition());
        tvAddress.setText(student.getAddress());
        tvParentPhoneNumber.setText(student.getParentphonenumber());
        tvLevel.setText(student.getLevel());
    }

    @OnClick(R.id.btn_edit)
    public void onViewClicked() {
        Intent intent=new Intent(this,EditStudentActivity.class);
        intent.putExtra("student",student);
        startActivity(intent);
    }

    public class ColorM {
        int color;
        int reversColor;
    }
}
