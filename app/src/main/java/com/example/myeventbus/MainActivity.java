package com.example.myeventbus;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button mBtnJumpToPost, mBtnJumpToProduce, mBtnPostStickyEvent;
    private TextView mTvMessage;
    private String userArray[] = {"Cyra", "Morgen", "Iris", "Mia"};
    private String messageArray[] = {"我发表了新的美食文章", "我更新了我的相册", "我在FaceBook申请了账号", "我做了一个好看的小视频"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvMessage = (TextView) this.findViewById(R.id.tv_message);
        mBtnJumpToPost = (Button) this.findViewById(R.id.btn_jump_to_post);
        mBtnJumpToProduce = (Button) this.findViewById(R.id.btn_jump_to_produce);
        mBtnPostStickyEvent = (Button) this.findViewById(R.id.btn_post_sticky_event);
        mBtnJumpToPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.start(MainActivity.this);
            }
        });
        mBtnJumpToProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProduceActivity.start(MainActivity.this);
            }
        });
        mBtnPostStickyEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uIndex = (int) (Math.random() * userArray.length);
                int mIndex = (int) (Math.random() * messageArray.length);
                final EventData eventData = new EventData(userArray[uIndex], messageArray[mIndex]);
                Log.i(TAG, "method:onCreate#mBtnPostStickyEvent#onClick#eventData=" + eventData);
                StickyActivity.start(MainActivity.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "method:onCreate#mBtnPostStickyEvent#onClick#postDelayed#eventData=" + eventData);
                        EventBus.getDefault().postSticky(eventData);
                    }
                }, 1500);
            }
        });
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshMessage(EventData eventData) {
        Log.i(TAG, "method:Subscribe#refreshMessage#eventData=" + eventData);
        mTvMessage.setText(eventData.getUserName() + ":\n\n" + eventData.getMessage());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
