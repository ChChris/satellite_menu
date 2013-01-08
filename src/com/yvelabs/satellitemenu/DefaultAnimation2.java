package com.yvelabs.satellitemenu;

import com.yvelabs.satellitemenu.utils.MyMathUtils;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class DefaultAnimation2 extends AbstractAnimation {

	@Override
	public Animation createPlanetLaunchAnimation(View view) {
		RotateAnimation rotateAnimation = new RotateAnimation(0f, -135f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setDuration(300);
		rotateAnimation.setFillEnabled(true);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setFillBefore(false);

		view.startAnimation(rotateAnimation);
		return rotateAnimation;
	}

	@Override
	public Animation createPlanetDrawBackAnimation(View view) {
		RotateAnimation rotateAnimation = new RotateAnimation(-135f, 0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotateAnimation.setDuration(300);
		rotateAnimation.setFillEnabled(true);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setFillBefore(false);

		view.startAnimation(rotateAnimation);
		return rotateAnimation;
	}

	@Override
	public Animation createPlanetItemClickedAnimation(View view) {
		return createPlanetDrawBackAnimation(view);
	}

	/**
	 * 卫星  发射动画
	 */
	@Override
	public Animation createSatelliteLaunchAnimation(View view) {
		AnimationSet animationSet = new AnimationSet(false);
		SatelliteItemModel itemModel = (SatelliteItemModel) view.getTag();
		
		// 移动动画
		TranslateAnimation translateAnimation = new TranslateAnimation(
				itemModel.getOriginX() - itemModel.getStopX()
						- itemModel.getAdjustX(), 0, itemModel.getOriginY()
						- itemModel.getStopY() - itemModel.getAdjustX(), 0);
		translateAnimation.setStartOffset(MyMathUtils.getRandom(0, 70));
		translateAnimation.setDuration(400);
		translateAnimation.setAnimationListener(new SatelliteLaunchListener(view));
		translateAnimation.setInterpolator(new OvershootInterpolator(3F));
		animationSet.addAnimation(translateAnimation);
		
		view.startAnimation(animationSet);
		return animationSet;
	}

	@Override
	public Animation createSatelliteDrawBackAnimation(View view) {
		AnimationSet animationSet = new AnimationSet(false);
		SatelliteItemModel itemModel = (SatelliteItemModel) view.getTag();
		
		//移动动画
		TranslateAnimation translateAnimation = new TranslateAnimation(0, itemModel.getOriginX() - itemModel.getStopX() - itemModel.getAdjustX(), 0, itemModel.getOriginY() - itemModel.getStopY() - itemModel.getAdjustX());
		translateAnimation.setStartOffset(200 + MyMathUtils.getRandom(0, 70));
		translateAnimation.setDuration(400);
		translateAnimation.setAnimationListener(new SatelliteDrawBackListener(view));
		translateAnimation.setInterpolator(new AnticipateInterpolator(3F));
		animationSet.addAnimation(translateAnimation);
		
		view.startAnimation(animationSet);
		return animationSet;
	}

	@Override
	public Animation createSatelliteItemClickedAnimation(View view) {
		AnimationSet clickedAnimationSet = new AnimationSet(false);
		//收回动画
		//放大动画
		ScaleAnimation scaleAnimation = new ScaleAnimation(1, 3, 1, 3, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(600);
		scaleAnimation.setStartOffset(100);
		clickedAnimationSet.addAnimation(scaleAnimation);
		//透明动画
		AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
		alphaAnimation.setDuration(300);
		alphaAnimation.setStartOffset(250);
		clickedAnimationSet.addAnimation(alphaAnimation);
		
		clickedAnimationSet.setAnimationListener(new SatelliteItemClickedListener(view));
		
		//收回所有卫星
		for (SatelliteItemModel itemModel : getSatelliteList()) {
			if (itemModel.getId() != ((SatelliteItemModel)view.getTag()).getId()) 
			createSatelliteDrawBackAnimation(itemModel.getView());
		}
		
		view.startAnimation(clickedAnimationSet);
		
		return clickedAnimationSet;
	}
	
	/**
	 * 卫星 发射 动画监听
	 * @author Yve
	 *
	 */
	private class SatelliteLaunchListener implements Animation.AnimationListener {
		private View satellite;
		
		public SatelliteLaunchListener (View satellite) {
			this.satellite = satellite;
//			itemModel = (SatelliteItemModel) satellite.getTag();
		}
		@Override
		public void onAnimationEnd(Animation animation) { }
		@Override
		public void onAnimationRepeat(Animation animation) { }
		@Override
		public void onAnimationStart(Animation animation) {
			satellite.setVisibility(View.VISIBLE);
		}
		
	}
	
	/**
	 * 卫星 收回 动画监听
	 * @author Yve
	 *
	 */
	private class SatelliteDrawBackListener implements Animation.AnimationListener {
		private View view;
		
		private SatelliteDrawBackListener (View view) {
			this.view = view;
//			itemModel = (SatelliteItemModel) view.getTag();
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			view.setVisibility(View.GONE);
		}
		@Override
		public void onAnimationRepeat(Animation animation) { }
		@Override
		public void onAnimationStart(Animation animation) { }
		
	}
	
	/**
	 * 卫星 点击 动画监听器
	 * @author Yve
	 *
	 */
	private class SatelliteItemClickedListener implements Animation.AnimationListener {
		private View view;
		
		public SatelliteItemClickedListener (View view) {
			this.view = view;
		}
		@Override
		public void onAnimationEnd(Animation animation) {
			view.setVisibility(View.GONE);
		}
		@Override
		public void onAnimationRepeat(Animation animation) { }
		@Override
		public void onAnimationStart(Animation animation) { }
	}

}
