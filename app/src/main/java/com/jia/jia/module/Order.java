package com.jia.jia.module;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class Order {

    private long id;

    private long itemId;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    private String name;
    private double price;
    private String other;
    private int counts;
    private int state;

    public Order(String name, double price, String other, int count, int state) {
        this.name = name;
        this.price = price;
        this.other = other;
        this.counts = count;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getCount() {
        return counts;
    }

    public void setCount(int count) {
        this.counts = count;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
