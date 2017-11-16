package swimmingpool.co.uk.jesmondswimmingpool.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.BaseActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.LoginActivity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Tutor;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.SpUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/15.
 */

public class AddTutorActivity extends BaseActivity {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_comfirm_password)
    EditText etComfirmPassword;
    @BindView(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private MaterialDialog processing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tutor);
        ButterKnife.bind(this);
        setTitle("Add tutor");

    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        // Reset errors.
        etName.setError(null);
        etAddress.setError(null);
        etPassword.setError(null);
        etUsername.setError(null);
        etPhoneNumber.setError(null);
        etNote.setError(null);
        etComfirmPassword.setError(null);

        // Store values at the time of the login attempt.
        String name = etName.getText().toString();
        String address = etAddress.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String note = etNote.getText().toString();
        String comfirmPassword = etComfirmPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            etName.setError(getString(R.string.error_field_required));
            focusView = etName;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            etUsername.setError(getString(R.string.error_field_required));
            focusView = etUsername;
            cancel = true;
        }
        else if (username.length()<6) {
            etUsername.setError(getString(R.string.error_tooshort_username));
            focusView = etUsername;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
            focusView = etPassword;
            cancel = true;
        }
        else if (password.length()<6) {
            etPassword.setError(getString(R.string.error_tooshort_password));
            focusView = etPassword;
            cancel = true;
        }else if(!password.equals(comfirmPassword))
        {
            etComfirmPassword.setError(getString(R.string.error_different_password));
            focusView = etComfirmPassword;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);/*
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();*/
            Tutor tutor=new Tutor();
            tutor.setUsername(username);
            tutor.setPassword(password);
            tutor.setName(name);
            tutor.setNote(note);
            tutor.setPhonenumber(phoneNumber);
            tutor.setAddress(address);
            HttpHelper.getInstance().post(UrlConstant.ADD_TUTOR, tutor, new HttpCallBack<CommonEntity<Tutor>>() {
                @Override
                public void onSuccess(CommonEntity<Tutor> o) {
                    showProgress(false);
                    UIUtils.showToastSafe(AddTutorActivity.this,o.getMsg());
                    finish();
                }


                @Override
                public void onFailure(String message, int code) {
                    UIUtils.showToastSafe(AddTutorActivity.this,message+":" +code);
                    showProgress(false);
                }

                @Override
                public void after() {

                }
            });


        }

    }

    private void showProgress(boolean b) {

        if(processing==null){
            processing = new MaterialDialog.Builder(this)
                    .title("processing")
                    .content(R.string.please_wait)
                    .progress(true, 0).progressIndeterminateStyle(true).build();
        }
        if(b&&!processing.isShowing()){
            processing.show();
        }else if(!b&&processing.isShowing()){
                processing.dismiss();
        }
    }

}
