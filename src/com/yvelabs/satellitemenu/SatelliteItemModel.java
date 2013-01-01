package com.yvelabs.satellitemenu;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class SatelliteItemModel {
	
	private int id;
    private int imgResourceId;
    private Drawable imgDrawable;
    private String imgAssetPath;
	private ImageView view;
	private int originX;
	private int originY;
    private int finalX;
    private int finalY;
    private int relativeX;
    private int relativeY;
    
    public int getRelativeX() {
		return relativeX;
	}

	public void setRelativeX(int relativeX) {
		this.relativeX = relativeX;
	}

	public int getRelativeY() {
		return relativeY;
	}

	public void setRelativeY(int relativeY) {
		this.relativeY = relativeY;
	}

	public int getOriginX() {
		return originX;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	public SatelliteItemModel (int id, int imgResourceId) {
    	this.id = id;
    	this.imgResourceId = imgResourceId;
    }
    
	public SatelliteItemModel (int id, Drawable imgDrawable) {
		this.id = id;
		this.imgDrawable = imgDrawable;
	}
	
	public SatelliteItemModel (int id, String imgAssetPath) {
		this.id = id;
		this.imgAssetPath = imgAssetPath;
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getImgResourceId() {
		return imgResourceId;
	}
	public void setImgResourceId(int imgResourceId) {
		this.imgResourceId = imgResourceId;
	}
	public Drawable getImgDrawable() {
		return imgDrawable;
	}
	public void setImgDrawable(Drawable imgDrawable) {
		this.imgDrawable = imgDrawable;
	}
	public ImageView getView() {
		return view;
	}
	public void setView(ImageView view) {
		this.view = view;
	}
	public int getFinalX() {
		return finalX;
	}
	public void setFinalX(int finalX) {
		this.finalX = finalX;
	}
	public int getFinalY() {
		return finalY;
	}
	public void setFinalY(int finalY) {
		this.finalY = finalY;
	}
	
	public String getImgAssetPath() {
		return imgAssetPath;
	}
	public void setImgAssetPath(String imgAssetPath) {
		this.imgAssetPath = imgAssetPath;
	}

}
