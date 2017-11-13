package swimmingpool.co.uk.jesmondswimmingpool.http;

/**
 * Created by cody on 2017/11/12.
 */

public interface HttpCallBack<Bean> {

     void onSuccess(Bean bean);
    void onFailure(String message, int code);
    void after();



}
