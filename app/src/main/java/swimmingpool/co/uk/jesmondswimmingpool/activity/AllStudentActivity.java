package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import swimmingpool.co.uk.jesmondswimmingpool.R;

/**
 * Created by cody on 2017/11/30.
 */

public class AllStudentActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("All Students");
        setContentView(R.layout.activity_all_students);


    }
}
