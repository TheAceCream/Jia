package com.jia.jia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.activity.DetailsActivity;
import com.jia.jia.adapter.GoodsAdapter;
import com.jia.jia.module.GetItem;
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

public class SortFragment extends Fragment {

    private RadioButton bt0,bt1, bt2, bt3, bt4, bt5, bt6, bt7;
    private RecyclerView recyclerView;
    private GoodsAdapter mAdapter;
    private ArrayList<Goods> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);

        bt0 = view.findViewById(R.id.sort0);
        bt1 = view.findViewById(R.id.sort1);
        bt2 = view.findViewById(R.id.sort2);
        bt3 = view.findViewById(R.id.sort3);
        bt4 = view.findViewById(R.id.sort4);
        bt5 = view.findViewById(R.id.sort5);
        bt6 = view.findViewById(R.id.sort6);
        bt7 = view.findViewById(R.id.sort7);

        mAdapter = new GoodsAdapter();
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
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


        getStarList();

        bt0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStarList();
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIt(101);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIt(102);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIt(103);
            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIt(104);
            }
        });

        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIt(105);
            }
        });

        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIt(106);
            }
        });

        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setIt(107);
            }
        });

        return view;


    }

    private void setIt(int flag) {
        OkHttpUtils
                .postString()
                .url(MyApplication.baseUrl + "/item/getItemList.do")
                .content(new Gson().toJson(new GetItem(flag)))
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


                        if (response1.getCode() == 0) {
                            ToastUtils.showShort("成功");

                        } else {
                            ToastUtils.showShort("失败");

                        }
                        mList = response1.getData();
                        mAdapter.setNewData(response1.getData());

                    }
                });
    }

    private void getStarList(){
        OkHttpUtils
                .postString()
                .url(MyApplication.baseUrl + "/item/getItemListByStar.do")
                .content("{}")
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


                        if (response1.getCode() == 0) {
                            ToastUtils.showShort("成功");

                        } else {
                            ToastUtils.showShort("失败");

                        }
                        mList = response1.getData();
                        mAdapter.setNewData(response1.getData());

                    }
                });
    }

}