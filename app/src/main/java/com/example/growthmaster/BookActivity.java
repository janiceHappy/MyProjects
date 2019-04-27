package com.example.growthmaster;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.growthmaster.fragment.CustomDatePickerDialogFragment;

import java.util.Calendar;

public class BookActivity extends AppCompatActivity implements View.OnClickListener,
        CustomDatePickerDialogFragment.OnSelectedDateListener{

    private EditText describeContentEt;
    private TextView describeContentTv;
    private RelativeLayout bookDate;
    private TextView selectedDate;
    private RelativeLayout bookTime;
    private TextView selectedTime;

    private Button bookButton;
    private RadioGroup formSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initViews();

        formSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.form_voice:
                        break;
                    case R.id.form_video:
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_button:
                bookButton.setText("已预约");
                bookButton.setBackgroundColor(Color.GRAY);
                describeContentEt.setVisibility(View.INVISIBLE);
                describeContentTv.setVisibility(View.VISIBLE);
                break;
            case R.id.book_date:
                showDatePickDialog();
                break;
            case R.id.book_time:
                showTimePickDialog();
        }

    }

    private void initViews(){
        bookButton = (Button) findViewById(R.id.book_button);
        bookButton.setOnClickListener(this);
        describeContentEt = (EditText) findViewById(R.id.describe_content_et);
        describeContentTv = (TextView) findViewById(R.id.describe_content_tv);
        bookDate = (RelativeLayout) findViewById(R.id.book_date);
        selectedDate = (TextView) findViewById(R.id.selected_date);
        bookTime = (RelativeLayout) findViewById(R.id.book_time);
        selectedTime = (TextView) findViewById(R.id.selected_time);

        bookDate.setOnClickListener(this);
        bookTime.setOnClickListener(this);



        formSelect = (RadioGroup) findViewById(R.id.form_select);
    }

    long day = 24 * 60 * 60 * 1000;

    private void showDatePickDialog() {
        CustomDatePickerDialogFragment fragment = new CustomDatePickerDialogFragment();
        fragment.setOnSelectedDateListener(this);
        Bundle bundle = new Bundle();
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        currentDate.set(Calendar.HOUR_OF_DAY,0);
        currentDate.set(Calendar.MINUTE,0);
        currentDate.set(Calendar.SECOND,0);
        currentDate.set(Calendar.MILLISECOND,0);
        bundle.putSerializable(CustomDatePickerDialogFragment.CURRENT_DATE,currentDate);


        long start = currentDate.getTimeInMillis() - day ;
        long end = currentDate.getTimeInMillis() + day * 14;
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(start);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(end);
        bundle.putSerializable(CustomDatePickerDialogFragment.START_DATE,currentDate);
        bundle.putSerializable(CustomDatePickerDialogFragment.END_DATE,endDate);

        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(),CustomDatePickerDialogFragment.class.getSimpleName());
    }

    @Override
    public void onSelectedDate(int year, int monthOfYear, int dayOfMonth) {
        selectedDate.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
        selectedDate.setVisibility(View.VISIBLE);
    }

    private void showTimePickDialog(){
        final String[] times = new String[]{"19:00-19:50", "20:00-20:50", "21:00-21:50"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
        builder.setSingleChoiceItems(times, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "你选择了" + times[which], Toast.LENGTH_SHORT).show();
                        selectedTime.setText(times[which]);
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
