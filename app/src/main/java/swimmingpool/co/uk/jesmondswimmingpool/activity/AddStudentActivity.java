package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class AddStudentActivity extends BaseActivity {

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
    Student student;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ButterKnife.bind(this);
        setTitle("Add Student");
        student=new Student();
        student.setPaid(0);
        student.setCreatedate(new Date());
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
        etName.setError(null);
        etParentNumber.setError(null);
        String address = etAddress.getText().toString();
        String level = etLevel.getText().toString();
        String note = etNote.getText().toString();
        String number = etParentNumber.getText().toString();
        String me = etMedical.getText().toString();
        String name = etName.getText().toString();
        if(TextUtils.isEmpty(name) ){
            etName.setError("name cannot be empty");
            return ;
        }
        if(TextUtils.isEmpty(number) ){
            etName.setError("phone number cannot be empty");
            return ;
        }

        student.setParentphonenumber(number);
        student.setLevel(level);
        student.setName(name);
        student.setAddress(address);
        student.setNote(note);
        student.setMedicalcondition(me);
        final MaterialDialog materialDialog = DialogUtils.showProcessingDialog(this);

        HttpHelper.getInstance().post(UrlConstant.ADD_STUDENT, student, new HttpCallBack<CommonEntity<Student>>() {

            @Override
            public void onSuccess(final CommonEntity<Student> studentCommonEntity) {
                UIUtils.showToastSafe(AddStudentActivity.this,studentCommonEntity.getMsg());
                etAddress.setText("");
                etLevel.setText("");
                etNote.setText("");
                etParentNumber.setText("");
                etMedical.setText("");
                etName.setText("");
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(AddStudentActivity.this,message+code);

            }
            @Override
            public void after() {
                materialDialog.dismiss();
            }
        });
    }
}
