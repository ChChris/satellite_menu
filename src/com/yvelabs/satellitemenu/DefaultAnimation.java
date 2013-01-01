package com.yvelabs.satellitemenu;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

public class DefaultAnimation extends AbstractAnimation {

	/**
	 * 星球  发射动画
	 */
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

	/**
	 * 星球 收回动画
	 */
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

	/**
	 * 星球 点击动画
	 */
	public Animation createPlanetItemClickedAnimation(View view) {

		return createPlanetDrawBackAnimation(view);
	}

	/**
	 * 卫星 发射动画
	 */
	public Animation createSatelliteLaunchAnimation(View satellite) {
		AnimationSet animationSet = new AnimationSet(false);
		SatelliteItemModel itemModel = (SatelliteItemModel) satellite.getTag();
		
		TranslateAnimation translate = new TranslateAnimation(itemModel.getOriginX(), /*itemModel.getFinalX()*/0, itemModel.getOriginY(), /*itemModel.getFinalY()*/0);
		translate.setStartOffset(0);
		translate.setDuration(500);
		translate.setAnimationListener(new SatelliteLaunchListener(satellite));
		translate.setInterpolator(new OvershootInterpolator(3F));
		
		animationSet.addAnimation(translate);
		satellite.startAnimation(animationSet);
		
		return animationSet;
	}

	/**
	 * 卫星 收回动画
	 */
	public Animation createSatelliteDrawBackAnimation(View view) {
		AnimationSet animationSet = new AnimationSet(false);
		SatelliteItemModel itemModel = (SatelliteItemModel) view.getTag();

		//移动动画
		TranslateAnimation translate = new TranslateAnimation(/*itemModel.getFinalX()*/0, itemModel.getOriginX(), /*itemModel.getFinalY()*/0, itemModel.getOriginY());
		translate.setStartOffset(0);
		translate.setDuration(500);
		translate.setAnimationListener(new SatelliteDrawBackListener(view));
		animationSet.addAnimation(translate);
		
		view.startAnimation(animationSet);
		return animationSet;
	}

	/**
	 * 卫星 点击动画
	 */
	public Animation createSatelliteItemClickedAnimation(View view) {
		AnimationSet animationSet = new AnimationSet(false);
		//收回动画
		
		//收回所有卫星
		drawBackAllSatellites(100);
		
		return animationSet;
	}
	
	/**
	 * 收回所有卫星
	 * @param delayTime
	 */
	private void drawBackAllSatellites(int delayTime) {
		TranslateAnimation translate;
		for (SatelliteItemModel itemModel : getSatelliteList()) {
			translate = new TranslateAnimation(/*itemModel.getFinalX()*/0, itemModel.getOriginX(), /*itemModel.getFinalY()*/0, itemModel.getOriginY());
			translate.setStartOffset(delayTime);
			translate.setDuration(500);
			translate.setAnimationListener(new SatelliteDrawBackListener(itemModel.getView()));
			itemModel.getView().startAnimation(translate);
		}
	}
	
	/**
	 * 卫星 发射 动画监听
	 * @author Yve
	 *
	 */
	private class SatelliteLaunchListener implements Animation.AnimationListener {
		private View satellite ;
		private SatelliteItemModel itemModel;
		
		public SatelliteLaunchListener (View satellite) {
			this.satellite = satellite;
			itemModel = (SatelliteItemModel) satellite.getTag();
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

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
		private SatelliteItemModel itemModel;
		
		private SatelliteDrawBackListener (View view) {
			this.view = view;
			itemModel = (SatelliteItemModel) view.getTag();
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			
			view.setVisibility(View.GONE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			
		}
		
	}

}
