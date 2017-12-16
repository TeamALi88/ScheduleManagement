package com.example.haoji;

import android.util.Log;
import android.widget.Toast;

import com.example.haoji.userActivity.RegisterActivity;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by win10 on 2017/11/16.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,JSONObject json,okhttp3.Callback callback){
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient() ;
        RequestBody requestbody = RequestBody.create(JSON,json.toString());
        Request request = new Request.Builder() .url(address).post(requestbody).build();
        System.out.print(requestbody);
        client .newCall(request ).enqueue(callback );

    }
}

