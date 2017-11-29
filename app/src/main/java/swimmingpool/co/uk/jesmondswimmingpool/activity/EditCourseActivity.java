package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.UpdateCourseEvent;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.AppBus;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/29.
 */

public class EditCourseActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_tutor)
    EditText etTutor;
    @BindView(R.id.tv_start)
    AppCompatTextView tvStart;
    @BindView(R.id.tv_end)
    AppCompatTextView tvEnd;
    @BindView(R.id.et_Note)
    EditText etNote;
    private Course course;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        ButterKnife.bind(this);
        setTitle("Edit");
        course = (Course) getIntent().getSerializableExtra("course");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String start = simpleDateFormat.format(course.getStartDate());
        String end = simpleDateFormat.format(course.getEndDate());
        tvStart.setText(start);
        tvEnd.setText(end);
        etName.setText(course.getName());
        etTutor.setText(course.getTutorname());
        etNote.setText(course.getNote());
    }

    @OnClick({R.id.tv_start, R.id.tv_end,R.id.btn_confirm})
    public void onViewClicked(View view) {
        Calendar instance = Calendar.getInstance();

        switch (view.getId()) {
            case R.id.tv_start:
                instance.setTime(course.getStartDate());
                showDatePicker(view,instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),instance.get(Calendar.DAY_OF_MONTH));
                break;
            case R.id.tv_end:
                instance.setTime(course.getEndDate());
                showDatePicker(view,instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),instance.get(Calendar.DAY_OF_MONTH));
                break;

            case R.id.btn_confirm:
                submit();
                break;

        }
    }

    private void submit() {
        CharSequence start = tvStart.getText();
        CharSequence end = tvEnd.getText();
        String name = etName.getText().toString();
        String node = etNote.getText().toString();
        String tutor = etTutor.getText().toString();
        Course course=new Course();
        course.setId(this.course.getId());
        try {
            Date starD = simpleDateFormat.parse(start.toString());
            Date endD = simpleDateFormat.parse(end.toString());
            course.setEndDate(endD);
            course.setStartDate(starD);
        } catch (ParseException e) {
            e.printStackTrace();
            UIUtils.showToastSafe(this,"error");
            return ;
        }
        course.setName(name);
        course.setNote(node);
        course.setTutorname(tutor);
        final MaterialDialog materialDialog = DialogUtils.showProcessingDialog(this);
        HttpHelper.getInstance().update(UrlConstant.UPDATE_COURSE, course, new HttpCallBack<CommonEntity<Course>>() {


            @Override
            public void onSuccess(final CommonEntity<Course> courseCommonEntity) {
                AppBus.getInstance().post(new UpdateCourseEvent(courseCommonEntity.getBean()));
                    UIUtils.showToastSafe(EditCourseActivity.this,courseCommonEntity.getMsg());
                    UIUtils.postDelayed(new Runnable() {
                        @Override
                        public void run(){
                            finish();
                        }
                    },500);
            }
            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(EditCourseActivity.this,message+code);
            }

            @Override
            public void after() {
                materialDialog.dismiss();
            }
        });




    }

    public void showDatePicker(final View view, int year, int month, int day){
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                TextView textView= (TextView) view;
                Calendar instance = Calendar.getInstance();
                instance.set(i,i1,i2);
                String format = simpleDateFormat.format(instance.getTime());
                textView.setText(format);
            }
        }, year, month, day);

        datePickerDialog.show();
    }
}
