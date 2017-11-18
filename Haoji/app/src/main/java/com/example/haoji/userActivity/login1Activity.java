package com.example.haoji.userActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
//import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haoji.Button.ButtonData;
import com.example.haoji.HttpUtil;
import com.example.haoji.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editText1;
    private EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editText1 = (EditText) findViewById(R.id.userphone);
        editText2 = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.modifyPsw);
        button.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.modifyPsw:
                //获取用户名和密码
                String inputText1 = editText1.getText().toString();
                String inputText2 = editText2.getText().toString();
                //构造JSONObject类型的数据
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username",inputText1);
                    jsonObject.put("password",inputText2);
                    jsonObject.put("phonenum",inputText1);
                    jsonObject.put("qq"," ");
                    jsonObject.put("weibo"," ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //发送请求
                HttpUtil.sendOkHttpRequest("http://97.64.21.155:8001/user/signup", jsonObject, new okhttp3.Callback() {
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if(judgeState(responseData)){

                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_LONG);
                        }
                    }
                    public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            default:
                break;
        }
    }

    private boolean judgeState(String responseData) {
        try{
            JSONArray jsonArray = new JSONArray(responseData);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            int state = jsonObject.getInt("state");
            if(state==200) return true;
            else return false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      switch(item.getItemId()){
          case android.R.id.home:
              this.finish();
              return true;
      }

        return super.onOptionsItemSelected(item);
    }


}
