package com.jia.jia.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.adapter.OrderAdapter;
import com.jia.jia.module.GetItem2;
import com.jia.jia.module.Goods;
import com.jia.jia.module.Order;
import com.jia.jia.module.Response;
import com.jia.jia.module.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter mAdapter;
    private ArrayList<Order> mOrderList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.recycler_view);


        mAdapter = new OrderAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OkHttpUtils
                        .get()
                        .url(MyApplication.baseUrl + "order/changeOrderState.do")
                        .addParams("orderId", String.valueOf(mOrderList.get(position).getId()))
                        .addParams("state", "103")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                LogUtils.i("-------------- aaaa" + e.toString());

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                LogUtils.i("-------------- aaaa" + response);
                                initData();
                            }
                        });
            }
        });

        initData();


    }

    private void initData(){
        OkHttpUtils
                .postString()
                .url(MyApplication.baseUrl + "/order/getOrderList.do")
                .content(new Gson().toJson(new User(Long.valueOf(CacheUtils.getInstance().getString("userId")))))
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
                        Type listType = new TypeToken<Response<ArrayList<Order>>>() {
                        }.getType();
                        Response<ArrayList<Order>> response1 = new Gson().fromJson(response, listType);


                        if (response1.getCode() == 0) {
                            ToastUtils.showShort("成功");

                        } else {
                            ToastUtils.showShort("失败");

                        }

                        mAdapter.setNewData(response1.getData());
                        mOrderList = response1.getData();
                    }
                });

    }

}
