package com.jia.jia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.module.Critics;
import com.jia.jia.module.GetCritics;
import com.jia.jia.module.GetItem;
import com.jia.jia.module.Goods;
import com.jia.jia.module.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class DetailsActivity extends AppCompatActivity {

//    private TextView name, type, price, store , details, sell, star , button;
    private String name, type, price, store , details, sell, star ;
    private TextView button;
    private ImageView img;
    private ListView details_list;
    private List<Critics> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        button = findViewById(R.id.add_to_car);

        details_list = findViewById(R.id.details_list);
        img = findViewById(R.id.img);
        name = getIntent().getExtras().getString("name");
        type = getIntent().getExtras().getString("type");
        price = getIntent().getExtras().getString("price");
        store =getIntent().getExtras().getString("store");
        details= getIntent().getExtras().getString("details");
        sell = getIntent().getExtras().getString("sell");
        star = getIntent().getExtras().getString("star");
        String[] content = {"商品名称："+name,"商品类别："+type,"商品价格："+price,
                "库存："+store,"已售出："+sell,"综合评分："+star,"详细描述："+details,
                "                            点击查看商品评价"+"            "};
        if (getIntent().getExtras().getString("id")!=null){
            setIt(Long.valueOf(getIntent().getExtras().getString("id")));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailsActivity.this,
                R.layout.support_simple_spinner_dropdown_item,content);
        details_list.setAdapter(adapter);
        details_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                if (j==7){
                    //跳页面
                    Intent intent = new Intent(DetailsActivity.this, CriticActivity.class);
                    intent.putExtra("cri_content",(Serializable)mList);
                    startActivity(intent);
                }
            }
        });

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
                        goods.get(where).setCounts(count + 1);
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

    private void setIt(long flag) {
        OkHttpUtils
                .postString()
                .url(MyApplication.baseUrl + "/critic/getCriticList.do")
                .content(new Gson().toJson(new GetCritics(flag)))
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
                        Type listType = new TypeToken<Response<ArrayList<Critics>>>() {
                        }.getType();
                        Response<ArrayList<Critics>> response1 = new Gson().fromJson(response, listType);


                        if (response1.getCode() == 0) {
                            ToastUtils.showShort("成功");

                        } else {
                            ToastUtils.showShort("失败");

                        }
                        mList = response1.getData();

                    }
                });
    }
}







