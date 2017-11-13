package swimmingpool.co.uk.jesmondswimmingpool;

import android.util.Log;

import org.junit.Test;

import swimmingpool.co.uk.jesmondswimmingpool.activity.LoginActivity;
import swimmingpool.co.uk.jesmondswimmingpool.activity.MainActivity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Tutor;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;

import static org.junit.Assert.assertEquals;

/**
 * Created by cody on 2017/11/13.
 */

public class LoginTest {

    @Test
    public void login() throws Exception {

        Tutor tutor=new Tutor();
        tutor.setUsername("a123456");
        tutor.setPassword("123456");
        HttpHelper.getInstance().post(UrlConstant.LOGIN, tutor, new HttpCallBack<CommonEntity<Tutor>>() {
            @Override
            public void onSuccess(CommonEntity<Tutor> o) {


            }

            @Override
            public void onFailure(String message, int code) {

            }

            @Override
            public void after() {

            }
        });

    }



}
