package com.example.growthmaster;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.growthmaster.adapter.MsgAdapter;
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

public class ChatActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    User chatUser;
    //public static String URL = "http://lj1757620885.6655.la:54746/office/pushMessage?cid=";
    //private SharedPreferencesHelper sharedPreferencesHelper;
    private String CID,sendMesageContent;//好友的CID
    //private DemoIntentService msgService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

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
                chatUser = new User();
                chatUser.setCid("fb61f569efd33cf3eb536eb945537acb");
                chatUser.setMsg(sendMesageContent);
                if(!"".equals(content)){
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                    Gson gson = new Gson();
                    String json = gson.toJson(chatUser);
//                    HttpUtil.getCall("/pushMessage",json).enqueue(new Callback() {
//                        @Override
//                        public void onFailure(Call call, IOException e) {
//
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Response response) throws IOException {
//
//                        }
//                    });
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

//    @OnClick(R.id.message_send)
//    public void sedMessage(){
//        sendMesageContent = inputText.getText().toString();
//        chatUser = new ChatUser();
//        chatUser.setCid("fb61f569efd33cf3eb536eb945537acb");
//        chatUser.setMsg(sendMesageContent);
//        if (!"".equals(sendMesageContent)) {
//            Message msg = new Message(sendMesageContent, Message.TYPE_SENT);
//            msgList.add(msg);
//            //当有新消息时，调用适配器notifyItemInserted通知列表有新的数据插入，刷新RecyclerView
//            msgAdapter.notifyItemInserted(msgList.size() - 1);
//            //将RecyclerView定位到最后一行，保证可以看到最新消息
//            msgRecyclerView.scrollToPosition(msgList.size() - 1);
//            inputText.setText("");
//            Gson gson = new Gson();
//            String json = gson.toJson(chatUser);
//            HttpUtil.getCall("/pushMessage",json).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//
//                }
//            });
//
//        }
//    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            msgService = ((DemoIntentService.MyGeTui) iBinder).get();
//
//            msgService.setOnProgressListener(new DemoIntentService.OnProgressListener() {
//                @Override
//                public void onProgress(String  progress) {
//                    //   mProgressBar.setProgress(progress);
//
//                    android.os.Message message = android.os.Message.obtain();
//                    message.obj = progress;
//                    mHandler.sendMessage(message);
//                }
//            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

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
