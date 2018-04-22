package com.jia.jia.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.R;
import com.jia.jia.module.Goods;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class DetailsActivity extends AppCompatActivity {

    private TextView name, type, price, details, sell, button;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = findViewById(R.id.name);
        type = findViewById(R.id.type);
        price = findViewById(R.id.price);
        details = findViewById(R.id.details);
        sell = findViewById(R.id.sell);
        button = findViewById(R.id.add_to_car);
        img = findViewById(R.id.img);

        name.setText(getIntent().getExtras().getString("name"));
        type.setText(getIntent().getExtras().getString("type"));
        price.setText(getIntent().getExtras().getString("price"));
        details.setText(getIntent().getExtras().getString("details"));
        sell.setText(getIntent().getExtras().getString("sell"));

        Glide.with(this).load(getIntent().getExtras().getString("img")).into(img);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray array = CacheUtils.getInstance().getJSONArray("goods");
                if (array == null) {
                    List<Goods> goods = new ArrayList<Goods>();
                    Goods goods1 = new Goods();
                    goods1.setName(getIntent().getExtras().getString("name"));
                    goods1.setPrice(Double.valueOf(getIntent().getExtras().getString("price")));
                    goods1.setState(101);
                    goods1.setOther("");
                    goods1.setCounts(1);
                    goods1.setUserId(Long.valueOf(CacheUtils.getInstance().getString("userId")));
                    goods1.setImg(getIntent().getExtras().getString("img"));
                    goods1.setItemId(Long.valueOf(getIntent().getExtras().getString("id")));
                    goods.add(goods1);
                    CacheUtils.getInstance().put("goods", new Gson().toJson(goods));
                } else {
                    Gson gson = new Gson();
                    List<Goods> goods = gson.fromJson(array.toString(),
                            new TypeToken<List<Goods>>() {
                            }.getType());

                    boolean isHave = false;
                    int where = 0;
                    for (int i = 0; i < goods.size(); i++) {
                        if (goods.get(i).getItemId() == Long.valueOf(getIntent().getExtras().getString("id"))) {
                            isHave = true;
                            where = i;
                            break;
                        }
                    }

                    if (isHave) {
                        int count = goods.get(where).getCounts();
                        LogUtils.i("---------->  " + count);
                        goods.get(where).setCounts(count + 1);
                        LogUtils.i("---------->  " + goods.get(where).getCounts());
                        CacheUtils.getInstance().put("goods", new Gson().toJson(goods));

                    } else {
                        Goods goods1 = new Goods();
                        goods1.setName(getIntent().getExtras().getString("name"));
                        goods1.setPrice(Double.valueOf(getIntent().getExtras().getString("price")));
                        goods1.setState(101);
                        goods1.setOther("");
                        goods1.setCounts(1);
                        goods1.setUserId(Long.valueOf(CacheUtils.getInstance().getString("userId")));
                        goods1.setImg(getIntent().getExtras().getString("img"));

                        goods1.setItemId(Long.valueOf(getIntent().getExtras().getString("id")));
                        goods.add(goods1);

                        CacheUtils.getInstance().put("goods", new Gson().toJson(goods));
                    }


                }

                ToastUtils.showShort("添加成功");
                finish();
            }
        });


    }
}







