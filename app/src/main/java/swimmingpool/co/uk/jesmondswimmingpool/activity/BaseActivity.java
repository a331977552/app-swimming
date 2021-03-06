package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import swimmingpool.co.uk.jesmondswimmingpool.R;

/**
 * Created by cody on 2017/11/14.
 */

public  abstract  class BaseActivity  extends SwipeBackActivity{
    Toolbar toolbar;
    ViewGroup parent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         parent = (ViewGroup) findViewById(R.id.parent);
         getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void setContentView(View view) {
        parent.addView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        View.inflate(this,layoutResID,parent);
    }
}
