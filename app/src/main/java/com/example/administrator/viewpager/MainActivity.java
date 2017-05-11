package com.example.administrator.viewpager;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int ids [] = {R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e};
    private ViewPager viewpager;
    private TextView tv_msg;
    private ArrayList<ImageView> imageViews;
    private int lastIndex;
    private LinearLayout ll_group_point;
    private static final int PROGRESS = 0;
    private boolean isRunning  = false;
    private final String[] imageDescriptions = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀" };
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PROGRESS://自定滑动页面
                    //滚动页面
                    viewpager.setCurrentItem(viewpager.getCurrentItem()+1);

                    if(!isRunning){
                        handler.sendEmptyMessageDelayed(PROGRESS, 4000);
                    }


                    break;

                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        imageViews = new ArrayList<ImageView>();
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        ll_group_point = (LinearLayout) findViewById(R.id.ll_group_point);
        for(int i=0;i<ids.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);
            imageViews.add(imageView);
            ImageView iv_point = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.leftMargin = 10;
            iv_point.setLayoutParams(params );
            iv_point.setImageResource(R.drawable.point_bg);
            if(i ==0){
                iv_point.setEnabled(true);
            }else{
                iv_point.setEnabled(false);
            }
            ll_group_point.addView(iv_point);
        }
        viewpager.setAdapter(new MyPagerAdapter());
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int myIndex = position%imageViews.size();
                String text = imageDescriptions[myIndex];
                tv_msg.setText(text);
                ll_group_point.getChildAt(lastIndex).setEnabled(false);
                ll_group_point.getChildAt(myIndex).setEnabled(true);
                lastIndex = myIndex;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


      /* int currentpostion = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageViews.size();
        viewpager.setCurrentItem(currentpostion);
        String text = imageDescriptions[currentpostion%imageViews.size()];
        tv_msg.setText(text);*/
        handler.sendEmptyMessageDelayed(PROGRESS, 2000);
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = imageViews.get(position%imageViews.size());
            container.addView(imageView);
            return imageView;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view ==object;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View)object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = true;
    }
}
