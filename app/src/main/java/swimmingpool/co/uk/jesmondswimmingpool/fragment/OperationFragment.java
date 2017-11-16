package swimmingpool.co.uk.jesmondswimmingpool.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AddCourseActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AddStudentActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AttendanceActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.DeleteCourseActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.EditStudentActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.LoginActivity;
import swimmingpool.co.uk.jesmondswimmingpool.utils.SpUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UserManager;

/**
 * Created by cody on 2017/11/14.
 */

public class OperationFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_addcourse)
    LinearLayoutCompat llAddcourse;
    ;
    @BindView(R.id.ll_deleteCourse)
    LinearLayoutCompat llDeleteCourse;
    @BindView(R.id.ll_addStudent)
    LinearLayoutCompat llAddStudent;
    @BindView(R.id.ll_editStudent)
    LinearLayoutCompat llEditStudent;
    @BindView(R.id.ll_attendance_record)
    LinearLayoutCompat llAttenanceRecord;
    Unbinder unbinder;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.btn_loginOut)
    Button btnLoginOut;
    @BindView(R.id.tv_tutorName)
    TextView tvTutorName;
    @BindView(R.id.tv_tutorEmail)
    TextView tvTutorEmail;
    @BindView(R.id.ll_addTutor)
    LinearLayoutCompat ll_addTutor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation, container, false);
        unbinder = ButterKnife.bind(this, view);
        llAddcourse.setOnClickListener(this);
        llDeleteCourse.setOnClickListener(this);
        llAddStudent.setOnClickListener(this);
        llEditStudent.setOnClickListener(this);
        llAttenanceRecord.setOnClickListener(this);
        ll_addTutor.setOnClickListener(this);
        tvTutorName.setText("Name:"+UserManager.getInstance().getName());

        tvTutorEmail.setText("Email:"+UserManager.getInstance().getPhonenumber());
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_addcourse:
                intent.setClass(getActivity(), AddCourseActivity.class);
                break;
            case R.id.ll_deleteCourse:
                intent.setClass(getActivity(), DeleteCourseActivity.class);
//                intent.setClass(getActivity(),Main2Activity.class);
                break;
            case R.id.ll_addStudent:
                intent.setClass(getActivity(), AddStudentActivity.class);
                break;
            case R.id.ll_editStudent:
                intent.setClass(getActivity(), EditStudentActivity.class);
                break;
            case R.id.ll_attendance_record:
                intent.setClass(getActivity(), AttendanceActivity.class);
                break;
            case R.id.ll_addTutor:
                intent.setClass(getActivity(), AddTutorActivity.class);

                break;
        }
        startActivity(intent);
    }

    @OnClick(R.id.btn_loginOut)
    public void onViewClicked() {
        AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setMessage("sure to login out?").setTitle("confirm").setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                SpUtils.put("user","");
                UserManager.getInstance().clean();
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        alertDialog.show();

    }
}
