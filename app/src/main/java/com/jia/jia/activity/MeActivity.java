package com.jia.jia.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.module.Response;
import com.jia.jia.module.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class MeActivity extends AppCompatActivity {

    private Button save;
    private EditText username, mobile, email, area;
    private RadioButton radio1, radio2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_me);

        save = findViewById(R.id.save);
        username = findViewById(R.id.username);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        area = findViewById(R.id.area);
        radio1 = findViewById(R.id.man);
        radio2 = findViewById(R.id.woman);

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio1.setChecked(true);
                radio2.setChecked(false);
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio2.setChecked(true);
                radio1.setChecked(false);
            }
        });

        radio1.setChecked(true);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int sex = 1;
                if (radio1.isChecked()) {
                    sex = 1;
                } else {
                    sex = 2;
                }

                User user = new User(Long.valueOf(CacheUtils.getInstance().getString("userId")), username.getText().toString(), sex, mobile.getText().toString(), email.getText().toString(), area.getText().toString());

                OkHttpUtils
                        .postString()
                        .url(MyApplication.baseUrl + "/user/updateUser.do")
                        .content(new Gson().toJson(user))
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
                                Response<String> response1 = new Gson().fromJson(response, Response.class);
                                if (response1.getCode() == 0) {
                                    ToastUtils.showShort("成功");
                                    finish();
                                } else {
                                    ToastUtils.showShort("失败");

                                }


                            }
                        });

            }
        });



        OkHttpUtils
                .get()
                .url(MyApplication.baseUrl + "user/getUserById.do")
                .addParams("id", CacheUtils.getInstance().getString("userId"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Type type = new TypeToken<Response<User>>() {
                        }.getType();
                        Response<User> response1 = new Gson().fromJson(response, type);
                        username.setText(response1.getData().getUsername());
                        mobile.setText(response1.getData().getMobile());
                        email.setText(response1.getData().getEmail());
                        area.setText(response1.getData().getArea());
                        if (response1.getData().getSex() == 1) {
                            radio1.setChecked(true);
                            radio2.setChecked(false);
                        } else {
                            radio1.setChecked(false);
                            radio2.setChecked(true);
                        }
                    }
                });


    }
}
