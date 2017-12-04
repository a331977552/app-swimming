package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.CourseListAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CourseChoosing;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.CourseChangeVo;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.CourseVo;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentCourse;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.CourseChangeEvent;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.AppBus;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.LogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/27.
 */

public class CourseChangeActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.list)
    ListViewCompat list;


    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Course> bean;
    private StudentCourse course;
    private boolean show_finished=false;
    private MaterialDialog processingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
//        show_finished=SpUtils.getBoolean("show_finished",true);
        setTitle("change");
        course = (StudentCourse) getIntent().getSerializableExtra("course");
        ButterKnife.bind(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark);

        load();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.course_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_show_finished){
            show_finished=!show_finished;
            swipeRefreshLayout.setRefreshing(true);
            load();
            item.setTitle(show_finished?"hideFinished":"showFinished");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void load() {
        CourseVo courseVo = new CourseVo();
        courseVo.setStudentId(course.getStudent().getId());

        courseVo.setDay(new Date());
        courseVo.setIncludingFishished(show_finished);
        courseVo.setIncludingChose(false);
        HttpHelper.getInstance().post(UrlConstant.GET_ALL_COURSE, courseVo, new HttpCallBack<CommonEntity<CourseVo>>() {


            @Override
            public void onSuccess(CommonEntity<CourseVo> courseVoCommonEntity) {
                bean = courseVoCommonEntity.getBean().getCourseList();
                list.setAdapter(new CourseListAdapter(CourseChangeActivity.this, bean));
                list.setOnItemClickListener(CourseChangeActivity.this);
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(CourseChangeActivity.this, message + code);
            }

            @Override
            public void after() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Course course = bean.get(i);
        new MaterialDialog.Builder(this).content("sure to change course from "+ this.course.getCourse().getName()+" to "+course.getName() +" for student "+this.course.getStudent().getName()+"?").
                contentColorRes(R.color.black).title("reminder").positiveText("YES").negativeText("NO").onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                processingDialog = DialogUtils.processingDialog(CourseChangeActivity.this);
                dialog.dismiss();
                processingDialog.show();
                change((Course)dialog.getTag());
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    dialog.dismiss();
            }
        }).tag(course).show();


    }

    private void change(Course tag) {
        CourseChangeVo courseChangeVo=new CourseChangeVo();
        courseChangeVo.setStudentId(course.getStudent().getId());
        courseChangeVo.setCourseId(tag.getId());
        courseChangeVo.setPreviousCourseId(course.getCourse().getId());
        LogUtils.i(courseChangeVo.toString());
        HttpHelper.getInstance().post(UrlConstant.CHANGE,courseChangeVo , new HttpCallBack<CommonEntity<CourseChoosing>>() {

            @Override
            public void onSuccess(CommonEntity<CourseChoosing> courseChoosingCommonEntity) {
                        UIUtils.showToastSafe(CourseChangeActivity.this,courseChoosingCommonEntity.getMsg());

                        UIUtils.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                if(processingDialog!=null)
                                    processingDialog.dismiss();
                                AppBus.getInstance().post(new CourseChangeEvent());
                                finish();
                            }
                        },300);

            }

            @Override
            public void onFailure(String message, int code) {
                processingDialog.dismiss();
                UIUtils.showToastSafe(CourseChangeActivity.this,message+ code);
            }

            @Override
            public void after() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        load();
    }
}
