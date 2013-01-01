package com.yvelabs.satellitemenu.utils;

import java.util.List;

import com.yvelabs.satellitemenu.SatelliteItemModel;

public class MyMathUtils {
	
	public int getAverageAngle (int angle, int itemCount) {
		return angle / (itemCount - 1);
	}
	
	public static int getFinalX(float angle, int distance){
		return Double.valueOf(Math.rint(distance * Math.cos(Math.toRadians(angle)))).intValue();
    }
    
    public static int getFinalY(float angle, int distance){
        return Double.valueOf(Math.rint(-1 * distance * Math.sin(Math.toRadians(angle)))).intValue();
    }
    
    public void calcFinalXY (List<SatelliteItemModel> satelliteList, int originAngle, int endAngle, int distance) {
    	int aveAngle = getAverageAngle((endAngle - originAngle), satelliteList.size());
    	
    	int index = 0;
    	for (SatelliteItemModel itemModel : satelliteList) {
    		int x = getFinalX(originAngle + (aveAngle * index), distance);
    		int y = getFinalY(originAngle + (aveAngle * index), distance);
    		itemModel.setFinalX(x);
    		itemModel.setFinalY(y);
    		index ++;
    	}
    }

}
