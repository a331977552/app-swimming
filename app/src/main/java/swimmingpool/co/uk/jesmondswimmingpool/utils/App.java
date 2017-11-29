package swimmingpool.co.uk.jesmondswimmingpool.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by cody on 2017/11/13.
 */

public class App extends Application {

    public static Context context;
    public static int mMainThreadId = -1;
    public static Thread mMainThread;
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
