package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.utils.LogUtils;

/**
 * Created by cody on 2017/11/14.
 */

public class DeleteCourseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("delete");
        setContentView(R.layout.activity_delete_course);
    }
}
