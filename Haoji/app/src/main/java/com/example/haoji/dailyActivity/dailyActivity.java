package com.example.haoji.dailyActivity;

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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.support.design.widget.NavigationView;
import com.example.haoji.Button.DragFloatActionButton;

import com.example.haoji.GlobalVariable;
import com.example.haoji.R;
import com.example.haoji.userActivity.RegisterActivity;
import com.example.haoji.userActivity.login1Activity;
import com.example.haoji.Button.SectorMenuButton;
import com.example.haoji.Button.ButtonData;
import com.example.haoji.Button.ButtonEventListener;
import com.example.haoji.userActivity.showinfoActivity;
import com.example.haoji.dailyActivity.read_userName;
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
        //DragFloatActionButton addSchedule = (DragFloatActionButton) findViewById(R.id.addSchedule);
        //addSchedule.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View view) {
        // Intent intent = new Intent(dailyActivity.this ,newPlan.class);
        //startActivity(intent);
        // }
        // });
        FragmentChat chat = new FragmentChat();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg, chat).commit();
        OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview();
        getSupportFragmentManager().beginTransaction().replace(R.id.fg2, onedaylist).commit();
        RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab_menu);


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
                        OnedayFragmentRecyclerview onedaylist = new  OnedayFragmentRecyclerview();
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
                    initSpeech2();
                    //Intent intent = new Intent(dailyActivity.this ,newPlan.class);

                    //startActivity(intent);

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