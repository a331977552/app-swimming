package swimmingpool.co.uk.jesmondswimmingpool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.CourseDetailsActivity;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.AttendanceListAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.CourseVo;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.UpdateCourseEvent;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.AppBus;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class AttendanceFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @BindView(R.id.list)
    ListViewCompat list;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.login_progress)
    LinearLayout loginProgress;
    @BindView(R.id.login_retry)
    LinearLayout loginRetry;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    private List<Course> allCourses = new ArrayList<>();
    private List<Course> availableCourse = new ArrayList<>();
    private List<Course> dataList = new ArrayList<>();

    private AttendanceListAdapter attendanceListAdapter;
    private Course course;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_attendance, container, false);

        unbinder = ButterKnife.bind(this, inflate);
        AppBus.getInstance().register(this);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        list.setAdapter();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorAccent, R.color.c_07aeed);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();
    }

    private void load() {
        CourseVo courseVo = new CourseVo();

        HttpHelper.getInstance().post(UrlConstant.GET_ALL_COURSE, courseVo, new HttpCallBack<CommonEntity<CourseVo>>() {
            @Override
            public void onSuccess(CommonEntity<CourseVo> listCommonEntity) {
                availableCourse.clear();
                dataList.clear();
                allCourses = listCommonEntity.getBean().getCourseList();
                Date today = new Date();
                long time = today.getTime();
                for (Course c : allCourses) {
                    if (c.getStartDate().getTime() < time && c.getEndDate().getTime() > time) {
                        availableCourse.add(c);
                        dataList.add(c);
                    }
                    if (attendanceListAdapter == null) {
                        attendanceListAdapter = new AttendanceListAdapter(getActivity(), availableCourse);
                        list.setAdapter(attendanceListAdapter);
                        list.setOnItemClickListener(AttendanceFragment.this);
                        list.setOnItemLongClickListener(AttendanceFragment.this);
                    } else {
                        attendanceListAdapter.notifyDataSetChanged();
                    }


                }

            }

            @Override
            public void onFailure(String message, int code) {
                if (attendanceListAdapter == null) {
                    loginRetry.setVisibility(View.VISIBLE);
                }
                UIUtils.showToastSafe(getActivity(), message + code);
            }

            @Override
            public void after() {
                loginProgress.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        AppBus.getInstance().unregister(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), CourseDetailsActivity.class);
        course = availableCourse.get(i);
        intent.putExtra("course", course);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final Long id = availableCourse.get(i).getId();
        DialogUtils.showSingleChoiceDialog(getContext(), new String[]{"Delete Course " + availableCourse.get(i).getName()}, new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(final MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (which == -1) {
                    UIUtils.showToastSafe(getActivity(), "Please Choice one option");
                } else if (which == 0) {
                    HttpHelper.getInstance().remove(UrlConstant.REMOVE_COURSE + id, new HttpCallBack<CommonEntity<Object>>() {
                        @Override
                        public void onSuccess(CommonEntity<Object> objectCommonEntity) {
                            Course remove = availableCourse.remove(i);
                            allCourses.remove(remove);
                            dataList.remove(remove);
                            attendanceListAdapter.notifyDataSetChanged();
                            UIUtils.showToastSafe(getActivity(), objectCommonEntity.getMsg());
                        }

                        @Override
                        public void onFailure(String message, int code) {
                            UIUtils.showToastSafe(getActivity(), message + ":" + code);
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

    @OnClick(R.id.btn_retry)
    public void onViewClicked() {
        loginProgress.setVisibility(View.VISIBLE);
        loginRetry.setVisibility(View.GONE);
        load();

    }

    public void startSearch(String newText) {
        if (attendanceListAdapter == null)
            return;
//        searchResult.clear();

        availableCourse.clear();
        if (TextUtils.isEmpty(newText)) {
            availableCourse.addAll(dataList);
            attendanceListAdapter.notifyDataSetChanged();
            return;
        }
        for (Course s : allCourses) {
            if (s.getName().toLowerCase().trim().contains(newText.toLowerCase().trim())) {
//                searchResult.add(s);
                availableCourse.add(s);
            }
        }
        attendanceListAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onAchievementChanged(UpdateCourseEvent editAchievementEvent){
            if(course!=null){
                course.setNote(editAchievementEvent.getBean().getNote());
                course.setEndDate(editAchievementEvent.getBean().getEndDate());
                course.setStartDate(editAchievementEvent.getBean().getStartDate());
                course.setTutorname(editAchievementEvent.getBean().getTutorname());
                course.setName(editAchievementEvent.getBean().getName());
                attendanceListAdapter.notifyDataSetChanged();
            }

    }
}
