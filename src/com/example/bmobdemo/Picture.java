package com.example.bmobdemo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Picture extends BmobObject {

    private static final long serialVersionUID = 1L;
    private BmobFile file;
    
    public BmobFile getFile() {
        return file;
    }
}
