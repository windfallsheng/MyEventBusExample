package com.windfallsheng.myeventbus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class PostActivity extends AppCompatActivity {

    private static final String TAG = PostActivity.class.getSimpleName();
    private TextView mTvMain, mTvMainThread, mTvPosting, mTvPostingThead;
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
        EventBus.getDefault().register(this);
        mTvMain = (TextView) this.findViewById(R.id.textview_main);
        mTvMainThread = (TextView) this.findViewById(R.id.textview_main_thread);
        mTvPosting = (TextView) this.findViewById(R.id.textview_posting);
        mTvPostingThead = (TextView) this.findViewById(R.id.textview_posting_thread);
        mTvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uIndex = (int) (Math.random() * userArray.length);
                int mIndex = (int) (Math.random() * messageArray.length);
                EventData eventData = new EventData(userArray[uIndex], messageArray[mIndex]);
                Log.i(TAG, "method:onCreate#mBtnSendEventData#onClick#eventData=" + eventData);
                Log.i(TAG, "method:onCreate#mTvMain#onClick#currentThread=" + Thread.currentThread());
                EventBus.getDefault().post(eventData);
            }
        });
        mTvMainThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uIndex = (int) (Math.random() * userArray.length);
                int mIndex = (int) (Math.random() * messageArray.length);
                final EventData eventData = new EventData(userArray[uIndex], messageArray[mIndex]);
                Log.i(TAG, "method:onCreate#mTvMainThread#onClick#eventData=" + eventData);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "method:onCreate#mTvMainThread#onClick#currentThread=" + Thread.currentThread());
                        EventBus.getDefault().post(eventData);
                    }
                }).start();
            }
        });
        mTvPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uIndex = (int) (Math.random() * userArray.length);
                int mIndex = (int) (Math.random() * messageArray.length);
                final String message = userArray[uIndex] + ":" + messageArray[mIndex];
                Log.i(TAG, "method:onCreate#mTvPosting#onClick#message=" + message);
                Log.i(TAG, "method:onCreate#mTvPosting#onClick#currentThread=" + Thread.currentThread());
                EventBus.getDefault().post(message);
            }
        });
        mTvPostingThead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uIndex = (int) (Math.random() * userArray.length);
                int mIndex = (int) (Math.random() * messageArray.length);
                final String message = userArray[uIndex] + ":" + messageArray[mIndex];
                Log.i(TAG, "method:onCreate#mTvPostingThead#onClick#message=" + message);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "method:onCreate#mTvPostingThead#onClick#currentThread=" + Thread.currentThread());
                        EventBus.getDefault().post(message);
                    }
                }).start();
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(String message) {
        Log.i(TAG, "method:onMessageEvent#currentThread=" + Thread.currentThread());
        Log.i(TAG, "method:onMessageEvent#message=" + message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
