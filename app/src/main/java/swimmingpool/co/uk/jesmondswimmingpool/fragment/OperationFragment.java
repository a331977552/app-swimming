package swimmingpool.co.uk.jesmondswimmingpool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AddCourseActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AddStudentActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AttendanceListActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.DeleteCourseActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.EditStudentActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.LoginActivity;
import swimmingpool.co.uk.jesmondswimmingpool.utils.SpUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UserManager;

/**
 * Created by cody on 2017/11/14.
 */

public class OperationFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.btn_loginOut)
    Button btnLoginOut;
    @BindView(R.id.tv_tutorName)
    TextView tvTutorName;
    @BindView(R.id.tv_tutorEmail)
    TextView tvTutorEmail;
    @BindView(R.id.ll_addcourse)
    LinearLayoutCompat llAddcourse;
    @BindView(R.id.ll_deleteCourse)
    LinearLayoutCompat llDeleteCourse;
    @BindView(R.id.ll_addStudent)
    LinearLayoutCompat llAddStudent;
    @BindView(R.id.ll_editStudent)
    LinearLayoutCompat llEditStudent;

    Unbinder unbinder;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvTutorName.setText("welcome manager");
//        tvTutorEmail.setText("Email:"+UserManager.getInstance().getPhonenumber());
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_loginOut, R.id.ll_addcourse, R.id.ll_deleteCourse, R.id.ll_addStudent, R.id.ll_editStudent})
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
                startActivity(intent);
                break;
            case R.id.ll_addStudent:
                intent.setClass(getActivity(), AddStudentActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_editStudent:
                intent.setClass(getActivity(), EditStudentActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_attendance_record:
                intent.setClass(getActivity(), AttendanceListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_loginOut:
                onViewClicked();
                break;
    /*        case R.id.ll_addTutor:
                intent.setClass(getActivity(), AddTutorActivity.class);*/
        }

    }


    public void onViewClicked() {
        MaterialDialog alertDialog = new MaterialDialog.Builder(getActivity()).content("Sure to login out?").title("Reminder").positiveText("YES").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
                SpUtils.put("user", "");
                UserManager.getInstance().clean();
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();
            }
        }).negativeText("CANCEL").onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        }).build();

        alertDialog.show();

    }

}
