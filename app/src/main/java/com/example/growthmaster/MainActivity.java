package com.example.growthmaster;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private SearchView searchView;

    private LinearLayout psychologicalServices;
    private LinearLayout studyResource;
    private LinearLayout psychologicalTests;
    private LinearLayout myEmotion;

    private Button qingsu;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("成长守护专家");
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initViews();
        bindViews();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_view:
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.psychological_services:
                Intent intentBook = new Intent(MainActivity.this,PsychologicalServices.class);
                startActivity(intentBook);
                break;
            case R.id.study_resource:
                Intent intentStudy = new Intent(MainActivity.this,StudyResource.class);
                startActivity(intentStudy);
                break;
            case R.id.psychological_tests:
                break;
            case R.id.my_emotion:
                Intent intentFM = new Intent(MainActivity.this,FMActivity.class);
                startActivity(intentFM);
                break;
            case R.id.qingsu:
                Intent intent5 = new Intent(MainActivity.this,ChatActivity.class);
                startActivity(intent5);
                break;
            default:
                break;

        }
    }

    private void initViews(){
        searchView = (SearchView)findViewById(R.id.search_view);
        psychologicalServices = (LinearLayout) findViewById(R.id.psychological_services);
        studyResource = (LinearLayout) findViewById(R.id.study_resource);
        psychologicalTests = (LinearLayout) findViewById(R.id.psychological_tests);
        myEmotion = (LinearLayout) findViewById(R.id.my_emotion);
        qingsu = (Button)findViewById(R.id.qingsu);
        mViewPager = (ViewPager) findViewById(R.id.vp);
    }

    private void bindViews(){
        searchView.setOnClickListener(this);
        psychologicalServices.setOnClickListener(this);
        studyResource.setOnClickListener(this);
        psychologicalTests.setOnClickListener(this);
        myEmotion.setOnClickListener(this);
        qingsu.setOnClickListener(this);
    }
}
