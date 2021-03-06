package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Achievement;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Attendance;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Courseitem;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentVo;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/15.
 */

public class CourseStudentAdapter extends DefaultAdpater<StudentVo> {


    private List<Courseitem> ahievements;
    private final Long courseId;
    private List<String> aciTitle;


    public CourseStudentAdapter(List<Courseitem> ahievements, Long courseId, Activity appCompatActivity, List<StudentVo> list) {
        super(appCompatActivity, list);
        this.ahievements = ahievements;
        this.courseId = courseId;

    }

    public void setAchievement(List<Courseitem> ahievements) {
        this.ahievements = ahievements;

    }


    @Override
    protected BaseHolder getHolder() {
        return new StudentHolder(getActivity());
    }


    public class StudentHolder extends BaseHolder<StudentVo> {
        @BindView(R.id.tv_name)
        AppCompatTextView tvName;
        @BindView(R.id.tv_level)
        AppCompatTextView tvLevel;
        @BindView(R.id.tv_paid)
        AppCompatTextView tvPaid;
        @BindView(R.id.tv_parentPhoneNumber)
        AppCompatTextView tvParentPhoneNumber;

        @BindView(R.id.pb)
        ProgressBar pb;
        @BindView(R.id.tv_attend)
        TextView tv_attend;


        @BindView(R.id.tv_achievement)
        TextView tv_achievement;


        public StudentHolder(Activity activity) {
            super(activity);

        }


        @Override
        public int initViewId() {
            return R.layout.item_course_student;
        }


        @Override
        protected void initData(final StudentVo student) {

            if (student.getAttendance() != null) {
                tv_attend.setText("attended");
                tv_attend.setTextColor(Color.RED);
            } else {
                tv_attend.setText("attended");
            }

            tv_attend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getData().getAttendance() == null) {
                        pb.setVisibility(View.VISIBLE);
                        tv_attend.setVisibility(View.INVISIBLE);
                        Attendance attendance = new Attendance();
                        attendance.setStudentid(getData().getStudent().getId());
                        attendance.setCourseid(courseId);
                        attendance.setRecordDate(new Date());
                        HttpHelper.getInstance().post(UrlConstant.SIGN_IN, attendance, new HttpCallBack<CommonEntity<Attendance>>() {


                            @Override
                            public void onSuccess(CommonEntity<Attendance> attendanceCommonEntity) {
                                tv_attend.setText("Attended");
                                tv_attend.setTextColor(Color.RED);
                                getData().setAttendance(attendanceCommonEntity.getBean());
                            }

                            @Override
                            public void onFailure(String message, int code) {
                                UIUtils.showToastSafe(getActivity(), message + code);
                            }

                            @Override
                            public void after() {
                                tv_attend.setVisibility(View.VISIBLE);
                                pb.setVisibility(View.INVISIBLE);
                            }
                        });
                    }else{

                        DialogUtils.showDialog(getActivity(), "are you sure you want to cancel Sign in?", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                pb.setVisibility(View.VISIBLE);
                                tv_attend.setVisibility(View.INVISIBLE);
                                HttpHelper.getInstance().post(UrlConstant.UNSIGN_IN, student.getAttendance(), new HttpCallBack<CommonEntity<Attendance>>() {
                                    @Override
                                    public void onSuccess(CommonEntity<Attendance> attendanceCommonEntity) {
                                        getData().setAttendance(null);
                                        tv_attend.setText("attend");
                                        tv_attend.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                                    }

                                    @Override
                                    public void onFailure(String message, int code) {
                                        UIUtils.showToastSafe(getActivity(), message + code);
                                    }

                                    @Override
                                    public void after() {
                                        pb.setVisibility(View.INVISIBLE);
                                        tv_attend.setVisibility(View.VISIBLE);
                                    }
                                });

                            }
                        }).show();



                    }

                }
            });


            tvName.setText("Name: " + student.getStudent().getName());
            tvLevel.setText("ID: " + student.getStudent().getId());
            tvPaid.setText("Paid: " + (student.getStudent().getPaid() == null ? "unknow" : student.getStudent().getPaid() == 1 ? "YES" : "NO"));

            tvParentPhoneNumber.setText("Tel: " + student.getStudent().getParentphonenumber());
            aciTitle = new ArrayList<>();
            for (Courseitem a : ahievements) {
                aciTitle.add(a.getName());
            }
            tv_achievement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ahievements != null) {
                        if (ahievements.isEmpty()) {
                            UIUtils.showToastSafe(getActivity(), "this is no achievement for this course");
                        } else {
                            new MaterialDialog.Builder(getActivity()).items(aciTitle).itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                    if(which==-1)
                                    {
                                        UIUtils.showToastSafe(getActivity(),"please tick at least one achievement");
                                        return false;
                                    }
                                    dialog.dismiss();
                                    postAchivement(getData(),which);
                                    return false;
                                }

                            }).positiveText("confirm").negativeText("cancel").show();
                        }
                    }
                }
            });
        }
    }

    /**
     * get student an achievement
     * @param data
     * @param which
     */
    private void postAchivement(StudentVo data, Integer which) {
         final MaterialDialog  processDialog = DialogUtils.processingDialog(getActivity());
        processDialog.show();
        Courseitem courseitem = ahievements.get(which);
        Achievement achievement=new Achievement();
        achievement.setCourseid(courseitem.getCourseid());
        achievement.setCourseitemid(courseitem.getId());
        achievement.setStudentid(data.getStudent().getId());
        achievement.setAchievement(courseitem.getName());

            HttpHelper.getInstance().post(UrlConstant.ACHIEVEMENT, achievement, new HttpCallBack<CommonEntity<Achievement>>() {
                @Override
                public void onSuccess(CommonEntity<Achievement> achievementCommonEntity) {
                            UIUtils.showToastSafe(getActivity(),achievementCommonEntity.getMsg());
                }

                @Override
                public void onFailure(String message, int code) {
                    UIUtils.showToastSafe(getActivity(),message+code);
                }

                @Override
                public void after() {
                    processDialog.dismiss();
                }
            });



    }



}
