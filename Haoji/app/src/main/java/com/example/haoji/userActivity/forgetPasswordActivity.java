package com.example.haoji.userActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haoji.Button.ButtonData;
import com.example.haoji.HttpUtil;
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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.haoji.R;



public class forgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userPhone;//手机号/用户名
    private EditText editCode;//验证码
    private EditText newPassWord;//新密码
    private EditText reNewPassWord;//重复新密码
    private Button getCode;//获取验证码
    private Button confirm;//确定
    private int i = 60;//倒计时
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        userPhone = (EditText) findViewById(R.id.userName2);
        editCode = (EditText) findViewById(R.id.edit_text);
        newPassWord = (EditText) findViewById(R.id.newpassWord1);
        reNewPassWord = (EditText) findViewById(R.id.newpassWord2);
        getCode = (Button) findViewById(R.id.getCode);
        confirm = (Button) findViewById(R.id.modifyPsw);
        getCode.setOnClickListener(this);
        confirm.setOnClickListener(this);
        init();
    }
    private void init(){
        EventHandler eventHandler1 = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler1.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler1);
    }

    @Override
    public void onClick(View v) {
        String phoneNum = userPhone.getText().toString();
        // String passWord = password.getText().toString();
        switch (v.getId()) {
            case R.id.getCode:
                // 1. 通过规则判断手机号
                if (!judgephoneNum(phoneNum)) {
                    return;
                } // 2. 通过sdk请求获取短信验证码
                SMSSDK.getVerificationCode("86", phoneNum);

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                getCode.setClickable(false);
                getCode.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler1.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler1.sendEmptyMessage(-8);
                    }
                }).start();
                break;

            case R.id.modifyPsw:
                //提交短信验证码，在监听中返回
                SMSSDK.submitVerificationCode("86", phoneNum, editCode
                        .getText().toString());
                break;
        }
    }

    Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            String phoneNum = userPhone.getText().toString();
            String passWord = newPassWord.getText().toString();
            if (msg.what == -9) {
                getCode.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                getCode.setText("获取验证码");
                getCode.setClickable(true);
                i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功，提交注册
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        if (isValid()&&isSame())
                        //构造JSONObject类型的数据
                        {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("username",phoneNum);
                                jsonObject.put("newpassword",passWord);
                                jsonObject.put("phonenum",phoneNum);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //发送请求
                            HttpUtil.sendOkHttpRequest("http://97.64.21.155:8001/user/reset/password", jsonObject, new okhttp3.Callback() {
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseData = response.body().string();
                                    if(judgeState(responseData)){
                                        //showres(responseData);
                                        Intent intent = new Intent(forgetPasswordActivity.this,
                                                login1Activity.class);
                                        startActivity(intent);
                                        //finish();
                                    }
                                    else{
                                        errorTip();
                                    }
                                }
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                }
                            });

                        }

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
                if (result == SMSSDK.RESULT_ERROR) {
                    Toast.makeText(getApplicationContext(), "验证码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    /**
     * 判断手机号码是否合理
     *
     * @param phoneNum
     */
    private boolean judgephoneNum(String phoneNum) {
        if (isMatchLength(phoneNum, 11)
                && isMobileNO(phoneNum)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！",Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }
    public boolean isValid(){
        if (newPassWord.getText().toString().trim().equals("")&&reNewPassWord.getText().toString().trim().equals("")) {
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isSame(){
        if(newPassWord.getText().toString().trim().equals(reNewPassWord.getText().toString().trim()))
            return true;
        else{
            Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();
            return false;
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

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }
    //    private void show (final String s){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_LONG ).show() ;
//            }
//        }) ;
//    }
    private void errorTip (){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(forgetPasswordActivity.this,"找回密码失败",Toast.LENGTH_LONG ).show() ;
            }
        }) ;
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
