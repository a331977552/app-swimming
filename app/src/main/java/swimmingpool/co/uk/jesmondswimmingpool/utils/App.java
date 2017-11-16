package swimmingpool.co.uk.jesmondswimmingpool.utils;

import android.app.Application;
import android.content.Context;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.text.SimpleDateFormat;
import java.util.logging.Handler;

import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.view.CustomFooter;
import swimmingpool.co.uk.jesmondswimmingpool.view.CustomHeader;

/**
 * Created by cody on 2017/11/13.
 */

public class App extends Application {
    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//color

                return new MaterialHeader(context);//
            }
        });
        //global footer
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //
                CustomFooter classicsFooter = new CustomFooter(context).setDrawableSize(20);

                return classicsFooter;
            }
        });
    }
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
