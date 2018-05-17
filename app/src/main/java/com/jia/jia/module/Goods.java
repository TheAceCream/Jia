package com.jia.jia.module;

/**
 * Created by linSir
 * date at 2018/3/17.
 * describe:
 */

public class Goods {

    private String img;
    private String title;
    private double price;
    private double store;
    private int sale;
    private int sort;
    private long id;
    private String note;
    private String sortName;
    private long itemId;
    private long userId;
    private int counts;
    private String other;
    private String name;
    private int state;
    private double star;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Goods(){

    }

    public Goods(String img, String name, double price) {
        this.img = img;
        this.title = name;
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStore() {
        return store;
    }

    public void setStore(double store) {
        this.store = store;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", store=" + store +
                ", sale=" + sale +
                ", sort=" + sort +
                ", id=" + id +
                ", note='" + note + '\'' +
                ", sortName='" + sortName + '\'' +
                ", itemId=" + itemId +
                ", userId=" + userId +
                ", counts=" + counts +
                ", other='" + other + '\'' +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", star=" + star +
                '}';
    }
}
