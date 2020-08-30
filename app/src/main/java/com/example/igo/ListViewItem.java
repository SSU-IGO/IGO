package com.example.igo;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String nameStr ;
    private String phoneStr ;
    private String stateStr;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setName(String name) {
        nameStr = name ;
    }
    public void setPhone(String phone) {
        phoneStr = phone ;
    }

    public void setState(String state) {
        stateStr = state;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getName() {
        return this.nameStr ;
    }
    public String getPhone() {
        return this.phoneStr ;
    }

    public String getState() {
        return this.stateStr;
    }

    public ListViewItem(Drawable icon, String name, String phone, String state) {
        this.iconDrawable = icon;
        this.nameStr = name;
        this.phoneStr = phone;
        this.stateStr = state;
    }
}