package com.jia.jia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.adapter.GoodsAdapter;
import com.jia.jia.module.Critics;
import com.jia.jia.module.GetItem2;
import com.jia.jia.module.Goods;
import com.jia.jia.module.Response;
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

public class ResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GoodsAdapter mAdapter;
    private String searchText;
    private ArrayList<Goods> mList = new ArrayList<>();
    private ArrayList<Critics> cList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new GoodsAdapter();
        recyclerView.setAdapter(mAdapter);

        searchText = getIntent().getExtras().getString("key");

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //name, type, price, store ,details, sell, star
                Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
                intent.putExtra("name", mList.get(position).getName());
                intent.putExtra("type", mList.get(position).getSortName());
                intent.putExtra("price", String.valueOf(mList.get(position).getPrice()));
                intent.putExtra("store", String.valueOf(mList.get(position).getStore()));
                intent.putExtra("details", mList.get(position).getNote());
                intent.putExtra("sell", String.valueOf(mList.get(position).getSale()));
                intent.putExtra("star", String.valueOf(mList.get(position).getStar()));
                intent.putExtra("id", String.valueOf(mList.get(position).getId()));
                intent.putExtra("img", MyApplication.baseUrl + "img/" + mList.get(position).getImg());
                startActivity(intent);
            }
        });


        OkHttpUtils
                .postString()
                .url(MyApplication.baseUrl + "/item/getItemList.do")
                .content(new Gson().toJson(new GetItem2(searchText)))
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
                        Type listType = new TypeToken<Response<ArrayList<Goods>>>() {
                        }.getType();
                        Response<ArrayList<Goods>> response1 = new Gson().fromJson(response, listType);
                        mList = response1.getData();

                        LogUtils.i("成功!" + mList.toString());

                        if (response1.getCode() == 0) {
                            ToastUtils.showShort("成功");

                        } else {
                            ToastUtils.showShort("失败");

                        }

                        mAdapter.setNewData(response1.getData());

                    }
                });

        OkHttpUtils
                .postString()
                .url(MyApplication.baseUrl + "/critic/getCriticList.do")
                .content(new Gson().toJson(new GetItem2(searchText)))
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
                        Type listType = new TypeToken<Response<ArrayList<Goods>>>() {
                        }.getType();
                        Response<ArrayList<Goods>> response1 = new Gson().fromJson(response, listType);
                        mList = response1.getData();

                        LogUtils.i("成功!" + mList.toString());

                        if (response1.getCode() == 0) {
                            ToastUtils.showShort("成功");

                        } else {
                            ToastUtils.showShort("失败");

                        }

                        mAdapter.setNewData(response1.getData());

                    }
                });


    }
}
