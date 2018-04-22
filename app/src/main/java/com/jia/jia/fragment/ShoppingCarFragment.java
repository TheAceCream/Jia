package com.jia.jia.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.CacheUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jia.jia.MyApplication;
import com.jia.jia.R;
import com.jia.jia.adapter.ShoppingCarAdapter;
import com.jia.jia.module.Goods;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class ShoppingCarFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button pay, delete;
    Gson gson = new Gson();
    private ShoppingCarAdapter mAdapter;
    protected boolean isCreate = false;
    List<Goods> goods = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_car, container, false);
        isCreate = true;
        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new ShoppingCarAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        JSONArray array = CacheUtils.getInstance().getJSONArray("goods");
        if (array != null) {
            goods = gson.fromJson(array.toString(),
                    new TypeToken<List<Goods>>() {
                    }.getType());
            mAdapter.setNewData(goods);
        }

        delete = view.findViewById(R.id.delete_it);
        pay = view.findViewById(R.id.take_it);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheUtils.getInstance().put("goods", new JSONArray());
                mAdapter.setNewData(new ArrayList<Goods>());
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                JSONArray array = CacheUtils.getInstance().getJSONArray("goods");
                if (array != null) {
                    goods = gson.fromJson(array.toString(),
                            new TypeToken<List<Goods>>() {
                            }.getType());
                }
                if (view.getId() == R.id.add) {

                    goods.get(position).setCounts(goods.get(position).getCounts() + 1);

                } else if (view.getId() == R.id.sub) {
                    int c = goods.get(position).getCounts();
                    if (c > 0) {
                        c = c - 1;
                    }
                    goods.get(position).setCounts(c);

                }

                mAdapter.setNewData(goods);

                CacheUtils.getInstance().put("goods", new Gson().toJson(goods));



            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONArray array = CacheUtils.getInstance().getJSONArray("goods");
                if (array == null) {
                    return;
                }

                new MaterialDialog.Builder(getActivity())
                        .title("请输入地址")
                        .content("地址:")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something

                                JSONArray array = CacheUtils.getInstance().getJSONArray("goods");
                                if (array != null) {
                                    List<Goods> goods = gson.fromJson(array.toString(),
                                            new TypeToken<List<Goods>>() {
                                            }.getType());

                                    for (int i = 0; i < goods.size(); i++) {

                                        goods.get(i).setOther(input.toString());
                                        OkHttpUtils
                                                .postString()
                                                .url(MyApplication.baseUrl + "/order/addOrder.do")
                                                .content(new Gson().toJson(goods.get(i)))
                                                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                                                .build()
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onError(Call call, Exception e, int id) {
                                                        LogUtils.i("失败" + e.toString());
                                                    }

                                                    @Override
                                                    public void onResponse(String response, int id) {
                                                        LogUtils.i("成功" + response.toString());


                                                    }
                                                });
                                    }
                                    CacheUtils.getInstance().put("goods", new JSONArray());
                                    mAdapter.setNewData(new ArrayList<Goods>());
                                }


                            }
                        }).show();


            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //getShoppingCar();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreate) {
            JSONArray array = CacheUtils.getInstance().getJSONArray("goods");
            if (array != null) {
                List<Goods> goods = gson.fromJson(array.toString(),
                        new TypeToken<List<Goods>>() {
                        }.getType());
                mAdapter.setNewData(goods);
            }

        }

    }

    //    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
//        @Override
//        public void onItemClick(View view, int position) {
////            Intent intent = new Intent(getActivity(), TravelNotesActivity.class);
////            intent.putExtra("id", position);
////            startActivity(intent);
//        }
//    };
//
//
//    public void getShoppingCar() {
//        String url = Url.url + "get_car_product";
//        SharedPreferences pref = getActivity().getSharedPreferences("user", MODE_PRIVATE);
//        String phone = pref.getString("user_phone", "null");
//        OkHttpUtils
//                .get().url(url)
//                .addParams("id", phone)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        Log.i("lin", "---lin---> 33" + e.toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        Log.i("lin", "---lin---> 11");
//                        try {
//                            JSONArray jsonArray = new JSONArray(response);
//
//
//                            List<GoodsModel> list = new ArrayList<GoodsModel>();
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                Log.i("lin", "---lin---->" + jsonArray.getJSONObject(i));
//                                GoodsModel obj = new GoodsModel(jsonArray.getJSONObject(i).getString("id"),
//                                        jsonArray.getJSONObject(i).getString("name"),
//                                        "" + jsonArray.getJSONObject(i).getString("price"),
//                                        jsonArray.getJSONObject(i).getString("type"),
//                                        jsonArray.getJSONObject(i).getString("img"),
//                                        jsonArray.getJSONObject(i).getString("details"));
//                                obj.setCount(jsonArray.getJSONObject(i).getString("count"));
//                                list.add(obj);
//                                mList = list;
//                            }
//                            adapter.refreshList(list);
//
//                            Map<Integer, Boolean> checked = adapter.getChecked();
//                            int money = 0;
//                            for (Map.Entry<Integer, Boolean> entry : checked.entrySet()) {
//                                if (entry.getValue()) {
//
//                                    money = money + Integer.parseInt(mList.get(entry.getKey()).getCount()) * Integer.parseInt(mList.get(entry.getKey()).getPrice().split(" ")[1]);
//
//                                }
//                                zongjia.setText("￥ "+money + "");
//
//                            }
//
//                        } catch (Exception e) {
//                            Log.i("lin", "---lin---> 22" + e.toString());
//
//                            zongjia.setText("￥ 0");
//
//                        }
//                    }
//                });
//    }

}





