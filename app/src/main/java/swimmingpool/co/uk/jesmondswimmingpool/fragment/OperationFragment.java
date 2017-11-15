package swimmingpool.co.uk.jesmondswimmingpool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AddCourseActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AddStudentActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AttendanceCActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.DeleteCourseActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.EditStudentActivity;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

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
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.ll_addcourse:
                UIUtils.showToastSafe(getActivity(), "ll_addcourse");
                intent.setClass(getActivity(), AddCourseActivity.class);

                break;
            case R.id.ll_deleteCourse:
                UIUtils.showToastSafe(getActivity(), "ll_deleteCourse");
                intent.setClass(getActivity(), DeleteCourseActivity.class);
//                intent.setClass(getActivity(),Main2Activity.class);
                break;
            case R.id.ll_addStudent:
                UIUtils.showToastSafe(getActivity(), "ll_addStudent");
                intent.setClass(getActivity(), AddStudentActivity.class);
                break;
            case R.id.ll_editStudent:
                UIUtils.showToastSafe(getActivity(), "ll_editStudent");
                intent.setClass(getActivity(), EditStudentActivity.class);
                break;
            case R.id.ll_attendance_record:
                intent.setClass(getActivity(), AttendanceCActivity.class);
                UIUtils.showToastSafe(getActivity(), "ll_attenance_record");
                break;
        }
        startActivity(intent);
    }
}
