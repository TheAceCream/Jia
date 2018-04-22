package com.jia.jia.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jia.jia.R;
import com.jia.jia.module.Order;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class OrderAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {
    public OrderAdapter() {
        super(R.layout.item_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, Order item) {

        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.price, String.valueOf(item.getPrice()));
        helper.setText(R.id.other, String.valueOf(item.getOther()));
        helper.setText(R.id.count, String.valueOf(item.getCount()));
        helper.addOnClickListener(R.id.take_it);



        switch (item.getState()) {
            case 101:
                helper.setText(R.id.state, "已支付");
                helper.setVisible(R.id.state, true);
                helper.setVisible(R.id.take_it, false);

                break;

            case 102:
                helper.setVisible(R.id.state, false);
                helper.setVisible(R.id.take_it, true);
                helper.addOnClickListener(R.id.take_it);
                break;

            case 103:
                helper.setText(R.id.state, "已确认收货");
                helper.setVisible(R.id.state, true);
                helper.setVisible(R.id.take_it, false);

                break;

            case 104:
                helper.setVisible(R.id.state, false);
                helper.setVisible(R.id.take_it, true);
                helper.setText(R.id.take_it, "待评价");

                break;


        }

    }
}

