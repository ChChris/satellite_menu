package com.yvelabs.satellitemenu;


import java.util.List;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public abstract class AbstractAnimation {
	
	private List<SatelliteItemModel> satelliteList; //Œ¿–«
	private ImageView planetMenu; //–««Ú 
	private RelativeLayout parentLayout; //∏∏øÿº˛
	private int satelliteDistance; //Œ¿–«æ‡¿Î
	
	public int getSatelliteDistance() {
		return satelliteDistance;
	}

	public void setSatelliteDistance(int satelliteDistance) {
		this.satelliteDistance = satelliteDistance;
	}

	public void setSatelliteList(List<SatelliteItemModel> satelliteList) {
		this.satelliteList = satelliteList;
	}
	
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

	public ImageView getPlanetMenu() {
		return planetMenu;
	}

	public void setPlanetMenu(ImageView planetMenu) {
		this.planetMenu = planetMenu;
	}
	
	public abstract Animation createPlanetLaunchAnimation(View view);
	
	public abstract Animation createPlanetDrawBackAnimation(View view);
	
	public abstract Animation createPlanetItemClickedAnimation(View view);
	
	public abstract Animation createSatelliteLaunchAnimation(View view);
	
	public abstract Animation createSatelliteDrawBackAnimation(View view);
	
	public abstract Animation createSatelliteItemClickedAnimation(View view);
	

}
