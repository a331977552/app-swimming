package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by cody on 2017/11/14.
 */

public abstract class DefaultAdpater<Bean>  extends BaseAdapter{
    private List<Bean> beanList;

    public DefaultAdpater(List<Bean> beanList) {

        this.beanList = beanList;
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
