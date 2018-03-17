package com.jia.jia.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.module.Goods;
import com.jia.jia.module.Response;
import com.jia.jia.module.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class LoginActivity extends AppCompatActivity {

    private EditText phone, password;
    private Button login, register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils
                        .postString()
                        .url(MyApplication.baseUrl + "/user/login.do")
                        .content(new Gson().toJson(new User(phone.getText().toString(), password.getText().toString())))
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ToastUtils.showShort("失败");
                                LogUtils.i("失败" + e.toString());
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.i("成功" + response.toString());

                                Type type = new TypeToken<Response<User>>() {
                                }.getType();
                                Response<User> response1 = new Gson().fromJson(response, type);

                                if (response1.getCode() == 0) {
                                    ToastUtils.showShort("成功");


                                    CacheUtils.getInstance().put("userId", String.valueOf(response1.getData().getId()));
                                    CacheUtils.getInstance().put("username", String.valueOf(response1.getData().getUsername()));



                                    ActivityUtils.startActivity(MainActivity.class);
                                    finish();
                                } else {
                                    ToastUtils.showShort("失败");

                                }


                            }
                        });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(RegisterActivity.class);
            }
        });

    }
}
