package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import swimmingpool.co.uk.jesmondswimmingpool.R;

/**
 * Created by cody on 2017/11/14.
 */

public abstract class BaseHolder<Data> {

    private final View rootView;
    private Data Data;

    public Data getData() {
        return Data;
    }

    public BaseHolder(Activity activity) {
        int id = initViewId();
        rootView = LayoutInflater.from(activity).inflate(id, null);
        rootView.setTag(this);

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