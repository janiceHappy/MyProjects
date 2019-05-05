package com.example.growthmaster.util;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;

import java.text.SimpleDateFormat;

public class DateUtil {

    @SuppressLint("SimpleDateFormat")
    public static String dataToString(ContactsContract.Data date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return df.format(date);
    }
}
