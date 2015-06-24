package com.example.bmobdemo;

import cn.bmob.v3.BmobObject;

public class Company extends BmobObject {

    private static final long serialVersionUID = 1L;
    private String name;
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
