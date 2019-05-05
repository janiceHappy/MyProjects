package com.example.growthmaster;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.growthmaster.adapter.MsgAdapter;
import com.example.growthmaster.api.TulingApi;
import com.example.growthmaster.db.Msg;
import com.example.growthmaster.db.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity{

    private static final String TAG = "ChatActivity";
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    private String CID,sendMesageContent;//好友的CID
    private ChatTask chatTask;
    private TulingApi tulingApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        tulingApi = new TulingApi();

        initMsgs();
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                sendMesageContent = inputText.getText().toString();
                CID = "123456";

                if(!"".equals(content)){
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");

                    chatTask = new ChatTask();
                    chatTask.execute();

                }
            }
        });
    }

    private void initMsgs(){
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello, Who is that?",Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom.Nice talking to you.",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

    private class ChatTask extends AsyncTask<Void,Void,Msg> {

        public ChatTask(){ }

        @Override
        protected Msg doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: ");
            return tulingApi.fetchMessage(sendMesageContent,"123456");
        }

        @Override
        protected void onPostExecute(Msg msg) {
            super.onPostExecute(msg);
            msgList.add(msg);
            adapter.notifyItemInserted(msgList.size()-1);
            msgRecyclerView.scrollToPosition(msgList.size()-1);
            inputText.setText("");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            chatTask = null;
        }


    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(android.os.Message message) {
            Msg msg = new Msg((String) message.obj, Msg.TYPE_RECEIVED);
            msgList.add(msg);
            //当有新消息时，调用适配器notifyItemInserted通知列表有新的数据插入，刷新RecyclerView
            adapter.notifyItemInserted(msgList.size() - 1);
            //将RecyclerView定位到最后一行，保证可以看到最新消息
            msgRecyclerView.scrollToPosition(msgList.size() - 1);
            return true;
        }
    });

}
