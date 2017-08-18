package com.xvshu.android;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.xvshu.android.db.CalcHisrory;
import com.xvshu.android.db.SqliteManage;
import com.xvshu.android.web.UseService;
import com.xvshu.android.web.common.RetrofitUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Stack;


/**
 * Created by xvshu on 2017/7/26.
 */
public class RetrofitActivity extends Activity implements View.OnClickListener {
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
        setContentView(R.layout.retrofit_login);
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
        final String userid = useridEt.getText().toString().trim();
        final String pass = passEt.getText().toString().trim();
        if(userid.equals("")){
            promptText.setText("用户名为空");
            return ;
        }
        if(pass.equals("")){
            promptText.setText("密码为空");
            return ;
        }

        Boolean isLoginSucess = false;
        try {
            UseService useService = RetrofitUtils.genService(UseService.class);
            Call<String> repos  =useService.login(userid,pass);
            repos.enqueue(new Callback<String>() {
                              @Override
                              public void onResponse(Call<String> call, Response<String> response) {
                                  Log.i("loginOnFailure", response.body().toString());
                                  if (response.body().equals("true")) {
                                      Toast.makeText(RetrofitActivity.this, "Rest登录成功！", Toast.LENGTH_LONG).show();
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

                                      Intent intent_hello = new Intent(RetrofitActivity.this, HelloActivity.class);
                                      startActivity(intent_hello);
                                  } else {
                                      Toast.makeText(RetrofitActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                                  }

                              }

                              @Override
                              public void onFailure(Call<String> call, Throwable throwable) {
                                  Log.e("loginOnFailure", throwable.getMessage(),throwable);
                              }
                          }
            );

        }catch (Exception ex){
            Log.e("=RetrofitUtils=>","RetrofitUtils.genService(UseService.class)",ex);
            isLoginSucess=false;
        }


    }
}
