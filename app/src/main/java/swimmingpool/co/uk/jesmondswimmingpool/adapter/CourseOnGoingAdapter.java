package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.AttendanceListActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.CourseListActivity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Finishstatus;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentCourse;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.LogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/15.
 */

public class CourseOnGoingAdapter extends DefaultAdpater<StudentCourse> {

    public CourseOnGoingAdapter(Activity activity, List<StudentCourse> bean) {
        super(activity, bean);
    }

    @Override
    protected BaseHolder getHolder() {
        return new CourseOnGoingHolder(getActivity());
    }

    public static class CourseOnGoingHolder extends BaseHolder<StudentCourse> {


        @BindView(R.id.tv_coursename)
        AppCompatTextView tvCoursename;
        @BindView(R.id.tv_change)
        AppCompatTextView tvChange;
        @BindView(R.id.tv_finish)
        AppCompatTextView tv_finish;
        @BindView(R.id.tv_attendance)
        AppCompatTextView tv_attendance;
        MaterialDialog materialDialog;
        private MaterialDialog processing;
        public CourseOnGoingHolder(Activity activity) {
            super(activity);
            ButterKnife.bind(this, getRootView());


        }

        @Override
        public int initViewId() {
            return R.layout.item_ongoing_course;
        }

        @Override
        protected void initData(final StudentCourse data) {
            LogUtils.i(data.toString());
            tvCoursename.setText(data.getCourse().getName());
            if(data.getStatus()==null){
                tv_finish.setTag(data.getCourse());
                tv_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(materialDialog==null){


                        materialDialog =new MaterialDialog.Builder(getActivity()).title("reminder").
                                content("Do you really want to finish this course for student "+data.getStudent().getName()+"?").
                                positiveText("YES").negativeText("NO").onPositive(new MaterialDialog.SingleButtonCallback() {



                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                processing = new MaterialDialog.Builder(getActivity())
                                        .title("processing")
                                        .content(R.string.please_wait)
                                        .progress(true, 0).cancelable(false)
                                        .show();
                                Finishstatus status =new Finishstatus();
                                status.setCourseid(getData().getCourse().getId());
                                status.setStudentid(getData().getStudent().getId());
                                HttpHelper.getInstance().post(UrlConstant.FINISH_COURSE,status, new HttpCallBack<CommonEntity<Object>>() {


                                    @Override
                                    public void onSuccess(CommonEntity<Object> objectCommonEntity) {
                                        tv_finish.setOnClickListener(null);
                                        tv_finish.setText("finished");
                                        tv_finish.setBackgroundResource(R.color.notice_time);
                                        UIUtils.showToastSafe(getActivity(),objectCommonEntity.getMsg());
                                    }

                                    @Override
                                    public void onFailure(String message, int code) {

                                     UIUtils.showToastSafe(getActivity(),message+":"+code);
                                    }

                                    @Override
                                    public void after() {
                                        processing.dismiss();
                                    }
                                });


                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
                            }
                        }).build();
                        }
                        materialDialog.show();
                    }
                });
            }else{
                tv_finish.setText("finished");
                tv_finish.setBackgroundResource(R.color.notice_time);
            }
            tv_attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(getActivity(),AttendanceListActivity.class);
                    StudentCourse tag = (StudentCourse) tvChange.getTag();
                    intent.putExtra("course",tag);
                    getActivity().startActivity(intent);


                }
            });
            tvChange.setTag(data);

                tvChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Intent intent=new Intent(getActivity(),CourseListActivity.class);
                        StudentCourse tag = (StudentCourse) tvChange.getTag();
                        intent.putExtra("course",tag);
                        getActivity().startActivity(intent);
                    }

                });

        }
    }
}
