package com.jia.jia.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.adapter.OrderAdapter;
import com.jia.jia.module.Evaluate;
import com.jia.jia.module.Order;
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

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter mAdapter;
    private ArrayList<Order> mOrderList = new ArrayList<>();
    private Dialog getCodeDialog;
    public float pingfen = 0;

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


                if (mOrderList.get(position).getState() == 104) {

                    showGetCodeDialog(OrderActivity.this, position);

                } else if (mOrderList.get(position).getState() == 102) {
                    OkHttpUtils
                            .get()
                            .url(MyApplication.baseUrl + "order/changeOrderState.do")
                            .addParams("orderId", String.valueOf(mOrderList.get(position).getId()))
                            .addParams("state", "104")
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


            }
        });

        initData();


    }

    private void initData() {
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


    private void showGetCodeDialog(Context mContext, final int position) {

        final AlertDialog.Builder builder2 = new AlertDialog.Builder(mContext);
        View mView = LayoutInflater.from(mContext).inflate(R.layout.roting_bar, null);

        RatingBar ratingBar = (RatingBar) mView.findViewById(R.id.bar);
        final EditText editText = mView.findViewById(R.id.edit_text);
        Button button = mView.findViewById(R.id.button);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                pingfen = v;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCodeDialog.dismiss();

                String userId = CacheUtils.getInstance().getString("userId");
                long userId2 = Long.parseLong(userId);


                OkHttpUtils
                        .postString()
                        .url(MyApplication.baseUrl + "/critic/addCritic.do")
                        .content(new Gson().toJson(new Evaluate(mOrderList.get(position).getItemId(),
                                userId2,
                                pingfen, editText.getText().toString())))
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
                                ToastUtils.showShort("评论成功");
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
                                initData();
                            }
                        });


            }
        });

        builder2.setView(mView);
        builder2.setCancelable(false);
        getCodeDialog = builder2.create();
        getCodeDialog.show();

    }

}
