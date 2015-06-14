package com.example.bmobdemo;

import cn.bmob.v3.BmobObject;

public class Action extends BmobObject {

    private static final long serialVersionUID = 1L;
    private String text;

    public void setText(String txt) {
        text = txt;
    }
    
    public String getText() {
        return text;
    }
}
