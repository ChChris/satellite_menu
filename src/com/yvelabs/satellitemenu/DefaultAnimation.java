package com.yvelabs.satellitemenu;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

public class DefaultAnimation extends AbstractAnimation {

	/**
	 * ����  ���䶯��
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
	 * ���� �ջض���
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
	 * ���� �������
	 */
	public Animation createPlanetItemClickedAnimation(View view) {

		return createPlanetDrawBackAnimation(view);
	}

	/**
	 * ���� ���䶯��
	 */
	public Animation createSatelliteLaunchAnimation(View satellite) {
		AnimationSet animationSet = new AnimationSet(false);
		SatelliteItemModel itemModel = (SatelliteItemModel) satellite.getTag();
		
		//�ƶ�����
		TranslateAnimation translate = new TranslateAnimation(itemModel.getOriginX() - itemModel.getStopX() - itemModel.getAdjustX(), 0, itemModel.getOriginY() - itemModel.getStopY() - itemModel.getAdjustX(), 0);
		translate.setStartOffset(0);
		translate.setDuration(500);
		translate.setAnimationListener(new SatelliteLaunchListener(satellite));
		translate.setInterpolator(new OvershootInterpolator(3F));
		animationSet.addAnimation(translate);
		
		//͸������
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        long alphaDuration = 100;
        if(getSatelliteDistance() < 60){
        	alphaDuration = getSatelliteDistance() / 4;
        }
        alphaAnimation.setDuration(alphaDuration);
        alphaAnimation.setStartOffset(0);
        animationSet.addAnimation(alphaAnimation);
        
        //��ת����
        RotateAnimation rotate = new RotateAnimation(0f, 360f, 
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        
        rotate.setInterpolator(new DecelerateInterpolator(1F));
        long duration = 100;
        if(getSatelliteDistance() <= 150){
            duration = getSatelliteDistance() / 3;
        }
        rotate.setDuration(300);
        rotate.setStartOffset(duration);   
        animationSet.addAnimation(rotate);
        
		
		satellite.startAnimation(animationSet);
		return animationSet;
	}

	/**
	 * ���� �ջض���
	 */
	public Animation createSatelliteDrawBackAnimation(View view) {
		AnimationSet animationSet = new AnimationSet(false);
		SatelliteItemModel itemModel = (SatelliteItemModel) view.getTag();

		//�ƶ�����
		TranslateAnimation translate = new TranslateAnimation(0, itemModel.getOriginX() - itemModel.getStopX() - itemModel.getAdjustX(), 0, itemModel.getOriginY() - itemModel.getStopY() - itemModel.getAdjustX());
		translate.setStartOffset(0);
		translate.setDuration(500);
		translate.setAnimationListener(new SatelliteDrawBackListener(view));
		animationSet.addAnimation(translate);
		
		
		//͸������
		AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        long alphaDuration = 100;
        if(getSatelliteDistance() < 60){
        	alphaDuration = getSatelliteDistance() / 4;
        }
        alphaAnimation.setDuration(300);
        alphaAnimation.setStartOffset(200);
        animationSet.addAnimation(alphaAnimation);
		
		view.startAnimation(animationSet);
		return animationSet;
	}

	/**
	 * ���� �������
	 */
	public Animation createSatelliteItemClickedAnimation(View view) {
		AnimationSet animationSet = new AnimationSet(false);
		//�ջض���
		
		//�ջ���������
		drawBackAllSatellites(100);
		
		return animationSet;
	}
	
	/**
	 * �ջ���������
	 * @param delayTime
	 */
	private void drawBackAllSatellites(int delayTime) {
		AnimationSet animationSet = new AnimationSet(false);
		AlphaAnimation alphaAnimation;
		for (SatelliteItemModel itemModel : getSatelliteList()) {
			TranslateAnimation translate = new TranslateAnimation(0, itemModel.getOriginX() - itemModel.getStopX() - itemModel.getAdjustX(), 0, itemModel.getOriginY() - itemModel.getStopY() - itemModel.getAdjustX());
			translate.setStartOffset(0);
			translate.setDuration(500);
			translate.setAnimationListener(new SatelliteDrawBackListener(itemModel.getView()));
			animationSet.addAnimation(translate);
			
			//͸������
			/*alphaAnimation = new AlphaAnimation(1f, 0f);
	        long alphaDuration = 10;
	        if(expandDuration < 10){
	        	alphaDuration = expandDuration / 10;
	        }
	        alphaAnimation.setDuration(alphaDuration);
	        alphaAnimation.setStartOffset((delay + duration) - alphaDuration);
	        
	        animationSet.addAnimation(alphaAnimation);*/
			
			itemModel.getView().startAnimation(animationSet);
		}
	}
	
	/**
	 * ���� ���� ��������
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
//			satellite.layout(itemModel.getStopX(), itemModel.getStopY(), itemModel.getStopX() + satellite.getWidth(), itemModel.getStopY() + satellite.getHeight());
		}
		
	}
	
	/**
	 * ���� �ջ� ��������
	 * @author Yve
	 *
	 */
	private class SatelliteDrawBackListener implements Animation.AnimationListener {
		private View view;
		private SatelliteItemModel itemModel;
		
		private SatelliteDrawBackListener (View view) {
			this.view = view;
//			itemModel = (SatelliteItemModel) view.getTag();
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
