package com.example.growthmaster;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growthmaster.db.CommentForResource;
import com.example.growthmaster.db.Resource;
import com.example.growthmaster.service.ClientService;
import com.google.gson.Gson;

public class ResourceContent extends AppCompatActivity
        implements View.OnClickListener {

    private ClientService myService;
    private MyServiceConn conn;

    private Resource resource;

    private TextView resourceTitle;
    private TextView resourePublisher;
    private TextClock resourcePublishDate;
    private TextView resourceContent;

    private TextView comment;
    private ImageView appreciate;
    private LinearLayout resourceComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_content);

        conn = new MyServiceConn();
        bindService(new Intent(this, ClientService.class), conn,
                BIND_AUTO_CREATE);

        initResource();
        initViews();
        bindViews();

        resourceTitle.setText(resource.getTitle());
        resourePublisher.setText(resource.getTitle());
        resourcePublishDate.setTimeZone(resource.getPublishTime());
        resourceContent.setText(resource.getContent());


        comment.setOnClickListener(this);
        appreciate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_edit:

                final AlertDialog dialog = new AlertDialog.Builder(ResourceContent.this).create();
                //实例化布局
                final View view = LayoutInflater.from(this).inflate(R.layout.comment_edit,null);
                dialog.setView(view);


                final EditText editText = (EditText) view.findViewById(R.id.edit_text);
                Button editCancel = (Button) view.findViewById(R.id.edit_cancel);
                Button editPublish = (Button) view.findViewById(R.id.edit_publish);

                editText.setFocusable(true);
                editText.setFocusableInTouchMode(true);
                editText.requestFocus();
                editCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                editPublish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content = editText.getText().toString();

                        //将comment传入数据库中
                        //？？？？？此处缺代码？？？？？
//                        String message = "comment" + "\t" + content;
//                        myService.sendRequest(message);

                        dialog.dismiss();
                        //此处应刷新列表
                        //？？？？？此处缺代码？？？？？
                    }
                });

                dialog.show();

                Window dialogWindow = dialog.getWindow();//获取window对象
                dialogWindow.setGravity(Gravity.BOTTOM);//设置对话框位置
                dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏

                //对话框弹出时，弹出软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText,0);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                //对话框收回时，隐藏软键盘
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //隐藏软键盘
                    InputMethodManager imm = (InputMethodManager) ResourceContent.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });

                break;
            case R.id.collect:


            case R.id.transmit:

            default:
                break;
        }
    }

    private void initResource(){
        String masterJson = getIntent().getStringExtra("resource");
        resource = new Gson().fromJson(masterJson, Resource.class);
    }

    private void initViews(){
        resourceTitle = (TextView) findViewById(R.id.resource_title);
        resourePublisher= (TextView) findViewById(R.id.resource_publisher);
        resourcePublishDate= (TextClock) findViewById(R.id.resource_publishDate);
        resourceContent= (TextView) findViewById(R.id.resource_content);

        comment = (TextView) findViewById(R.id.comment_edit);
        appreciate = (ImageView) findViewById(R.id.appreciate);
        resourceComment = (LinearLayout) findViewById(R.id.resource_comment_unclick);
    }

    private void bindViews(){

    }

    public class MyServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((ClientService.LocalBinder) service).getService();
            myService.setAppCompatActivity(ResourceContent.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService = null;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbindService(conn);
    }


}
