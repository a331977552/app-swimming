package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by cody on 2017/11/14.
 */

public abstract class DefaultAdpater<Bean>  extends BaseAdapter{
    private Activity activity;
    private List<Bean> beanList;

    public Activity getActivity() {
        return activity;
    }

    public DefaultAdpater(Activity activity, List<Bean> beanList) {
        this.activity = activity;

        this.beanList = beanList;
    }

    public void setListAndRefresh(List<Bean> data){
        beanList=data;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder  holder=null;
        if(convertView!=null&&convertView.getTag()instanceof BaseHolder){
            holder= (BaseHolder) convertView.getTag();
        }else{
            holder=getHolder();
        }

        holder.setData(beanList.get(position));
        return holder.getRootView();
    }

    protected abstract BaseHolder getHolder();

}
