package com.xvshu.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


/**
 * Created by xvshu on 2017/8/7.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "login";
    Button loginBtn = null;
    EditText useridEt = null;
    EditText passEt = null;
    TextView promptText = null;
    CheckBox cbRememberPass=null;
    CheckBox cbAutoLogin=null;
    private SharedPreferences spUser=null;

    private String spKeyUser="UserName";
    private String spKeyPass="PassWord";
    private String spKeyRememberPass="REMEMBERPASS";
    private String spKeyAutoLogin="AUTOLOGIN";
    private String spName="userInfo";
    private String userName="admin";
    private String passWord="admin";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        useridEt = (EditText) findViewById(R.id.userId);
        passEt = (EditText) findViewById(R.id.pass);
        promptText = (TextView) findViewById(R.id.promptText);
        cbRememberPass=(CheckBox)findViewById(R.id.cb_rememberpass);
        cbAutoLogin=(CheckBox)findViewById(R.id.cb_autologin);
        spUser = this.getSharedPreferences(spName, Context.MODE_PRIVATE);

        //判断记住密码多选框的状态
        if(spUser.getBoolean(spKeyRememberPass, false))
        {
            //设置默认是记录密码状态
            cbRememberPass.setChecked(true);
            useridEt.setText(spUser.getString(spKeyUser, ""));
            passEt.setText(spUser.getString(spKeyPass, ""));
            //判断自动登陆多选框状态
            if(spUser.getBoolean(spKeyAutoLogin, false))
            {
                //设置默认是自动登录状态
                cbAutoLogin.setChecked(true);
                //跳转界面
                Intent intent_hello = new Intent(this, HelloActivity.class);
                startActivity(intent_hello);

            }
        }

        //监听记住密码多选框按钮事件
        cbRememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (cbRememberPass.isChecked()) {
                    spUser.edit().putBoolean(spKeyRememberPass, true).commit();
                }else {
                    spUser.edit().putBoolean(spKeyRememberPass, false).commit();
                }

            }
        });

        //监听自动登录多选框事件
        cbAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (cbAutoLogin.isChecked()) {
                    spUser.edit().putBoolean(spKeyAutoLogin, true).commit();
                } else {
                    spUser.edit().putBoolean(spKeyAutoLogin, false).commit();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        String userid = useridEt.getText().toString().trim();
        String pass = passEt.getText().toString().trim();
        if(userid.equals("")){
            promptText.setText("用户名为空");
            return ;
        }
        if(pass.equals("")){
            promptText.setText("密码为空");
            return ;
        }

        if (userid.equals(userName) && pass.equals(passWord)) {
            Toast.makeText(this, "登录成功！", Toast.LENGTH_LONG).show();

            if(cbRememberPass.isChecked()) {
                //记住用户名、密码、
                SharedPreferences.Editor editor = spUser.edit();
                editor.putString(spKeyUser, userid);
                editor.putString(spKeyPass,pass);
                editor.commit();
            }else{
                //不记住用户名、密码
                SharedPreferences.Editor editor = spUser.edit();
                editor.putString(spKeyUser, "");
                editor.putString(spKeyPass,"");
                editor.commit();
            }

            Intent intent_hello = new Intent(this, HelloActivity.class);
            startActivity(intent_hello);
        } else {
            Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
        }

    }
}
