package com.windfallsheng.myeventbus;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class StickyActivity extends AppCompatActivity {

    private static final String TAG = StickyActivity.class.getSimpleName();

    private TextView mTvMessage;

    public static void start(Context context) {
        context.startActivity(new Intent(context, StickyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        mTvMessage = (TextView) this.findViewById(R.id.tv_message);
        EventBus.getDefault().register(this);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void refreshMessage(final EventData eventData) {
        Log.i(TAG, "method:refreshMessage#eventData=" + eventData);
        mTvMessage.setText("已经接收到 eventData=" + eventData.toString() + "， 延时3000毫秒刷新UI");
        Log.i(TAG, "method:refreshMessage#已经接收到 eventData=" + eventData.toString() + "， 延时3000毫秒刷新UI");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTvMessage.setText(eventData.getUserName() + ":\n\n" + eventData.getMessage());
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
