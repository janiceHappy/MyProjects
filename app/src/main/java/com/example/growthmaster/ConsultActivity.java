package com.example.growthmaster;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growthmaster.adapter.MasterListFragmentPagerAdapter;
import com.example.growthmaster.adapter.MyPagerAdapter;
import com.example.growthmaster.db.Master;
import com.example.growthmaster.adapter.MasterAdapter;

import java.util.ArrayList;
import java.util.List;

public class PsychologicalServices extends AppCompatActivity
        implements View.OnClickListener,ViewPager.OnPageChangeListener {

    private List<Master> masterList = new ArrayList<>();


    private ViewPager vpager_four;
    private ImageView img_cursor;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;

    private ArrayList<View> listViews;
    private int offset = 0;//移动条图片的偏移量
    private int currIndex = 0;//当前页面的编号
    private int bmpWidth;// 移动条图片的长度
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离

    private MasterListFragmentPagerAdapter masterListFragmentPagerAdapter;

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    public static final int PAGE_FIVE = 4;
    public static final int PAGE_SIX = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("心理咨询");
        setContentView(R.layout.psychological_services_layout);
        initViews();
        initMaster();

    }

    private void initMaster(){
        for(int i = 0; i < 8; i++){
            Master master = new Master("欧洲帅哥","心理学硕士",
                    "儿童成长","100-200",R.drawable.master);
            masterList.add(master);
        }
    }

    private void initPageItem(View view){
        RecyclerView masterListRecyclerView = (RecyclerView) view.findViewById(R.id.master_list_recycler_view);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        masterListRecyclerView.setLayoutManager(layoutManager1);
        MasterAdapter masterAdapter = new MasterAdapter(masterList);
        masterListRecyclerView.setAdapter(masterAdapter);
    }

    private void initViews() {
        vpager_four = (ViewPager) findViewById(R.id.view_pager);
        tv_one = (TextView) findViewById(R.id.tv_one);
        tv_two = (TextView) findViewById(R.id.tv_two);
        tv_three = (TextView) findViewById(R.id.tv_three);
        img_cursor = (ImageView) findViewById(R.id.img_cursor);

        //下划线动画的相关设置：
        bmpWidth = BitmapFactory.decodeResource(getResources(), R.mipmap.line).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 3 - bmpWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置
        //移动的距离
        one = offset * 2 + bmpWidth;// 移动一页的偏移量,比如1->2,或者2->3
        two = one * 2;// 移动两页的偏移量,比如1直接跳3


        //往ViewPager填充View，同时设置点击事件与页面切换事件
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        View view1 =  mInflater.inflate(R.layout.master_list, null, false);
        initPageItem(view1);
        listViews.add(view1);
        View view2 =  mInflater.inflate(R.layout.master_list, null, false);
        initPageItem(view2);
        listViews.add(view2);
        View view3 =  mInflater.inflate(R.layout.master_list, null, false);
        initPageItem(view3);
        listViews.add(view3);
        //listViews.add(mInflater.inflate(R.layout.view_two, null, false));
        //listViews.add(mInflater.inflate(R.layout.view_three, null, false));
        vpager_four.setAdapter(new MyPagerAdapter(listViews));
        vpager_four.setCurrentItem(0);          //设置ViewPager当前页，从0开始算

        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);

        vpager_four.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_one:
                vpager_four.setCurrentItem(0);
                break;
            case R.id.tv_two:
                vpager_four.setCurrentItem(1);
                break;
            case R.id.tv_three:
                vpager_four.setCurrentItem(2);
                break;
        }
    }

    @Override
    public void onPageSelected(int index) {
        Animation animation = null;
        switch (index) {
            case 0:
                if (currIndex == 1) {
                    animation = new TranslateAnimation(one, 0, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, 0, 0, 0);
                }
                break;
            case 1:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, one, 0, 0);
                } else if (currIndex == 2) {
                    animation = new TranslateAnimation(two, one, 0, 0);
                }
                break;
            case 2:
                if (currIndex == 0) {
                    animation = new TranslateAnimation(offset, two, 0, 0);
                } else if (currIndex == 1) {
                    animation = new TranslateAnimation(one, two, 0, 0);
                }
                break;
        }
        currIndex = index;
        animation.setFillAfter(true);// true表示图片停在动画结束位置
        animation.setDuration(300); //设置动画时间为300毫秒
        img_cursor.startAnimation(animation);//开始动画
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }


}
