package com.example.haoji.dailyActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;
import android.os.Handler;
import android.app.Activity;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


import com.example.haoji.GlobalVariable;
import com.example.haoji.R;
import com.example.haoji.userActivity.login1Activity;
import com.example.haoji.Button.SectorMenuButton;
import com.example.haoji.Button.ButtonData;
import com.example.haoji.Button.ButtonEventListener;
import com.example.haoji.userActivity.showinfoActivity;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;


import java.util.ArrayList;
import java.util.List;


public class dailyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String imgPath;
    private Handler handler;
    private int year;
    private int month;
    private int day;
    TextView dateChoose;
    private TextView DAY;
    private int year1;
    private int month1;
    private int day1;
    private Calendar c;
    private String s;
    private String[] ss;
    private int y,m,d;
    final int DATE_PICKER = 0;
    private static final int IMAGE = 1;
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();
    private GlobalVariable app;
    private TextView textView;
    //private Intent intent = new Intent(dailyActivity.this ,newPlan.class);
    private void st()
    {
        String str;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidebar);
        //申明appid
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5a33bfff");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initBottomSectorMenuButton();
        DAY  = (TextView) findViewById(R.id.day);

        c = Calendar.getInstance();
        Date d1 = c.getTime();
       /* TextView tv=new TextView();
        tv.setText(str);*/
        String str = toString(d1);
        DAY.setText(str);
        DAY.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                showDialog(DATE_PICKER);
                    //Calendar c = Calendar.getInstance();
                    // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                    new DatePickerDialog(dailyActivity.this,
                            // 绑定监听器
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    DAY.setText(year + "-" + (monthOfYear+1)
                                            + "-" + dayOfMonth);
                                    y = year;
                                    m = monthOfYear;
                                    d = dayOfMonth;
                                }
                            }
                            // 设置初始日期
                            , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                            .get(Calendar.DAY_OF_MONTH)).show();
//                datepicker.init(year, month-1, day, new DatePicker.OnDateChangedListener() {
//                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
//                        DAY.setText(i+"-"+i1+"-"+i2);
//                    }
//                });
                OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview(y,m,d);
                getSupportFragmentManager().beginTransaction().replace(R.id.fg2, onedaylist).commit();

                }
            });
//        s = DAY.getText().toString();
//        ss = s.split("-");
//        y = Integer.parseInt(ss[0]);
//        m = Integer.parseInt(ss[1]);
//        d = Integer.parseInt(ss[2]);
        FragmentChat chat = new FragmentChat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
        OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview(y,m,d);
        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, onedaylist).commit();
        RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab_menu);

        //Handler处理子进程获取的数据
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("value");
                Intent intent = new Intent(dailyActivity.this ,newPlan.class);
                intent.putExtra("from", "Main");
                intent.putExtra("txt",val);
                startActivity(intent);
            }
        };

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        app = (GlobalVariable) getApplication();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        textView = (TextView) headerView.findViewById(R.id.userNameSider);
        if(app.getState()==0)
            textView.setText("未登陆");
        else
        textView.setText(app.getUserName());

        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rbOneday:
                        FragmentChat chat = new FragmentChat();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
                        OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview(y,m,d);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, onedaylist).commit();
                        break;
                    case R.id.rbThreeDay:
                        FragmentThreeDay threeday=new FragmentThreeDay();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg,threeday).commit();
                        ThreedayFragmentRecyclerview threedaylist = new  ThreedayFragmentRecyclerview();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, threedaylist).commit();
                        break;
                    case R.id.rbWeek:
                        FragmentWeek week = new FragmentWeek();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg,week).commit();
                        WeekFragmentRecyclerview weeklist = new  WeekFragmentRecyclerview();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, weeklist).commit();
                        break;

                    case R.id.rbMonth:
                        FragmentMonth month = new FragmentMonth();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, month)
                                .commit();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        initBottomSectorMenuButton();
        FragmentChat chat = new FragmentChat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
        OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview(y,m,d);
        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, onedaylist).commit();
        RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        app = (GlobalVariable) getApplication();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        textView = (TextView) headerView.findViewById(R.id.userNameSider);
        if(app.getState()==0)
            textView.setText("未登陆");
        else
            textView.setText(app.getUserName());

        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case R.id.rbOneday:
                        FragmentChat chat = new FragmentChat();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
                        OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview(y,m,d);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, onedaylist).commit();
                        break;
                    case R.id.rbThreeDay:
                        FragmentThreeDay threeday=new FragmentThreeDay();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg,threeday).commit();
                        ThreedayFragmentRecyclerview threedaylist = new  ThreedayFragmentRecyclerview();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, threedaylist).commit();
                        break;
                    case R.id.rbWeek:
                        FragmentWeek week = new FragmentWeek();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg,week).commit();
                        WeekFragmentRecyclerview weeklist = new  WeekFragmentRecyclerview();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, weeklist).commit();
                        break;

                    case R.id.rbMonth:
                        FragmentMonth month = new FragmentMonth();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fg, month)
                                .commit();
                        break;
                    default:
                        break;
                }

            }
        });
    }


    private void initBottomSectorMenuButton() {
        SectorMenuButton sectorMenuButton = (SectorMenuButton) findViewById(R.id.bottom_sector_menu);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.mipmap.add, R.mipmap.speak,
                R.mipmap.dynamic, R.mipmap.text};
        for (int i = 0; i < 4; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonData.setBackgroundColorId(this, R.color.colorAccent);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        setListener(sectorMenuButton);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imgPath = c.getString(columnIndex);
            c.close();
            //生成发送OCR请求子进程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(imgPath);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"),file);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("apikey","b5a900dce088957")
                            .addFormDataPart("file","image.jpg",fileBody)
                            .addFormDataPart("language","chs")
                            .build();
                    Request request = new Request.Builder()
                            .url("https://api.ocr.space/parse/image")
                            .post(requestBody)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        String jsonString = response.body().string();
                        try {
                            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("ParsedResults");
                            String jsonString1 = jsonArray.getString(0);
                            String jsonString2 = new JSONObject(jsonString1).getString("ParsedText");
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putString("value", jsonString2);
                            msg.setData(data);
                            handler.sendMessage(msg);
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void setListener(final SectorMenuButton button) {

        button.setButtonEventListener(new ButtonEventListener() {

            @Override

            public void onButtonClicked(int index) {

                int buttonid = index;

                if (buttonid == 1) {
                    //Intent intent = new Intent(dailyActivity.this ,login1Activity.class);
                     //intent.putExtra("from","dailyActivity");
                     initSpeech( dailyActivity.this);
                    // intent.putExtra("txt",test);
                     // startActivity(intent);

                    //initSpeech(getBaseContext());
                    //Intent intent = new Intent(dailyActivity.this ,newPlan.class);
                    //intent = getIntent()
                    //startActivity(intent);


                }

                if (buttonid == 3) {
                    Intent intent = new Intent(dailyActivity.this ,newPlan.class);
                      intent.putExtra("from", "Main");//调用的时候要把"Main"改成其他的就行
                //    String test="啊啊啊啊2017年05月10日12时18分啊啊啊啊";严格按照这个格式，前导零不能没有且年份长度为4，其他长度为2
                //    intent.putExtra("txt",test);//通过这个传递数据
                    startActivity(intent);
                }
                //TODO 调用语音识别,语音识别调用newPlan
                if (buttonid == 1){

                }

                if(buttonid == 2){
                    //调用相册
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE);
                }
            }



            @Override

            public void onExpand() {

            }



            @Override

            public void onCollapse() {

            }

        });

    }
    ///测试代码
    public void initSpeech2( ) {
        Intent intent = new Intent(dailyActivity.this ,newPlan.class);

        startActivity(intent);
    }
    public void initSpeech( Context context) {
        final String test;
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (!isLast) {
                    //解析语音
                    Intent intent = new Intent(dailyActivity.this ,newPlan.class);
                    intent.putExtra("from","dailyActivity");
                    String test= parseVoice(recognizerResult.getResultString());
                    intent.putExtra("txt",test);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();

    }

    /**
     * 解析语音json
     */
    public String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

    /**
     * 语音对象封装
     */
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }



    private void showToast(String text) {
        Toast.makeText(dailyActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



//    protected Dialog onCreateDialog(int id){
//        switch(id){
//            case DATE_PICKER:
//                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener(){
//                    @Override
//                    public void onDateSet(DatePicker view, int _year, int _month, int _day){
//                        year1 = _year;
//                        month1 = _month+1;
//                        day1 = _day;
//                        DAY.setText(year1+"-"+month1+"-"+day1);
//                    }
//                };
//                return new DatePickerDialog(this,dateListener,year1,month1,day1);
//
//
//        }
////        app = (GlobalVariable) getApplication();
////        app = GlobalVariable.getInstance();
////        app.setDay(day1);
////        app.setYear(year1);
////        app.setMonth(month1);
//        return null;
//    }
    public static String toString(Date date) {

        String time;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd");
        time = formater.format(date);
        return time;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        app = (GlobalVariable) getApplication();
        int id = item.getItemId();
        if (id == R.id.info) {
            if(app.getState()==0){//未登录状态
                Intent intent = new Intent(dailyActivity.this ,login1Activity.class);
                startActivity(intent);
            }
            else{//已登录状态
                Intent intent = new Intent(dailyActivity.this ,showinfoActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.analyse) {
            Intent intent = new Intent(dailyActivity.this ,pieChartActivity.class);
            startActivity(intent);

        } else if (id == R.id.refresh) {

        } else if (id == R.id.setting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}