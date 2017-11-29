package swimmingpool.co.uk.jesmondswimmingpool.http;

import swimmingpool.co.uk.jesmondswimmingpool.utils.App;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/28.
 */

public abstract class SimpleHttpCallBack<Bean> implements HttpCallBack<Bean> {



    @Override
    public void onFailure(String message, int code) {
        UIUtils.showToastSafe(App.context,message+":"+code);
    }

}
