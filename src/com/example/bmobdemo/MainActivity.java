package com.example.bmobdemo;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity {

    public static String APPID = "fbc79e391d7442ff047c889805542a06";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Bmob.initialize(getApplicationContext(), APPID);
    }
    
    public void readAction(View view) {
        final BmobQuery<Action> query = new BmobQuery<Action>();
        query.setLimit(10);
        query.findObjects(getApplicationContext(), new FindListener<Action>() {
            @Override
            public void onSuccess(List<Action> list) {
                Toast.makeText(MainActivity.this, "size=" + list.size() + ", text=" + list.get(0).getText(), 
                        Toast.LENGTH_LONG).show();
            }
            
            @Override
            public void onError(int error, String msg) {
                Toast.makeText(MainActivity.this, "error=" + error + ", msg=" + msg, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    public void addAction(View view) {
        final Action action = new Action();
        action.setText("买车险: " + new Date(System.currentTimeMillis()));
        action.save(this, new SaveListener() {
            
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Add action=" + action.getText() + " Success!", Toast.LENGTH_LONG).show();
            }
            
            @Override
            public void onFailure(int error, String msg) {
                Toast.makeText(MainActivity.this, "Add action=" + action.getText() + " Failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
