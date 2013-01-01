package com.yvelabs.satellitemenu.utils;


import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.yvelabs.satellitemenu.SatelliteItemModel;


public class ImageUtils {
	
	public void setImage (Context context, ImageView imageView, SatelliteItemModel itemModel) throws Exception {
		if (itemModel.getImgResourceId() > 0) {
			setImage(imageView, itemModel.getImgResourceId());
		} else if (itemModel.getImgDrawable() != null) {
			setImage(imageView, itemModel.getImgDrawable());
		} else if (itemModel.getImgAssetPath() != null && itemModel.getImgAssetPath().length() > 0) {
			setImage(context, imageView, itemModel.getImgAssetPath());
		} else {
			throw new Exception("Satellite(" + itemModel.getId() + ") need a picture.");
		}
	}
	
	public void setImage (ImageView imageView, int resourceId) {
		imageView.setImageResource(resourceId);
	}
	public void setImage (ImageView imageView, Bitmap image) {
		imageView.setImageBitmap(image);
	}
	public void setImage (ImageView imageView, Drawable drawable) {
		imageView.setImageDrawable(drawable);
	}
	public void setImage (Context context, ImageView imageView, String assetPath) throws IOException {
		setImage(imageView, getImageFromAssetFile(context, assetPath));
	}

	public Bitmap getImageFromAssetFile(Context context, String fileName) throws IOException {
		Bitmap image = null;
		AssetManager assetManager = context.getAssets();
		InputStream is = assetManager.open(fileName);
		image = BitmapFactory.decodeStream(is);
		is.close();
		return image;
	}

}
