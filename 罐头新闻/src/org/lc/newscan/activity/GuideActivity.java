package org.lc.newscan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.lc.newscan.R;
import org.lc.newscan.utils.Constant;
import org.lc.newscan.utils.PreUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "GuideActivity";
    private ViewPager mVpPager;
    private LinearLayout mLlCon;
    private List<ImageView> mDatas;
    private ImageView mIvRed;
    private int width;
    private Button mBtnStart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //初始化代码
        initView();
        //按钮添加点击事件
        mBtnStart.setOnClickListener(this);
        //获取数据
        mDatas = getDatas();
        //给mVpPager添加适配器
        mVpPager.setAdapter(new MyPagerAdapter());
        //添加pager的监听,进行小红点的移动
        mVpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //位置,百分比,速度
                //获取树状观察者
                //获取圆点之间的距离
                getCircleDistance();
                int distance = (int) (positionOffset * width + position * width + 0.5f);
                if (width > 0) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvRed.getLayoutParams();
                    params.leftMargin = distance;
                    mIvRed.setLayoutParams(params);
                }
            }

            public void onPageSelected(int position) {
                if (position < 2) {
                    mBtnStart.setVisibility(View.GONE);
                } else {
                    mBtnStart.setVisibility(View.VISIBLE);
                }
            }

            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getCircleDistance() {
        final ViewTreeObserver observer = mLlCon.getViewTreeObserver();
        if (width == 0) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    //加载完毕时
                    width = mLlCon.getChildAt(1).getLeft() - mLlCon.getChildAt(0).getLeft();
                    Log.d(TAG, "" + width);
                    observer.removeGlobalOnLayoutListener(this);
                }
            });
        }
    }

    public void onClick(View v) {
        if (mBtnStart == v) {
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            //然后进行记录不是第一次打开
            PreUtils.put(this, Constant.FIRST_OPEN, false);
            finish();
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        public int getCount() {
            if (mDatas != null) {
                return mDatas.size();
            }
            return 0;
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mDatas.get(position);
            container.addView(view);
            return mDatas.get(position);
        }
    }

    private void initView() {
        mVpPager = (ViewPager) findViewById(R.id.guide_vp_viewpager);
        mLlCon = (LinearLayout) findViewById(R.id.guide_ll_container);
        mIvRed = (ImageView) findViewById(R.id.guide_iv_index);
        mBtnStart = (Button) findViewById(R.id.guide_btn_start);
        //让Button隐藏
        mBtnStart.setVisibility(View.GONE);
    }


    public List<ImageView> getDatas() {
        List<ImageView> list = new ArrayList<ImageView>();
        int photo[] = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        for (int x = 0; x < 3; x++) {
            ImageView view = new ImageView(GuideActivity.this);
            view.setImageResource(photo[x]);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(view);
            //添加小白点
            ImageView iconView = new ImageView(GuideActivity.this);
            iconView.setImageResource(R.drawable.shape_circle_normal);
            if (x > 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10;
                iconView.setLayoutParams(params);
            }
            mLlCon.addView(iconView);
        }
        return list;
    }
}
