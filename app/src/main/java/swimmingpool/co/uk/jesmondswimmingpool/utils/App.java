package swimmingpool.co.uk.jesmondswimmingpool.utils;

import android.app.Application;
import android.content.Context;

import java.util.logging.Handler;

/**
 * Created by cody on 2017/11/13.
 */

public class App extends Application {
    public static Context context;
    /** 主线程ID */
    public static int mMainThreadId = -1;
    /** 主线程ID */
    public static Thread mMainThread;
    /** 主线程Handler */
    public static android.os.Handler mMainThreadHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new android.os.Handler();

    }
}
