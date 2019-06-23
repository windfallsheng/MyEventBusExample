package com.example.myeventbus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class PostActivity extends AppCompatActivity {

    private static final String TAG = PostActivity.class.getSimpleName();
    private Button mBtnSendEventData;
    private String userArray[] = {"Cyra", "Morgen", "Iris", "Mia"};
    private String messageArray[] = {"我发表了新的美食文章", "我更新了我的相册", "我在FaceBook申请了账号", "我做了一个好看的小视频"};

    public static void start(Context context) {
        context.startActivity(new Intent(context, PostActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "method:onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mBtnSendEventData = (Button) this.findViewById(R.id.btn_send_event_data);
        mBtnSendEventData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uIndex = (int) (Math.random() * userArray.length);
                int mIndex = (int) (Math.random() * messageArray.length);
                EventData eventData = new EventData(userArray[uIndex], messageArray[mIndex]);
                Log.i(TAG, "method:onCreate#mBtnSendEventData#onClick#eventData="+eventData);
                EventBus.getDefault().post(eventData);
//                finish();
            }
        });
    }
}
