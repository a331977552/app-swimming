package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Courseitem;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.CourseItemVo;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.EditAchievementEvent;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.AppBus;
import swimmingpool.co.uk.jesmondswimmingpool.utils.DialogUtils;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;

/**
 * Created by cody on 2017/11/29.
 */

public class EditAchievementActivity extends BaseActivity {
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private Course course;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_achievment);
        ButterKnife.bind(this);
//        R.layout.item_input;
        setTitle("edit");
        course = (Course) getIntent().getSerializableExtra("course");
        ArrayList<Parcelable> ahievements = getIntent().getParcelableArrayListExtra("ahievements");
        if(ahievements==null || ahievements.size()==0){

        }else{

            for (Parcelable p : ahievements) {
                Courseitem  courseitem= (Courseitem) p;
                addItem(courseitem);
            }
        }



    }

    private void addItem(Courseitem courseitem) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_input, null);

        TextInputLayout input = inflate.findViewById(R.id.input);
        EditText editText = inflate.findViewById(R.id.et_name);
        input.setHint(courseitem.getName());
        editText.setHint("");
        editText.setText("");
        editText.setTag(courseitem);

        llContent.addView(inflate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.item_add) {
            Courseitem courseitem=new Courseitem();
            courseitem.setName("");
            courseitem.setCourseid(course.getId());
            addItem(courseitem);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.edit_achievement,menu);
        return true;
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        int childCount = llContent.getChildCount();
        final ArrayList<Courseitem> courseitems=new ArrayList<>();
        for (int i=0;i<childCount;i++){
            View childAt = llContent.getChildAt(i);
            EditText et = childAt.findViewById(R.id.et_name);
            Courseitem courseitem= (Courseitem) et.getTag();
            courseitem.setCourseid(course.getId());
            courseitem.setId(null);
            if(!TextUtils.isEmpty(et.getText().toString())){
                courseitem.setName(et.getText().toString());
            }
            courseitems.add(courseitem);
        }

        List<Courseitem> removing=new ArrayList<>();
        for (Courseitem item:courseitems){
            if(item.getName()==null ||item.getName().isEmpty()){
                removing.add(item);
            }
        }

        courseitems.removeAll(removing);
        if(courseitems.isEmpty())
        {
            new MaterialDialog.Builder(this).content("Sure to delete all the achievement realted to this course ?").positiveText("YES").negativeText("CANCEL").onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    submit(courseitems);
                    dialog.dismiss();
                }
            }).onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                }
            }).show();
        }else{
            submit(courseitems);

        }

    }

    private void submit(ArrayList<Courseitem> courseitems) {
        btnConfirm.setEnabled(false);
        final MaterialDialog materialDialog = DialogUtils.processingDialog(this);
        materialDialog.show();
        CourseItemVo courseItemVo=new CourseItemVo();
        courseItemVo.setCourseId(course.getId());
        courseItemVo.setItems(courseitems);
        HttpHelper.getInstance().post(UrlConstant.ADD_ITEMS, courseItemVo, new HttpCallBack<CommonEntity<CourseItemVo>>() {

            @Override
            public void onSuccess(CommonEntity<CourseItemVo> listCommonEntity) {
                    UIUtils.showToastSafe(getApplication(),listCommonEntity.getMsg());
                    AppBus.getInstance().post(new EditAchievementEvent(listCommonEntity.getBean().getItems()));
                    UIUtils.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            finish();
                        }
                    },300);
            }

            @Override
            public void onFailure(String message, int code) {
                btnConfirm.setEnabled(true);
                UIUtils.showToastSafe(getApplication(),message+code);
            }

            @Override
            public void after() {
                materialDialog.dismiss();
            }
        });
    }
}
