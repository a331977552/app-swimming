package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Attendance;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentCourse;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class AttendanceListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.login_progress)
    LinearLayout loginProgress;
    @BindView(R.id.login_retry)
    LinearLayout loginRetry;
    @BindView(R.id.cv)
    MaterialCalendarView cv;
    @BindView(R.id.tv_reminder)
    TextView tvReminder;

    private StudentCourse course;

    private List<Attendance> bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);
        course = (StudentCourse) getIntent().getSerializableExtra("course");
        setTitle("attendance");
        ButterKnife.bind(this);
        loginRetry.setOnClickListener(this);
        cv.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        tvReminder.setText("Student "+course.getStudent().getName()+"'s Attendance for Course "+course.getCourse().getName() );
        load();

    }

    private void load() {
        Attendance attendance = new Attendance();
        attendance.setCourseid(course.getCourse().getId());
        attendance.setStudentid(course.getStudent().getId());
        HttpHelper.getInstance().post(UrlConstant.ATTENDANCE, attendance, new HttpCallBack<CommonEntity<List<Attendance>>>() {

            @Override
            public void onSuccess(CommonEntity<List<Attendance>> listCommonEntity) {
                loginRetry.setVisibility(View.GONE);
                bean = listCommonEntity.getBean();
                cv.setVisibility(View.VISIBLE);

                cv.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);

                for (Attendance a : bean) {
                    Date recordDate = a.getRecordDate();
                    
                    cv.setDateSelected(recordDate, true);

                }
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(AttendanceListActivity.this, message + code);
                loginRetry.setVisibility(View.VISIBLE);
            }

            @Override
            public void after() {
                loginProgress.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onClick(View view) {
        loginProgress.setVisibility(View.VISIBLE);
        loginRetry.setVisibility(View.GONE);
        load();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
