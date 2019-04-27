package com.example.growthmaster;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growthmaster.adapter.FMAdapter;
import com.example.growthmaster.adapter.MasterAdapter;
import com.example.growthmaster.api.FMApi;
import com.example.growthmaster.bean.FM;
import com.example.growthmaster.util.HttpUtil;
import com.example.growthmaster.util.PictureLoader;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FMActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "FMActivity";

    private Button showBtn;
    private Button refreshBtn;
    private ImageView showImg;
    private TextView showText;

    private ArrayList<FM> data;
    private int limit = 10;
    private int curPos = 0; //当前显示的第几个
    private int offset = 0; //当前偏移值
    private PictureLoader loader;
    private FMApi fmApi;
    private FMTask fmTask;

    private RecyclerView fmListRecyclerView;
    private FMAdapter fmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm);
        fmApi = new FMApi();
        loader = new PictureLoader();
        intiData();
        initUI();

        fmTask = new FMTask();
        fmTask.execute();
    }

    private void intiData(){
        data = new ArrayList<>();

    }

    private void initUI(){

//        showBtn = (Button) findViewById(R.id.btn_show);
        refreshBtn =(Button) findViewById(R.id.btn_refresh);
//        showImg = (ImageView) findViewById(R.id.img_show);
//        showText = (TextView) findViewById(R.id.text_show);

//        showBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);

        fmListRecyclerView = (RecyclerView) findViewById(R.id.fm_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        fmListRecyclerView.setLayoutManager(layoutManager);
        fmAdapter = new FMAdapter(FMActivity.this,data);
        fmListRecyclerView.setAdapter(fmAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.btn_show:
//                Toast.makeText(FMActivity.this,"clicked",Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "click: btn_show");
//                if (data != null && !data.isEmpty()){
//                    if(curPos > 9){
//                        curPos = 0;
//                    }
//                    Log.d(TAG, "click: btn_show > 9");
//                    showText.setText(data.get(curPos).getTitle());
//                    loader.load(showImg, data.get(curPos).getCover());
//                    curPos++;
//                }
//                break;
            case R.id.btn_refresh:
                fmTask = new FMTask();
                fmTask.execute();
                fmListRecyclerView.scrollToPosition(0);
                break;
            default:
                break;
        }
    }

    private class FMTask extends AsyncTask<Void,Void,ArrayList<FM>>{

        public FMTask(){ }

        @Override
        protected ArrayList<FM> doInBackground(Void... params) {
           return fmApi.fetchFM(offset,limit);
        }

        @Override
        protected void onPostExecute(ArrayList<FM> fms) {
            super.onPostExecute(fms);
            data.clear();
            data.addAll(fms);
            offset += limit;
            curPos = 0;
            fmAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            fmTask = null;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fmTask.cancel(true);
    }
}
