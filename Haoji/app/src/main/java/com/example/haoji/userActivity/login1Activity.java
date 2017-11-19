package com.example.haoji.userActivity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoji.Button.ButtonData;
import com.example.haoji.HttpUtil;
import com.example.haoji.R;
import com.example.haoji.dailyActivity.dailyActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login1Activity extends AppCompatActivity{
//    private EditText editText1;
//    private EditText editText2;
    private EditText userPhone;//用户名
    private EditText passWord;//密码
    private Button loginBtn;//登录按钮
    private TextView register;//注册控件
    private TextView passForget;//忘记密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //通过id获取各控件
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userPhone = (EditText) findViewById(R.id.userphone);
        passWord = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        passForget = (TextView) findViewById(R.id.forget_psw);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //登录按钮的点击事件
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取手机号和密码
                String myPhone = userPhone.getText().toString();
                String myPass = passWord.getText().toString();
                //构造发送的json数据
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("password",myPass);
                    jsonObject.put("phonenum",myPhone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //发送okhttp 请求
                HttpUtil.sendOkHttpRequest("http://97.64.21.155:8001/user/login", jsonObject, new okhttp3.Callback() {
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if(isValid()){
                            if(judgeState(responseData)){       //跳转到主界面
                                Intent intent=new Intent(login1Activity.this,dailyActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(login1Activity.this,"登录失败",Toast.LENGTH_LONG);
                            }
                        }
                    }
                    public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        //跳转到注册界面
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login1Activity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //跳转到忘记密码界面
        passForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login1Activity.this,forgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    //判断请求返回状态
    private boolean judgeState(String responseData) {
        try{
            JSONObject jsonObject = new JSONObject(responseData);
            int state = jsonObject.getInt("state");
            if(state==200) {
                return true;
            }
            else if(state==401){
                Toast.makeText(this,"手机号错误",Toast.LENGTH_SHORT);
                return false;
            }
            else if(state==402){
                Toast.makeText(this,"密码错误",Toast.LENGTH_SHORT);
                return false;
            }
            else return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    //判断用户名和密码是否合法
    public boolean isValid(){
        if (userPhone.getText().toString().trim().equals("")||passWord.getText().toString().trim().equals("")) {
            Toast.makeText(this,"用户名或密码有误",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
