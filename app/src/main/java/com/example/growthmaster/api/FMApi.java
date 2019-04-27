package com.example.growthmaster.api;

import android.util.Log;

import com.example.growthmaster.bean.FM;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FMApi {
    private static final String TAG = "Network";
    private static final String BASE_URL = "https://yiapi.xinli001.com/fm/newfm-list.json";
    private static final String KEY = "046b6a2a43dc6ff6e770255f57328f89";
    // offset=0
    // limit=10

    public ArrayList<FM> fetchFM(int offset, int limit){
        String fetchUrl = BASE_URL + "?offset=" + offset + "&limit=" + limit + "&key=" + KEY;
        ArrayList<FM> fms = new ArrayList<>();
        Log.d("FMApi", "fetchFM: ");
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
                fms = parseFM(result);
            }else {
                Log.e(TAG,"请求失败：" + code);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return fms;
    }


     /**
      * 解析返回Json数据的方法
      */
     public ArrayList<FM> parseFM(String content) throws Exception{
        ArrayList<FM> fms = new ArrayList<>();
         JSONObject object = new JSONObject(content);
         JSONArray array = object.getJSONArray("data");
         for (int i = 0 ; i < array.length(); i++){
             JSONObject results = (JSONObject) array.get(i);
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
             fm.setObject_id(results.getString("object_id"));
             fms.add(fm);
             Log.d(TAG, "parseFM: fm add");
         }
         return fms;
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
