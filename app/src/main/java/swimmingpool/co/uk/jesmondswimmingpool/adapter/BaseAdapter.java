package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by cody on 2017/11/15.
 */

public abstract class BaseAdapter<Data> extends RecyclerView.Adapter<BaseAdapter.RecycleBaseHolder> {

    private AdapterView.OnItemClickListener onItemClickListener;

    public Activity getAppCompatActivity() {
        return appCompatActivity;
    }

    private Activity appCompatActivity;

    public void setListAndRefresh(List<Data> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private List<Data> list;

    public List<Data> getList() {
        return list;
    }

    public BaseAdapter(Activity appCompatActivity, List<Data> list) {
        this.appCompatActivity = appCompatActivity;
        this.list = list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener){

        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public void onBindViewHolder(BaseAdapter.RecycleBaseHolder holder, int position) {
        if(onItemClickListener!=null){
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    onItemClickListener.onItemClick(null,v,tag,getItemId(tag));
                }
            });

        }
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static abstract class RecycleBaseHolder<Data> extends RecyclerView.ViewHolder {

        public RecycleBaseHolder(View itemView) {
            super(itemView);

        }

        public abstract void setData(Data data);

    }

}
