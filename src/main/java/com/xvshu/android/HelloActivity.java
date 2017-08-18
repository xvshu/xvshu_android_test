package com.xvshu.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.xvshu.android.service.HelloService;


/**
 * Created by xvshu on 2017/7/26.
 */
public class HelloActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //得到按钮实例
        Button hellobtn = (Button)findViewById(R.id.hellobutton);
        //设置监听按钮点击事件
        hellobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到textview实例
                TextView hellotv = (TextView)findViewById(R.id.hellotextView);
                //弹出Toast提示按钮被点击了
                Toast.makeText(HelloActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
                //读取strings.xml定义的interact_message信息并写到textview上
                hellotv.setText("Hello android !");
            }
        });
    }

    // Method to start the service
    public void startService(View view) {
        startService(new Intent(getBaseContext(), HelloService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), HelloService.class));
    }

    public void showListView(View view) {
        Intent intent_hello = new Intent(this, ListViewDiarysActivity.class);
        startActivity(intent_hello);
    }

    public void backtologin(View view) {
        this.finish();
    }

    public void showCalc(View view) {
        Intent intent_calc = new Intent(this, CalcActivity.class);
        startActivity(intent_calc);
    }

    public void showWebMain(View view) {
        Intent intent_calc = new Intent(this, WebMainActivity.class);
        startActivity(intent_calc);
    }
    public void showWebRequest(View view) {
        Intent intent_calc = new Intent(this, WebRequestActivity.class);
        startActivity(intent_calc);
    }

    public void showretrofitview(View view) {
        Intent intent_calc = new Intent(this, RetrofitActivity.class);
        startActivity(intent_calc);
    }







}
