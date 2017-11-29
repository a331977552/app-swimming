package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.UpdateStudentEvent;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.AppBus;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class EditStudentActivity extends BaseActivity {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_parentNumber)
    EditText etParentNumber;
    @BindView(R.id.et_medical)
    EditText etMedical;
    @BindView(R.id.et_level)
    EditText etLevel;
    @BindView(R.id.tv_Paid)
    AppCompatTextView tvPaid;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private Student student;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        ButterKnife.bind(this);

        student = (Student) getIntent().getSerializableExtra("student");
        tvPaid.setText(student.getPaid()==0?"NO":"YES");
        etAddress.setText(student.getAddress());
        etLevel.setText(student.getLevel()+"");
        etNote.setText(student.getNote());
        etParentNumber.setText(student.getParentphonenumber());
        etMedical.setText(student.getMedicalcondition());
        etName.setText(student.getName());

// Set an action when any event is clicked.
//        mWeekView.setOnEventClickListener(mEventClickListener);

// T
    }

    @OnClick({R.id.tv_Paid, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_Paid:
                new MaterialDialog.Builder(this).items(new String[]{"YES","NO"}).itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            if(which==0)
                            {
                                student.setPaid(1);
                            }
                        if(which==1){
                            student.setPaid(0);
                        }
                        tvPaid.setText(which==0?"YES":"NO");
                        return false;
                    }
                }).show();
                break;
            case R.id.btn_confirm:
                submit();
                break;
        }
    }

    private void submit() {
        String address = etAddress.getText().toString();
        String level = etLevel.getText().toString();
        String note = etNote.getText().toString();
        String number = etParentNumber.getText().toString();
        String me = etMedical.getText().toString();
        String name = etName.getText().toString();
        student.setParentphonenumber(number);
        student.setLevel(level);
        student.setName(name);
        student.setAddress(address);
        student.setNote(note);
        student.setMedicalcondition(me);
        final MaterialDialog materialDialog = DialogUtils.showProcessingDialog(this);

        HttpHelper.getInstance().update(UrlConstant.UPDATE_STUDENT, student, new HttpCallBack<CommonEntity<Student>>() {

            @Override
            public void onSuccess(final CommonEntity<Student> studentCommonEntity) {
                UIUtils.showToastSafe(EditStudentActivity.this,studentCommonEntity.getMsg());
                UIUtils.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Student bean = studentCommonEntity.getBean();
                        AppBus.getInstance().post(new UpdateStudentEvent(bean));
                        finish();
                    }
                },500);
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(EditStudentActivity.this,message+code);

            }
            @Override
            public void after() {
                materialDialog.dismiss();
            }
        });
    }

    ;
}
