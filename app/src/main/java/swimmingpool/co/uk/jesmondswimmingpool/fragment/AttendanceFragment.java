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

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.activity.CourseDetailsActivity;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.AttendanceListAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.CourseVo;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.LogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class AttendanceFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.list)
    ListViewCompat list;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private List<Course> bean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_attendance, container, false);

        unbinder = ButterKnife.bind(this,inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        list.setAdapter();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,R.color.colorAccent,R.color.c_07aeed);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();

    }

    private void load() {
        CourseVo courseVo=new CourseVo();
        courseVo.setDay(new Date());
        HttpHelper.getInstance().post(UrlConstant.GET_ALL_COURSE,courseVo, new HttpCallBack<CommonEntity<List<Course>>>() {

            @Override
            public void onSuccess(CommonEntity<List<Course>> listCommonEntity) {
                bean = listCommonEntity.getBean();

                list.setAdapter(new AttendanceListAdapter(getActivity(), bean));
                list.setOnItemClickListener(AttendanceFragment.this);

            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(getActivity(),message+code);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogUtils.i("onItemClick");
        Intent intent=new Intent(getActivity(),CourseDetailsActivity.class);
        intent.putExtra("course",bean.get(i));
        startActivity(intent);
    }
}
