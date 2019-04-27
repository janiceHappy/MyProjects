package com.example.growthmaster;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.growthmaster.db.Master;
import com.google.gson.Gson;

public class MasterDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Master master;

    private ImageView masterImage;
    private TextView masterName;
    private TextView masterTitle;
    private TextView masterField;

    private Button booking_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("专家详情页");
        setContentView(R.layout.master_detail_layout);
        initMaster();
        initViews();

        masterImage = (ImageView) findViewById(R.id.master_detail_image);
        masterImage.setImageResource(master.getImageId());
        masterName = (TextView) findViewById(R.id.master_detail_name);
        masterName.setText(master.getName());
        masterTitle = (TextView) findViewById(R.id.master_detail_title);
        masterTitle.setText(master.getTitle());
        masterField = (TextView) findViewById(R.id.field_content);
        masterField.setText(master.getField());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.following:


            case R.id.chat:
                Intent intentChat = new Intent(MasterDetailActivity.this,
                        ChatActivity.class);
                startActivity(intentChat);
                break;
            case R.id.booking:
                Intent intentBook= new Intent(MasterDetailActivity.this,
                        BookActivity.class);
                startActivity(intentBook);
                break;
            default:
                break;
        }
    }

    public void initMaster(){
        String masterJson = getIntent().getStringExtra("master");
        master = new Gson().fromJson(masterJson,Master.class);
    }

    public void initViews(){

        booking_button = (Button) findViewById(R.id.booking);
        booking_button.setOnClickListener(this);


    }
}
