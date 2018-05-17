package com.jia.jia.module;

import java.io.Serializable;

/**
 * Created by AceCream on 2018/5/7.
 */

public class Critics implements Serializable{

    private String username;

    private int star;

    private String note;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Critics{" +
                "username='" + username + '\'' +
                ", star=" + star +
                ", note='" + note + '\'' +
                '}';
    }
}
