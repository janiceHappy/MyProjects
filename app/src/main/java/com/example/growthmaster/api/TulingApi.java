package com.example.growthmaster.api;

import android.util.Log;

import com.example.growthmaster.db.Msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TulingApi {
    private static final String TAG = "TulingApi";
    private static final String URL = "https://openapi.tuling123.com/openapi/api/v2";
    private static final String KEY = "f15f3d2dfbad4991affb3e859174b57e";

    public Msg fetchMessage(String text, String userId){
        Msg message = new Msg();
        try{

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            //使用JSONObject封装参数
            JSONObject jsonObject = new JSONObject();
            try {
                JSONObject perception = new JSONObject();
                JSONObject inputText = new JSONObject();
                inputText.put("text",text);
                perception.put("inputText",inputText);
                JSONObject userInfo = new JSONObject();
                userInfo.put("apiKey",KEY);
                userInfo.put("userId",userId);
                jsonObject.put("perception",perception);
                jsonObject.put("userInfo",userInfo);
            }catch (JSONException e){
                e.printStackTrace();
            }
            Log.d(TAG, "fetchMessage: "+jsonObject.toString());
            RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());

            Request request = new Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            Log.d(TAG, "fetchResponse: "+responseData);
            message = parseMessage(responseData);

        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }


    /**
     * 解析返回Json数据的方法
     */
    public Msg parseMessage(String content) throws Exception{
        Msg msg = new Msg();
        JSONObject object = new JSONObject(content);
        JSONArray results = object.getJSONArray("results");
        for(int i = 0; i < results.length(); i++){
            JSONObject result = (JSONObject) results.get(i);
            if(result.getString("resultType").equals("url")){
                JSONObject values = result.getJSONObject("values");
                msg.setPicture(values.getString("url"));
            }else if (result.getString("resultType").equals("text")){
                JSONObject values = result.getJSONObject("values");
                msg.setContent(values.getString("text"));
            }
        }

        Log.d(TAG, "parseMessage: message add");
        return msg;
    }

}
