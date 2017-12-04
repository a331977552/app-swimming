package swimmingpool.co.uk.jesmondswimmingpool.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.otto.Subscribe;
import com.zhy.view.flowlayout.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import swimmingpool.co.uk.jesmondswimmingpool.R;
import swimmingpool.co.uk.jesmondswimmingpool.adapter.CourseStudentAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.entity.CommonEntity;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Course;
import swimmingpool.co.uk.jesmondswimmingpool.entity.Courseitem;
import swimmingpool.co.uk.jesmondswimmingpool.entity.custom.StudentVo;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.EditAchievementEvent;
import swimmingpool.co.uk.jesmondswimmingpool.entity.event.UpdateCourseEvent;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.HttpHelper;
import swimmingpool.co.uk.jesmondswimmingpool.http.SimpleHttpCallBack;
import swimmingpool.co.uk.jesmondswimmingpool.http.UrlConstant;
import swimmingpool.co.uk.jesmondswimmingpool.utils.AppBus;
import swimmingpool.co.uk.jesmondswimmingpool.utils.UIUtils;
import swimmingpool.co.uk.jesmondswimmingpool.view.TagAdapter;
import swimmingpool.co.uk.jesmondswimmingpool.view.TagFlowLayout;

public class CourseDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    ListViewCompat list;
    @BindView(R.id.aboutStudent)
    TextView aboutStudent;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_tutor)
    AppCompatTextView tvTutor;
    @BindView(R.id.tv_start)
    AppCompatTextView tvStart;
    @BindView(R.id.tv_end)
    AppCompatTextView tvEnd;
    @BindView(R.id.tv_note)
    AppCompatTextView tvNote;
    @BindView(R.id.tv_time)
    AppCompatTextView tv_time;
    @BindView(R.id.ll_content)
    LinearLayoutCompat llContent;
    @BindView(R.id.achievement)
    TextView achievement;
    @BindView(R.id.btn_edit_course)
    Button btn_edit_course;
    @BindView(R.id.tf_flowlayout)
    swimmingpool.co.uk.jesmondswimmingpool.view.TagFlowLayout tfFlowlayout;
    @BindView(R.id.btn_edit)
    Button btn_edit;
    private Course course;
    private List<StudentVo> bean;
    private ArrayList<Courseitem> ahievements;
    private CourseStudentAdapter courseStudentAdapter;
    private TagAdapter<Courseitem> tagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        list = (ListViewCompat) findViewById(R.id.list);
        setTitle("CourseDetail");
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_header_course, null);
        list.addHeaderView(inflate);
        ButterKnife.bind(this);
        course = (Course) getIntent().getSerializableExtra("course");
        initCouseDetails();
        AppBus.getInstance().register(this);

        load();

    }

    private void initCouseDetails() {
        tvName.setText(course.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tvStart.setText(simpleDateFormat.format(course.getStartDate()));
        tvEnd.setText(simpleDateFormat.format(course.getEndDate()));
        tvNote.setText(course.getNote());
        tvTutor.setText(course.getTutorname());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppBus.getInstance().unregister(this);
    }

    private void load() {
        HttpHelper.getInstance().get(UrlConstant.GET_ALL_ITEM_BY_COURSE + course.getId(), new HttpCallBack<CommonEntity<ArrayList<Courseitem>>>() {

            @Override
            public void onSuccess(CommonEntity<ArrayList<Courseitem>> courseitemCommonEntity) {
                ahievements = courseitemCommonEntity.getBean();
                if (courseStudentAdapter != null)
                    courseStudentAdapter.setAchievement(ahievements);
                    tagAdapter = new TagAdapter<Courseitem>(ahievements) {

                    @Override
                    public View getView(FlowLayout parent, int position, Courseitem courseitem) {

                        CardView inflate = (CardView) LayoutInflater.from(CourseDetailsActivity.this).inflate(R.layout.item_achievement, null);
                        TextView textView = inflate.findViewById(R.id.tv_ach);
                        ColorM colorM = randomColor();
                        textView.setText(courseitem.getName());
                        ViewCompat.setElevation(inflate,10);
                        inflate.setCardBackgroundColor(colorM.color);
                        inflate.setCardElevation(10);

                        return inflate;
                    }

                };
                tfFlowlayout.setAdapter(tagAdapter);

                tfFlowlayout.setOnLongTagClickListener(new TagFlowLayout.OnTagLongClickListener() {
                    @Override
                    public boolean onTagLongClick(View view, int position, FlowLayout parent) {
                        showDelete(parent, position);
                        return true;
                    }
                });
            }

            @Override
            public void onFailure(String message, int code) {
                UIUtils.showToastSafe(CourseDetailsActivity.this, message + code);
            }

            @Override
            public void after() {
            }
        });
        HttpHelper.getInstance().get(UrlConstant.GET_ALL_STUDENT_BYCOURSE + course.getId(), new SimpleHttpCallBack<CommonEntity<List<StudentVo>>>() {


            @Override
            public void onSuccess(CommonEntity<List<StudentVo>> studentCommonEntity) {
                bean = studentCommonEntity.getBean();
                courseStudentAdapter = new CourseStudentAdapter(ahievements, course.getId(), CourseDetailsActivity.this, bean);
                list.setAdapter(courseStudentAdapter);
                list.setOnItemClickListener(CourseDetailsActivity.this);
            }


            @Override
            public void after() {

            }
        });

    }

    private void showDelete(FlowLayout parent, int position) {

        final ArrayList<Courseitem> achievements = this.ahievements;
        final Courseitem courseitem = achievements.get(position);
        new MaterialDialog.Builder(this).items(new String[]{"delete this achievement"}).itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if(which==-1)
                {
                    UIUtils.showToastSafe(CourseDetailsActivity.this,"Please tick one option");
                    return false;
                }
                HttpHelper.getInstance().remove(UrlConstant.REMOVE_ITEM + courseitem.getId(), new HttpCallBack<CommonEntity<Object>>() {

                    @Override
                    public void onSuccess(CommonEntity<Object> objectCommonEntity) {
                        UIUtils.showToastSafe(CourseDetailsActivity.this, objectCommonEntity.getMsg());
                        achievements.remove(courseitem);
                        tagAdapter.notifyDataChanged();
                    }

                    @Override
                    public void onFailure(String message, int code) {
                        UIUtils.showToastSafe(CourseDetailsActivity.this, message + code);
                    }

                    @Override
                    public void after() {

                    }
                });
                dialog.dismiss();
                return true;
            }
        }).positiveText("YES").negativeText("CANCEL").title("Option").show();


    }

    public ColorM randomColor() {
        Random random = new Random();
        String r, g, b, rr, gg, bb;
        int a = random.nextInt(256);
        int b1 = random.nextInt(256);
        int c = random.nextInt(256);
        r = Integer.toHexString(a).toUpperCase();
        g = Integer.toHexString(b1).toUpperCase();
        b = Integer.toHexString(c).toUpperCase();
        rr = Integer.toHexString(256 - a).toUpperCase();
        gg = Integer.toHexString(256 - b1).toUpperCase();
        bb = Integer.toHexString(256 - c).toUpperCase();
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        ColorM colorM = new ColorM();
        colorM.color = Color.parseColor("#" + r + g + b);
        r = rr.length() == 1 ? "0" + rr : rr;
        g = gg.length() == 1 ? "0" + gg : gg;
        b = bb.length() == 1 ? "0" + bb : bb;


        return colorM;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, StudentDetailActivity.class);
        StudentVo item = (StudentVo) adapterView.getAdapter().getItem(i);
        if (item == null)
            return;
        intent.putExtra("student", item.getStudent());
        startActivity(intent);

    }

    @OnClick({R.id.btn_edit,R.id.btn_edit_course})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.btn_edit:
                Intent intent = new Intent(this, EditAchievementActivity.class);
                intent.putParcelableArrayListExtra("ahievements", ahievements);
                intent.putExtra("course", course);
                startActivity(intent);
                break;
            case R.id.btn_edit_course:
                Intent intent2 = new Intent(this, EditCourseActivity.class);

                intent2.putExtra("course", course);
                startActivity(intent2);
                break;
        }


    }

    public class ColorM {
        int color;
    }
    @SuppressWarnings("unused")
    @Subscribe
    public void onAchievementChanged(EditAchievementEvent editAchievementEvent){

        ahievements.clear();
                ahievements.addAll(editAchievementEvent.getItems());
        tagAdapter.notifyDataChanged();


    }
    @SuppressWarnings("unused")
    @Subscribe
    public void onAchievementChanged(UpdateCourseEvent editAchievementEvent){
        course=editAchievementEvent.getBean();

        initCouseDetails();
    }
}
