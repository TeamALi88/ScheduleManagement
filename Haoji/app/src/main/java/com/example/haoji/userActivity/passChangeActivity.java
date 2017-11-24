package com.example.haoji.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haoji.GlobalVariable;
import com.example.haoji.HttpUtil;
import com.example.haoji.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class passChangeActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private GlobalVariable app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_change);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText1 = (EditText) findViewById(R.id.edit_newPassword);
        editText2 = (EditText) findViewById(R.id.reedit_Password);
        editText3 = (EditText) findViewById(R.id.edit_oldPassword);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button button = (Button) findViewById(R.id.confirm);
        button.setOnClickListener(this);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                String newpassword = editText1.getText().toString();
                String resnewpassword = editText2.getText().toString();
                String oldpassword = editText3.getText().toString();
                app = (GlobalVariable) getApplication();
                String phonenum = app.getUserPhone();
//                Toast.makeText(passChangeActivity.this,phonenum,Toast.LENGTH_LONG).show();
//                String inputText1 = editText1.getText().toString();
//                String inputText2 = editText2.getText().toString();
//                //构造JSONObject类型的数据

                if (resnewpassword.equals(newpassword)) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("newpassword", resnewpassword);
                    jsonObject.put("phonenum", phonenum);
                    jsonObject.put("oldpassword",oldpassword);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                //发送请求
                HttpUtil.sendOkHttpRequest("http://97.64.21.155:8001/user/change/password", jsonObject, new okhttp3.Callback() {
                    public void onResponse(okhttp3.Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        //showa(responseData);
                        if(judgeState(responseData)){
                              successTip();
                              Intent intent = new Intent(passChangeActivity.this,
                        showinfoActivity.class);
                              startActivity(intent);
                        }
                        else{
                            InputOldpswError();
                        }
//                        else{
//                            Toast.makeText(passChangeActivity.this,"注册失败",Toast.LENGTH_LONG);
//                        }
                    }

                    public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                    }
                });
              }
              else{
                    InputpswError();
                }
                break;
            default:
                break;
        }
    }

    private boolean judgeState(String responseData) {
        try{
            JSONObject jsonObject = new JSONObject(responseData);
            int state = jsonObject.getInt("state");
            if(state==200) return true;
            else return false;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void showa( final String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(passChangeActivity.this,s,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void InputpswError(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(passChangeActivity.this,"两次密码输入不一致",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void InputOldpswError(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(passChangeActivity.this,"原密码输入错误",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void successTip(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(passChangeActivity.this,"修改成功",Toast.LENGTH_LONG).show();
            }
        });
    }

}
