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
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.module.Response;
import com.jia.jia.module.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, password2;
    private Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!password.getText().toString().equals(password2.getText().toString())) {
                    ToastUtils.showShort("两次密码不一致");
                    return;
                }

                OkHttpUtils
                        .postString()
                        .url(MyApplication.baseUrl + "/user/addUser.do")
                        .content(new Gson().toJson(new User(username.getText().toString(), password.getText().toString())))
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
                                    ActivityUtils.startActivity(LoginActivity.class);
                                } else {
                                    ToastUtils.showShort("失败");

                                }


                            }
                        });

            }
        });


    }
}
