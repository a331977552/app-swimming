package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.view.View;

/**
 * Created by cody on 2017/11/14.
 */

public abstract class BaseHolder<Data> {

    private final View rootView;
    private Data Data;

    public BaseHolder() {
        rootView = initView();
        rootView.setTag(this);
    }

    public View getRootView(){
        return rootView;
    }
    public abstract View initView();

    public void setData(Data data){
        this.Data=data;
        initData();
    }

    protected abstract void initData();
}