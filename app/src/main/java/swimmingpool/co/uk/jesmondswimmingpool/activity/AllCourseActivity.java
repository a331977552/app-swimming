package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.AttendanceListAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.CourseVo;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class AllCourseActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Course> bean;
    private AttendanceListAdapter attendanceListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_course);
        ButterKnife.bind(this);
        setTitle("All Courses");
        loading();
    }

    private void loading() {
//        HttpHelper.getInstance().post();
        CourseVo courseVo = new CourseVo();

        HttpHelper.getInstance().post(UrlConstant.GET_ALL_COURSE, courseVo, new HttpCallBack<CommonEntity<CourseVo>>() {

            @Override
            public void onSuccess(CommonEntity<CourseVo> listCommonEntity) {
                if(bean==null){
                    bean = listCommonEntity.getBean().getCourseList();
                    attendanceListAdapter = new AttendanceListAdapter(AllCourseActivity.this, bean);

                    list.setAdapter(attendanceListAdapter);
                    list.setOnItemClickListener(AllCourseActivity.this);
                    list.setOnItemLongClickListener(AllCourseActivity.this);
                }else{
                    bean=listCommonEntity.getBean().getCourseList();
                    list.deferNotifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(AllCourseActivity.this, message + code);
            }

            @Override
            public void after() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loading();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, CourseDetailsActivity.class);
        intent.putExtra("course", bean.get(i));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final Long id = bean.get(i).getId();
        DialogUtils.showSingleChoiceDialog(this, new String[]{"Delete this Course"}, new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(final MaterialDialog dialog, View itemView, int which, CharSequence text) {
                    if(which==-1){
                        UIUtils.showToastSafe(AllCourseActivity.this,"Please Choice one option");
                    }else if(which==0){
                        HttpHelper.getInstance().remove(UrlConstant.REMOVE_COURSE + id, new HttpCallBack<CommonEntity<Object>>() {

                            @Override
                            public void onSuccess(CommonEntity<Object> objectCommonEntity) {
                                        bean.remove(i);
                                        attendanceListAdapter.notifyDataSetChanged();
                                        UIUtils.showToastSafe(AllCourseActivity.this,objectCommonEntity.getMsg());
                            }

                            @Override
                            public void onFailure(String message, int code) {
                                UIUtils.showToastSafe(AllCourseActivity.this,message+":"+code);
                            }

                            @Override
                            public void after() {
                                dialog.dismiss();
                            }
                        });
                    }

                return true;
            }
        });
        return true;
    }
}
