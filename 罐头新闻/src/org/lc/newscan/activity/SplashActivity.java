package org.lc.newscan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import org.lc.newscan.R;
import org.lc.newscan.utils.Constant;
import org.lc.newscan.utils.PreUtils;

public class SplashActivity extends Activity {

	private RelativeLayout mRlCt;
	private static final String TAG = "SplashActivity";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		initView();

		// 进行动画
		startAnimation();
	}

	private void startAnimation() {
		// 开始透明度,旋转,缩放
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
		alphaAnimation.setDuration(2000);
		alphaAnimation.setFillAfter(true);

		// 旋转
		RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setDuration(1000);
		rotateAnimation.setFillAfter(true);
		// 缩放
		ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAnimation.setDuration(1000);
		scaleAnimation.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.addAnimation(alphaAnimation);
		set.addAnimation(rotateAnimation);
		set.addAnimation(scaleAnimation);

		mRlCt.startAnimation(set);
		// 添加一个监听事件
		set.setAnimationListener(new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				// 当动画结束的时候,获取是否第一次打开软件
				boolean isOpen = (Boolean) PreUtils.get(SplashActivity.this,
						Constant.FIRST_OPEN, true);
				if (isOpen) {
					// 是的话进入引导页
					Log.d(TAG, "引导页");
					Intent intent = new Intent(SplashActivity.this,
							GuideActivity.class);
					startActivity(intent);
					finish();
				} else {
					// 进入主页
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}

			}

			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	private void initView() {
		mRlCt = (RelativeLayout) findViewById(R.id.splash_rl_container);
	}
}
