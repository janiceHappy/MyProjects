package com.example.growthmaster.api;

import android.util.Log;

import com.example.growthmaster.bean.FM;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BroadcastDetailApi {
    private static final String TAG = "BroadcastDetailApi";
    private static final String BASE_URL = "https://yiapi.xinli001.com/fm/broadcast-detail.json";
    private static final String KEY = "046b6a2a43dc6ff6e770255f57328f89";
    // offset=0
    // limit=10

    public FM fetchFM(String id){
        String fetchUrl = BASE_URL + "?id=" + id + "&key=" + KEY;
        FM fm = new FM();
        Log.d(TAG, "url: "+fetchUrl);
        try{

            URL url = new URL(fetchUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(500000);
            conn.setConnectTimeout(500000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            Log.v(TAG,"Server response: " + code);
            if (code == 200){
                InputStream in = conn.getInputStream();
                byte[] data = readFromStream(in);
                String result = new String(data,"UTF-8");
                fm = parseFM(result);
            }else {
                Log.e(TAG,"请求失败：" + code);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return fm;
    }


    /**
     * 解析返回Json数据的方法
     */
    public FM parseFM(String content) throws Exception{
        JSONObject object = new JSONObject(content);
        JSONObject results = object.getJSONObject("data");
        FM fm = new FM();

        fm.setId(results.getString("id"));
        fm.setTitle(results.getString("title"));
        fm.setCover(results.getString("cover"));
        fm.setUrl(results.getString("url"));
        fm.setSpeak(results.getString("speak"));
        fm.setFavnum(results.getInt("favnum"));
        fm.setViewnum(results.getInt("viewnum"));
        fm.setBackground(results.getString("background"));
        fm.setIs_teacher(results.getBoolean("is_teacher"));
        fm.setUrl_list(results.getString("url_list"));
        fm.setCommentnum(results.getInt("commentnum"));
        fm.setRange(results.getString("range"));
        fm.setDuration(results.getInt("duration"));

        Log.d(TAG, "parseFM: fm add");

        return fm;
    }

    /**
     * 读取流中数据的方法
     */
    public byte[] readFromStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len ;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }
}
