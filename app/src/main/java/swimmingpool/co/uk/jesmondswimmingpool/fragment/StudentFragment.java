package swimmingpool.co.uk.jesmondswimmingpool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.StudentDetailActivity;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.StudentAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.PageBean;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Student;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Tutor;
import swimmingpool.co.uk.jesmondswimmingpool.entity.TutorVo;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class StudentFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<Student> students;
    private StudentAdapter studentAdapter;
    private TutorVo tutorVo;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.setEnableAutoLoadmore(true);
                refreshLayout.setEnableLoadmore(true);
                load(0);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                int i=0;
                if(tutorVo!=null)
                {
                    i = tutorVo.getCurrentPage() + 1;

                }
                load(i);
            }
        });

        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        load(0);



    }

    private void load(int currentPage) {
        tutorVo = new TutorVo();
        tutorVo.setCurrentPage(currentPage);
        Tutor tutor=new Tutor();
        tutor.setId(swimmingpool.co.uk.jesmondswimmingpool.utils.UserManager.getInstance().getId());
        tutorVo.setTutor(tutor);

        HttpHelper.getInstance().post(UrlConstant.GET_ALL_STUDENT_BY_TUTOR, tutorVo, new HttpCallBack<CommonEntity<PageBean<Student>>>() {
            @Override
            public void onSuccess(CommonEntity<PageBean<Student>> listCommonEntity) {
                if(studentAdapter==null){
                    students = listCommonEntity.getBean().getBeans();
                    studentAdapter = new StudentAdapter(getActivity(), students);
                    studentAdapter.setOnItemClickListener(StudentFragment.this);
                    recyclerview.setAdapter(studentAdapter);
                }else{
                    students.addAll(listCommonEntity.getBean().getBeans());
                    studentAdapter.setListAndRefresh(students);
                }
                tutorVo.setTotalPage(listCommonEntity.getBean().getTotalPage());
                if((tutorVo.getCurrentPage()+1)==tutorVo.getTotalPage())
                {
                    //no more content
                    refreshLayout.setEnableAutoLoadmore(false);
                    refreshLayout.setEnableLoadmore(false);
                }
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(getActivity(),message);
            }

            @Override
            public void after() {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefresh();
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
        Intent intent=new Intent(getActivity(),StudentDetailActivity.class);
        intent.putExtra("student",students.get(position));
        startActivity(intent);
    }
}
