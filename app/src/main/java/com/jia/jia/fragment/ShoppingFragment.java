package com.jia.jia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.GlideImageLoader;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.activity.DetailsActivity;
import com.jia.jia.activity.ResultActivity;
import com.jia.jia.module.Goods;
import com.jia.jia.module.Response;
import com.jia.jia.module.User;
import com.youth.banner.Banner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class ShoppingFragment extends Fragment {


    private Banner banner;
    private ImageView search;
    private EditText editText;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        editText = view.findViewById(R.id.edit_text);
        search = view.findViewById(R.id.search);
        imageView1 = view.findViewById(R.id.image_view1);
        imageView2 = view.findViewById(R.id.image_view2);
        imageView3 = view.findViewById(R.id.image_view3);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResultActivity.class);
                intent.putExtra("key", editText.getText().toString());
                startActivity(intent);
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils
                        .get()
                        .url(MyApplication.baseUrl + "item/getItemDetail.do")
                        .addParams("itemId", "2172387431389825")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }
                            //
                            @Override
                            public void onResponse(String response, int id) {
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                                intent.putExtra("","");
                                Type type = new TypeToken<Response<Goods>>() {}.getType();
                                Response<Goods> goods = new Gson().fromJson(response, type);
                                Goods good = goods.getData();
                                intent.putExtra("id",good.getId()+"");
                                intent.putExtra("name", good.getName());
                                intent.putExtra("type", good.getSortName());
                                intent.putExtra("price", good.getPrice()+"");
                                intent.putExtra("store",good.getStore()+"");
                                intent.putExtra("details", good.getNote());
                                intent.putExtra("sell", good.getSale()+"");
                                intent.putExtra("star", good.getStar()+"");
                                intent.putExtra("img", MyApplication.baseUrl + "img/" + goods.getData().getImg());
                                startActivity(intent);
                            }
                        });
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils
                        .get()
                        .url(MyApplication.baseUrl + "item/getItemDetail.do")
                        .addParams("itemId", "2259990083656064")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }
                            //
                            @Override
                            public void onResponse(String response, int id) {
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                                intent.putExtra("","");
                                Type type = new TypeToken<Response<Goods>>() {}.getType();
                                Response<Goods> goods = new Gson().fromJson(response, type);
                                Goods good = goods.getData();
                                intent.putExtra("id",good.getId()+"");
                                intent.putExtra("name", good.getName());
                                intent.putExtra("type", good.getSortName());
                                intent.putExtra("price", good.getPrice()+"");
                                intent.putExtra("store",good.getStore()+"");
                                intent.putExtra("details", good.getNote());
                                intent.putExtra("sell", good.getSale()+"");
                                intent.putExtra("star", good.getStar()+"");
                                intent.putExtra("img", MyApplication.baseUrl + "img/" + goods.getData().getImg());
                                startActivity(intent);
                            }
                        });
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils
                        .get()
                        .url(MyApplication.baseUrl + "item/getItemDetail.do")
                        .addParams("itemId", "2260078869092736")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }
                            //
                            @Override
                            public void onResponse(String response, int id) {
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                                intent.putExtra("","");
                                Type type = new TypeToken<Response<Goods>>() {}.getType();
                                Response<Goods> goods = new Gson().fromJson(response, type);
                                Goods good = goods.getData();
                                intent.putExtra("id",good.getId()+"");
                                intent.putExtra("name", good.getName());
                                intent.putExtra("type", good.getSortName());
                                intent.putExtra("price", good.getPrice()+"");
                                intent.putExtra("store",good.getStore()+"");
                                intent.putExtra("details", good.getNote());
                                intent.putExtra("sell", good.getSale()+"");
                                intent.putExtra("star", good.getStar()+"");
                                intent.putExtra("img", MyApplication.baseUrl + "img/" + goods.getData().getImg());
                                startActivity(intent);
                            }
                        });
            }
        });

        Banner banner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.img1);
        images.add(R.mipmap.img2);
        images.add(R.mipmap.img3);
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用

        banner.start();


        return view;

    }
}
