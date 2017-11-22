package com.example.haoji.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoji.GlobalVariable;
import com.example.haoji.HttpUtil;
import com.example.haoji.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class changePasswordActivity extends AppCompatActivity {
    private EditText userName;
    private TextView userPhone;
    private EditText userQQ;
    private EditText userWeibo;
    private Button editPsw;//修改密码
    private FloatingActionButton editCheck;//修改成功
    private GlobalVariable app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        userName=(EditText) findViewById(R.id.edit_text1);
        userPhone=(TextView) findViewById(R.id.edit_text2);
        userQQ=(EditText)findViewById(R.id.edit_text3);
        userWeibo=(EditText)findViewById(R.id.edit_text4);
        editPsw = (Button) findViewById(R.id.edit_psw);
        editCheck=(FloatingActionButton) findViewById(R.id.edit_check);
        app = (GlobalVariable) getApplication();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置初始值
        userName.setText(app.getUserName());
        userPhone.setText(app.getUserPhone());
        userQQ.setText(app.getQqNum());
        userWeibo.setText(app.getWbNum());


        //发送请求
        editCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myPhone = userPhone.getText().toString();
                String myPsw=app.getUserPsw();
                String myName=userName.getText().toString();
                String myQQ=userQQ.getText().toString();
                String myWeibo=userWeibo.getText().toString();

                Toast.makeText(changePasswordActivity.this,"test",Toast.LENGTH_SHORT);

                //构造发送的json数据
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("password", myPsw);
                    jsonObject.put("phonenum", myPhone);
                    jsonObject.put("username", myName);
                    jsonObject.put("qq", myQQ);
                    jsonObject.put("weibo", myWeibo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //发送请求
                HttpUtil.sendOkHttpRequest("http://97.64.21.155:8001/user/change/data", jsonObject, new okhttp3.Callback() {
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if(judgeState(responseData)){       //跳转到显示信息界面
                          Intent intent = new Intent(changePasswordActivity.this,showinfoActivity.class);
                          startActivity(intent);
                        }
                        else{
                            showa();
                        }

                    }
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                });
                app.setState(1);
                app.setUserName(myName);
                app.setUserPhone(myPhone);
                app.setUserPsw(myPsw);
                app.setQqNum(myQQ);
                app.setWbNum(myWeibo);
            }
        });
        //修改密码按钮跳转
        editPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(changePasswordActivity.this,passChangeActivity.class);
                startActivity(intent);
            }
        });
    }
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean judgeState(String responseData) {
        try{
            JSONObject jsonObject = new JSONObject(responseData);
            int state = jsonObject.getInt("state");
            if(state==200) {
                return true;
            }
            else return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void showa(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(changePasswordActivity.this,"修改个人信息失败",Toast.LENGTH_LONG).show();
            }
        });
    }

}
