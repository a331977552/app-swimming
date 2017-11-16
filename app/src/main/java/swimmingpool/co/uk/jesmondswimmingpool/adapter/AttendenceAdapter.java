package swimmingpool.co.uk.jesmondswimmingpool.adapter;

import android.app.Activity;

import java.util.List;

/**
 * Created by cody on 2017/11/14.
 */

public class AttendenceAdapter extends DefaultAdpater{
    public AttendenceAdapter(Activity activity,List list) {
        super(activity,list);
    }

    @Override
    protected BaseHolder getHolder() {
        return null;
    }
}
