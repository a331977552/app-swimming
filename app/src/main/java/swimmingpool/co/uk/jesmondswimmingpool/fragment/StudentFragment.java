package swimmingpool.co.uk.jesmondswimmingpool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.StudentDetailActivity;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.StudentAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentVo;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class StudentFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.list)
    ListViewCompat list;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    private List<Student> students;
    private StudentAdapter studentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
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
                students = listCommonEntity.getBean();

                if (studentAdapter == null) {
                    studentAdapter = new StudentAdapter(getActivity(), students);
                    list.setAdapter(studentAdapter);
                    list.setOnItemClickListener(StudentFragment.this);
                }else{
                    studentAdapter.setListAndRefresh(students);
                }

            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(getActivity(), message+":"+code);
            }

            @Override
            public void after() {
               swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), StudentDetailActivity.class);
        intent.putExtra("student", students.get(position));
        startActivity(intent);
    }
}
