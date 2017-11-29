package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by cody on 2017/11/14.
 */

public abstract class BaseHolder<Data> {

    private final View rootView;
    private Data Data;
    private Activity activity;

    public Data getData() {
        return Data;
    }

    public Activity getActivity() {
        return activity;
    }

    public BaseHolder(Activity activity) {
        this.activity = activity;
        int id = initViewId();
        rootView = LayoutInflater.from(activity).inflate(id, null);
        rootView.setTag(this);
        ButterKnife.bind(this,rootView);
    }

    public View getRootView(){
        return rootView;
    }
    public abstract int initViewId();

    public void setData(Data data){
        this.Data=data;
        initData(Data);
    }

    protected abstract void initData(Data data);
}