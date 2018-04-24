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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.StudentDetailActivity;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.StudentAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentVo;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.UpdateStudentEvent;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.AppBus;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class StudentFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    Unbinder unbinder;
    @BindView(R.id.list)
    ListViewCompat list;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.login_progress)
    LinearLayout loginProgress;
    @BindView(R.id.btn_retry)
    Button btnRetry;
    @BindView(R.id.login_retry)
    LinearLayout loginRetry;

    private List<Student> students=new ArrayList<>();
    private  List<Student> finalResult=new ArrayList<>();
    private StudentAdapter studentAdapter;
    private boolean isInitialized;
    private boolean hasLoadData;
    private Student student;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        unbinder = ButterKnife.bind(this, view);
        isInitialized=true;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.colorPrimaryDark);
        AppBus.getInstance().register(this);
        return view;
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isInitialized && !hasLoadData) {
            hasLoadData = true;
            init();
        }
    }




    private void init() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        refresh();
    }

    private void refresh() {
        StudentVo tutorVo = new StudentVo();
        HttpHelper.getInstance().post(UrlConstant.GET_ALL_STUDENT, tutorVo, new HttpCallBack<CommonEntity<List<Student>>>() {
            @Override
            public void onSuccess(CommonEntity<List<Student>> listCommonEntity) {
                students.clear();
                finalResult = listCommonEntity.getBean();
                students.addAll(finalResult);
                if (studentAdapter == null) {
                    studentAdapter = new StudentAdapter(getActivity(), students);
                    list.setAdapter(studentAdapter);
                    list.setOnItemClickListener(StudentFragment.this);
                    list.setOnItemLongClickListener(StudentFragment.this);
                } else {
                    studentAdapter.setListAndRefresh(students);
                }

            }

            @Override
            public void onFailure(String message, int code) {
                if(studentAdapter==null){
                    loginRetry.setVisibility(View.VISIBLE);
                }
                UIUtils.showToastSafe(getActivity(), message + ":" + code);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), StudentDetailActivity.class);
        student = students.get(position);

        intent.putExtra("student",student);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        final Long id = students.get(i).getId();
        DialogUtils.showSingleChoiceDialog(getContext(), new String[]{"Delete  student "+students.get(i).getName()}, new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(final MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (which == -1) {
                    UIUtils.showToastSafe(getActivity(), "Please Choice one option");
                } else if (which == 0) {
                    HttpHelper.getInstance().remove(UrlConstant.REMOVE_STUDENT + id, new HttpCallBack<CommonEntity<Object>>() {

                        @Override
                        public void onSuccess(CommonEntity<Object> objectCommonEntity) {
                            Student remove = students.remove(i);
                            finalResult.remove(remove);
                            studentAdapter.notifyDataSetChanged();
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
        refresh();
    }

    public void startSearch(String query) {
        if(studentAdapter==null)
            return ;
        students.clear();

        if(TextUtils.isEmpty(query))
        {
            students.addAll(finalResult);
            studentAdapter.setListAndRefresh(students);
            return ;
        }
        for (Student s:finalResult) {
            if(s.getName().toLowerCase().trim().contains(query.toLowerCase().trim())){
                students.add(s);
            }
        }
        studentAdapter.setListAndRefresh(students);
    }
    @Subscribe
    public void onStudentChange(UpdateStudentEvent courseChangeEvent) {
        if(this.student!=null){
            Student stu = courseChangeEvent.getBean();
            this.student.setCreatedate(stu.getCreatedate());
            this.student.setPaid(stu.getPaid());
            this.student.setMedicalcondition(stu.getMedicalcondition());
            this.student.setMax(stu.getMax());
            this.student.setNote(stu.getNote());
            this.student.setAddress(stu.getAddress());
            this.student.setName(stu.getName());
            this.student.setLevel(stu.getLevel());
            this.student.setParentphonenumber(stu.getParentphonenumber());
            this.student.setMin(stu.getMin());
            this.student.setStartdate(stu.getStartdate());
            studentAdapter.notifyDataSetChanged();
        }


    }


}
