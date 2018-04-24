package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Courseitem;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.CourseVo;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class AddCourseActivity extends BaseActivity {

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
    @BindView(R.id.ll_content)
    LinearLayoutCompat llContent;
    @BindView(R.id.tv_addAchievement)
    TextView tvAddAchievement;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private SimpleDateFormat simpleDateFormat;
    private List<Courseitem> courseitems=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_course);
        ButterKnife.bind(this);
        setTitle("Add Course");
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tvStart.setText(simpleDateFormat.format(new Date()));
        tvEnd.setText(simpleDateFormat.format(new Date()));

    }

    @OnClick({R.id.tv_start, R.id.tv_end, R.id.tv_addAchievement, R.id.btn_confirm})
    public void onViewClicked(View view) {
        Calendar instance = Calendar.getInstance();

        switch (view.getId()) {
            case R.id.tv_start:
                CharSequence tvStartText = tvStart.getText();
                try {
                    Date parse = simpleDateFormat.parse(tvStartText.toString());
                    instance.setTime(parse);
                    showDatePicker(view,instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),instance.get(Calendar.DAY_OF_MONTH));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_end:
                CharSequence text = tvEnd.getText();
                try {
                    Date parse = simpleDateFormat.parse(text.toString());
                    instance.setTime(parse);
                    showDatePicker(view,instance.get(Calendar.YEAR),instance.get(Calendar.MONTH),instance.get(Calendar.DAY_OF_MONTH));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_addAchievement:
                addAchievementView();
                break;
            case R.id.btn_confirm:
                submit();
                break;
        }
    }

    private void submit() {
        etName.setError(null);
        Course course=new Course();
        String name= etName.getText().toString();
        String note = etNote.getText().toString();
        String tutor= etTutor.getText().toString();
        int childCount = llContent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = llContent.getChildAt(i);
            EditText viewById = childAt.findViewById(R.id.et_name);
            String s = viewById.getText().toString();
            courseitems.get(i).setName(s);
        }
        CharSequence tvStartText = tvStart.getText();
        CharSequence text = tvEnd.getText();
        if(TextUtils.isEmpty(name)){
            etName.setError("name cannot be empty!");
        }
        course.setTutorname(tutor);
        course.setName(name);
        try {
            Date start = simpleDateFormat.parse(tvStartText.toString());
            Date end = simpleDateFormat.parse(text.toString());
            if(start.getTime()>end.getTime()){
                UIUtils.showToastSafe(this,"date incorrect");
                return ;
            }
            course.setStartDate(start);
            course.setEndDate(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        course.setNote(note);
        CourseVo courseVo=new CourseVo();
        courseVo.setCourseitems(courseitems);
        courseVo.setCourse(course);
        final MaterialDialog materialDialog = DialogUtils.showProcessingDialog(this);
        HttpHelper.getInstance().post(UrlConstant.ADD_COURSE, courseVo, new HttpCallBack<CommonEntity<CourseVo>>() {
            @Override
            public void onSuccess(CommonEntity<CourseVo> courseVoCommonEntity) {
                        etName.setText("");
                        etNote.setText("");
                        etTutor.setText("");
                        llContent.removeAllViews();
                        courseitems.clear();

                        UIUtils.showToastSafe(AddCourseActivity.this,courseVoCommonEntity.getMsg());
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(AddCourseActivity.this,message+code);
            }

            @Override
            public void after() {
                materialDialog.dismiss();
            }
        });

    }

    private void addAchievementView() {
        final TextInputLayout inflate = (TextInputLayout) LayoutInflater.from(this).inflate(R.layout.item_input, null);
        final EditText et = inflate.findViewById(R.id.et_name);
        inflate.setHint("");
        et.setHint("Achievement "+(courseitems.size()+1));
        Courseitem items=new Courseitem();
        et.setTag(items);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    EditText editText= (EditText) view;
                    String s = editText.getText().toString();
                    Courseitem tag = (Courseitem) view.getTag();
                    if (TextUtils.isEmpty(s)){
                        courseitems.remove(tag);
                        llContent.removeView(inflate);
                    }

                }
            }
        });
        courseitems.add(items);
        llContent.addView(inflate);

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
