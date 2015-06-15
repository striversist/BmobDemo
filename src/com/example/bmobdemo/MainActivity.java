package com.example.bmobdemo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity {

    public static String APPID = "fbc79e391d7442ff047c889805542a06";
    private Handler mUiHandler = new Handler();
    private TextView mLoggerFlowTextView;
    private ScrollView mLoggerScrollView;
    private int mFlowIndex = 0;
    private SpannableStringBuilder mFlowStringBuilder = new SpannableStringBuilder();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mLoggerFlowTextView = (TextView) findViewById(R.id.logger_flow_tv);
        mLoggerScrollView = (ScrollView) findViewById(R.id.logger_sv);
        
        Bmob.initialize(getApplicationContext(), APPID);
    }
    
    public void readAction(View view) {
        final BmobQuery<Action> query = new BmobQuery<Action>();
        query.setLimit(10);
        query.findObjects(getApplicationContext(), new FindListener<Action>() {
            @Override
            public void onSuccess(List<Action> list) {
                addFlow("Success size=" + list.size(), Color.GREEN);
                for (Action action : list) {
                    addFlow("id=" + action.getObjectId() + ", text=" + action.getText());
                }
            }
            
            @Override
            public void onError(int error, String msg) {
                addFlow("error=" + error + ", msg=" + msg, Color.RED);
            }
        });
    }
    
    public void addAction(View view) {
        final Action action = new Action();
        action.setText("买车险: " + new Date(System.currentTimeMillis()));
        action.save(this, new SaveListener() {
            
            @Override
            public void onSuccess() {
                addFlow("Add action=" + action.getText() + " Success!");
            }
            
            @Override
            public void onFailure(int error, String msg) {
                addFlow("Add action=" + action.getText() + " Failed!", Color.RED);
            }
        });
    }

    // ----------------------- UI辅助类 Start --------------------------
    private void addFlow(String flow) {
        if (!TextUtils.isEmpty(flow)) {
            addFlow(flow, Color.WHITE);
        }
    }
    
    private void addFlow(String flow, int color) {
        String rowText = String.valueOf(++mFlowIndex) + ". " + flow;
        SpannableString ss = new SpannableString(rowText);
        ss.setSpan(new ForegroundColorSpan(color), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        mFlowStringBuilder.append(ss);
        mFlowStringBuilder.append("\n");
        mUiHandler.post(new Runnable() {  
            @Override
            public void run() {
                mLoggerFlowTextView.setText(mFlowStringBuilder);
            }
        });
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoggerScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 100);    // 待ScrollView中的内容全部显示出来，才会有效（否则无效果）。故延时一会儿
    }
    
    private interface SingleChoiceListener {
        public void onItemSeleted(String item);
    }
    private void showSingleChoiceDialog(final String[] items, final SingleChoiceListener listener) {
        if (items == null || items.length == 0 || listener == null)
            return;
        
        final ArrayList<Integer> choice = new ArrayList<Integer>();
        choice.add(0);
        new AlertDialog.Builder(this)
            .setSingleChoiceItems(items, 0, 
                    new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            choice.set(0, which);
                        }
                    })
            .setPositiveButton("OK", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String dirName = items[choice.get(0)];
                    listener.onItemSeleted(dirName);
                    dialog.dismiss();
                }
            })
            .setNegativeButton("Cancel", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .create().show();
    }
    // ----------------------- UI辅助类 End --------------------------
}
