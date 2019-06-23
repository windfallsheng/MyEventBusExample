package com.example.myeventbus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ProduceActivity extends AppCompatActivity {

    private static final String TAG = ProduceActivity.class.getSimpleName();
    private String userArray[] = {"Cyra", "Morgen", "Iris", "Mia"};
    private String messageArray[] = {"我发表了新的美食文章", "我更新了我的相册", "我在FaceBook申请了账号", "我做了一个好看的小视频"};

    public static void start(Context context) {
        context.startActivity(new Intent(context, ProduceActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "method:onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce);
        // 注册Otto
//        OttoBus.getInstance().register(this);
    }

//    @Produce
//    public EventData produceEventData() {
//        Log.i(TAG, "method:Produce#produceEventData");
//        int uIndex = (int) (Math.random() * userArray.length);
//        int mIndex = (int) (Math.random() * messageArray.length);
//        EventData eventData = new EventData(userArray[uIndex], messageArray[mIndex]);
//        Log.i(TAG, "method:Produce#produceEventData#eventData=" + eventData);
//        return eventData;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "method:onDestroy");
        // 注销Otto
//        OttoBus.getInstance().unregister(this);
    }
}
