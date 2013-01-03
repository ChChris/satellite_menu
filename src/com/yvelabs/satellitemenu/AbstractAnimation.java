package com.yvelabs.satellitemenu;


import java.util.List;

import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public abstract class AbstractAnimation {
	
	private List<SatelliteItemModel> satelliteList;
	
	public void setSatelliteList(List<SatelliteItemModel> satelliteList) {
		this.satelliteList = satelliteList;
	}

	private RelativeLayout parentLayout;
	
	public RelativeLayout getParentLayout() {
		return parentLayout;
	}

	public void setParentLayout(RelativeLayout parentLayout) {
		this.parentLayout = parentLayout;
	}

	public List<SatelliteItemModel> getSatelliteList() {
		return satelliteList;
	}

	public void init(List<SatelliteItemModel> satelliteList, RelativeLayout parentLayout) {
		this.satelliteList = satelliteList;
		this.parentLayout = parentLayout;
	}

	
	public abstract Animation createPlanetLaunchAnimation(View view);
	
	public abstract Animation createPlanetDrawBackAnimation(View view);
	
	public abstract Animation createPlanetItemClickedAnimation(View view);
	
	public abstract Animation createSatelliteLaunchAnimation(View view);
	
	public abstract Animation createSatelliteDrawBackAnimation(View view);
	
	public abstract Animation createSatelliteItemClickedAnimation(View view);
	
	/**
	 * ÐÇÇò  ·¢Éä ¶¯»­¼àÌýÆ÷
	 * @author Yve
	 *
	 */
	private class planetLaunchListener implements Animation.AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			
		}
		
	}
	
	/**
	 * ÐÇÇò ÊÕ»Ø ¶¯»­¼àÌý
	 * @author Yve
	 *
	 */
	private class planetDrawBackListener implements Animation.AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			
		}
		
	}
	
	/**
	 * ÐÇÇò µã»÷ ¶¯»­¼àÌý
	 * @author Yve
	 *
	 */
	private class planetItemClickedListener implements Animation.AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			
		}
		
	}
	
	
	
	/**
	 * ÎÀÐÇ µã»÷ ¶¯»­¼àÌýÆ÷
	 * @author Yve
	 *
	 */
	private class satelliteItemClickedListener implements Animation.AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			
		}
		
	}

}
