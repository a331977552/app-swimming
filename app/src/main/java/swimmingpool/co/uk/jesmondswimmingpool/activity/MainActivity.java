package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.HomeAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.fragment.AttendanceFragment;
import swimmingpool.co.uk.jesmondswimmingpool.fragment.OperationFragment;
import swimmingpool.co.uk.jesmondswimmingpool.fragment.StudentFragment;

public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener, SearchView.OnSuggestionListener {

    ViewPager viewPager;
    private BottomNavigationView navigation;
    private MenuItem item;
    private SearchView searchView;
    private AttendanceFragment attendanceFragment;
    private StudentFragment studentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigation=findViewById(R.id.navigation);
        viewPager  = (ViewPager) findViewById(R.id.viewPager);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(this);



        viewPager.setOffscreenPageLimit(3);
        attendanceFragment = new AttendanceFragment();
        studentFragment = new StudentFragment();
        OperationFragment operationFragment=new OperationFragment();
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(attendanceFragment);
        fragments.add(studentFragment);
        fragments.add(operationFragment);
        viewPager.setAdapter(new HomeAdapter(getSupportFragmentManager(),fragments));
        setTitle("Today's courses");
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        item = menu.findItem(R.id.action_settings);

        //加载searchview
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);//为搜索框设置监听事件
        searchView.setSubmitButtonEnabled(true);//设置是否显示搜索按钮
        searchView.setQueryHint("Course's name");//设置提示信息
        searchView.setOnCloseListener(this);
        searchView.setOnSuggestionListener(this);
        searchView.setIconifiedByDefault(true);//设置搜索默认为图标
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        int id=R.id.navigation_home;;
        switch (position){
            case 0:
                if(item!=null)
                item.setTitle("Search Course");
                setTitle("Today's courses");
                searchView.setQueryHint("Course's name");
                id=R.id.navigation_home;
                break;
            case 1:
                id=R.id.navigation_dashboard;
                setTitle("Students");
                if(item!=null)
                item.setTitle("Search Student");
                searchView.setQueryHint("student's name");
                break;
                case 2:
                    if(item!=null)
                 item.setTitle("Search Course");
                    searchView.setQueryHint("Course's name");
                id=R.id.navigation_notifications;
                    setTitle("Home");
                break;
        }
        navigation.setSelectedItemId(id);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        int currentItem = viewPager.getCurrentItem();
        if(currentItem==1){
            //student
            studentFragment.startSearch(query);
        }else{


        }


        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        int currentItem = viewPager.getCurrentItem();
        if(currentItem==1){
            //student
            studentFragment.startSearch(newText);
        }else{
            attendanceFragment.startSearch(newText);
        }
        return true;
    }

    @Override
    public boolean onClose() {

        return false;
    }

    @Override
    public boolean onSuggestionSelect(int position) {

        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {

        return false;
    }
}
