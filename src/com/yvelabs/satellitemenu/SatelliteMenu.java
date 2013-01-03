package com.yvelabs.satellitemenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yvelabs.satellitemenu.utils.ImageUtils;
import com.yvelabs.satellitemenu.utils.MyMathUtils;


/**
 * 
 * @author Yve
 *
 */
public class SatelliteMenu extends RelativeLayout {
	
	private int originAngle; //∆ ºΩ«∂»
	private int endAngle; //÷’÷πΩ«∂»
	private int satelliteDistance; //Œ¿–«æ‡¿Î
	
	private ImageView planetMenu;
	private AbstractAnimation menuAnimation;
	private OnSatelliteClickedListener onSatelliteClickedListener;
	private List<SatelliteItemModel> satelliteList = new ArrayList<SatelliteItemModel>();
	private boolean launched = false;
	private AtomicBoolean plusAnimationActive = new AtomicBoolean(false);
	

	public void setPlanetImg(int planetImgResourceId) {
		new ImageUtils().setImage(planetMenu, planetImgResourceId);
	}

	public void setPlanetImg(Drawable planetImgDrawable) {
		new ImageUtils().setImage(planetMenu, planetImgDrawable);
	}

	public void setPlanetImg(String planetImgAssetPath) {
		try {
			new ImageUtils().setImage(planetMenu.getContext(), planetMenu, planetImgAssetPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public SatelliteMenu(Context context) {
		super(context);
		init(context);
	}

	public SatelliteMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public SatelliteMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	/**
	 * µ„ª˜Œ¿–«∞¥≈•º‡Ã˝∆˜
	 * @author Yve
	 *
	 */
	public interface OnSatelliteClickedListener {
		public void onClick(View view);
	}
	/**
	 * …Ë÷√Œ¿–«∞¥≈•º‡Ã˝∆˜
	 * @param onSatelliteClickedListener
	 */
	public void setOnSatelliteClickedListener(OnSatelliteClickedListener onSatelliteClickedListener) {
		this.onSatelliteClickedListener = onSatelliteClickedListener;
	}

	/**
	 * ≥ı ºªØ
	 * @param context
	 */
	private void init(Context context) {
		planetMenu = new ImageView(context);
		setBackgroundColor(Color.rgb(255, 229, 145));
		planetMenu.setBackgroundColor(Color.rgb(84, 78, 110));
		RelativeLayout.LayoutParams planetlayoutPara = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		planetMenu.setScaleType(ScaleType.CENTER);
		addView(planetMenu, planetlayoutPara);
		
		//µ„ª˜–««Úº‡Ã˝
		planetMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (plusAnimationActive.compareAndSet(false, true)) {
					try {
						if (launched == false) {
							//TODO sun ying ∑¢…‰Œ¿–«
							menuAnimation.createPlanetLaunchAnimation(planetMenu);
							//Œ¿–«∑¢…‰∂Øª≠
							for (SatelliteItemModel model : satelliteList) {
								menuAnimation.createSatelliteLaunchAnimation(model.getView());
							}
						} else {
							//TODO sun ying  ’ªÿŒ¿–«
							menuAnimation.createPlanetDrawBackAnimation(planetMenu);
							//Œ¿–« ’ªÿ∂Øª≠
							for (SatelliteItemModel model : satelliteList) {
								menuAnimation.createSatelliteDrawBackAnimation(model.getView());
							}
						}
						launched = !launched;
						Toast.makeText(SatelliteMenu.this.getContext(), "µ„ª˜–««Úº‡Ã˝ launched:" + launched, Toast.LENGTH_SHORT ).show();
					} finally {
						plusAnimationActive.set(false);
					}
				}
			}

		});
	}
	
	/**
	 * ÃÌº”Œ¿–«∞¥≈•
	 * @param satelliteItemList
	 * @throws Exception
	 */
	private void addSatelliteItem (List<SatelliteItemModel> sateList){
		this.removeView(planetMenu);
		
		//…Ë÷√÷’µ„
		new MyMathUtils().calcStopXY(satelliteList, originAngle, endAngle, satelliteDistance); 
		
		for (final SatelliteItemModel itemModel : satelliteList) {
			//view Ù–‘…Ë÷√
			ImageView itemView = new ImageView(getContext());
			itemView.setTag(itemModel);
			itemView.setVisibility(View.GONE);
			itemView.setX(itemModel.getStopX()); //TODO sun ying setX
			itemView.setY(itemModel.getStopY()); //TODO sun ying setY
			itemView.setScaleType(ScaleType.CENTER);
			RelativeLayout.LayoutParams satellitelayoutPara = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			//…Ë÷√Model Ù–‘
			itemModel.setOriginX(Double.valueOf(Math.rint(planetMenu.getX())).intValue());
			itemModel.setOriginY(Double.valueOf(Math.rint(planetMenu.getY())).intValue());
			itemModel.setView(itemView);
			
			try {
				new ImageUtils().setImage(getContext(), itemView, itemModel);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//µ„ª˜Œ¿–«∞¥≈•º‡Ã˝∆˜
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//TODO sun ying µ„ª˜Œ¿–« ¬º˛
					if (plusAnimationActive.compareAndSet(false, true)) {
						try {
							//∆Ù∂Ø–««Ú Œ¿–« ∂Øª≠
							menuAnimation.createPlanetItemClickedAnimation(planetMenu);
							menuAnimation.createSatelliteItemClickedAnimation(view);
							
							//µ˜”√º‡Ã˝∆˜
							if (onSatelliteClickedListener != null) 
								onSatelliteClickedListener.onClick(view);
							
							//÷√Œ™ ’ªÿ◊¥Ã¨
							launched = false;
							
							Toast.makeText(SatelliteMenu.this.getContext(), "µ„ª˜Œ¿–«∞¥≈•º‡Ã˝∆˜", Toast.LENGTH_SHORT ).show();
						
						} finally {
							plusAnimationActive.set(false);
						}
					}
				}
			});
			
			this.addView(itemView, satellitelayoutPara);
		}
		menuAnimation.setSatelliteList(satelliteList);
		
		this.addView(planetMenu);
	}
	
	/**
	 * …Ë÷√layout ∏ﬂøÌ
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		//setMeasuredDimension(layoutWidth, layoutHeight);
	}
	
	/**
	 * …Ë÷√
	 * @param settingPara
	 * @throws Exception
	 */
	public void setting (SettingPara settingPara) throws Exception {
		
		this.endAngle = MyMathUtils.getAngle(settingPara.getEndAngle());
		this.originAngle = MyMathUtils.getAngle(settingPara.getOriginAngle());
		this.satelliteDistance = settingPara.getSatelliteDistance();
		
		
		//◊∞‘ÿ∂Øª≠
		menuAnimation = settingPara.getMenuAnimation();
		menuAnimation.init(satelliteList, this);
		
		//ÃÌº”Œ¿–«
		this.satelliteList.addAll(settingPara.getSatelliteList());
		
		//…Ë÷√–««ÚÕº±Í
		if (settingPara.getPlanetImgResourceId() > 0) {
			new ImageUtils().setImage(planetMenu, settingPara.getPlanetImgResourceId());
		} else if (settingPara.getPlanetImgDrawable() != null) {
			new ImageUtils().setImage(planetMenu, settingPara.getPlanetImgDrawable());
		} else if (settingPara.getPlanetImgAssetPath() != null && settingPara.getPlanetImgAssetPath().length() > 0) {
			new ImageUtils().setImage(getContext(), planetMenu, settingPara.getPlanetImgAssetPath());
		}
		
		
		Map<String, String> map = null;
		int layoutWidth;
		int layoutHeight;
		try {
			//int planetLenght = new ImageUtils().getLongestSide(getContext(), settingPara.getPlanetImgResourceId());
			int satelliteLenght = new ImageUtils().getLongestImage(getContext(), satelliteList) * 2;
			int radius = satelliteDistance + satelliteLenght;
			
			if (settingPara.getPlanetPosition() != null && settingPara.getPlanetPosition().length() > 0) {
				map = new MyMathUtils().getWidthHeightByPosition(settingPara.getPlanetPosition(), radius);
				layoutWidth = Integer.parseInt(map.get("WIDTH"));
				layoutHeight = Integer.parseInt(map.get("HEIGHT"));
			} else {
				map = new MyMathUtils().getWidthHeightNPosition(originAngle, endAngle, radius);
				layoutWidth = Integer.parseInt(map.get("WIDTH"));
				layoutHeight = Integer.parseInt(map.get("HEIGHT"));
				settingPara.setPlanetPosition(map.get("POSITION"));
			}
			
			//…Ë÷√layout ∏ﬂ, øÌ
			RelativeLayout.LayoutParams parentlayoutPara = (LayoutParams) getLayoutParams();
			parentlayoutPara.width = layoutWidth ;
			parentlayoutPara.height = layoutHeight;
			setLayoutParams(parentlayoutPara);
			
			//…Ë÷√–««ÚŒª÷√
			setViewPosition(settingPara.getPlanetPosition(), planetMenu);
			
			//…Ë÷√Œ¿–«
			addSatelliteItem(satelliteList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setViewPosition (String position, View view) throws Exception {
		RelativeLayout.LayoutParams layoutPara = (LayoutParams) view.getLayoutParams();
		if (layoutPara == null) {
			layoutPara = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		}
		
		if (SettingPara.POSITION_TOP_LEFT.equals(position)) {
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		} else if (SettingPara.POSITION_TOP_RIGHT.equals(position)) {
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		} else if (SettingPara.POSITION_BOTTOM_LEFT.equals(position)) {
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		} else if (SettingPara.POSITION_BOTTOM_RIGHT.equals(position)) {
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		} else if (SettingPara.POSITION_TOP_CENTER.equals(position)) {
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			layoutPara.addRule(RelativeLayout.CENTER_HORIZONTAL);
		} else if (SettingPara.POSITION_BOTTOM_CENTER.equals(position)) {
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			layoutPara.addRule(RelativeLayout.CENTER_HORIZONTAL);
		} else if (SettingPara.POSITION_LEFT_CENTER.equals(position)) {
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			layoutPara.addRule(RelativeLayout.CENTER_VERTICAL);
		} else if (SettingPara.POSITION_RIGHT_CENTER.equals(position)) {
			layoutPara.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutPara.addRule(RelativeLayout.CENTER_VERTICAL);
		} else if (SettingPara.POSITION_CENTER.equals(position)) {
			layoutPara.addRule(RelativeLayout.CENTER_IN_PARENT);
		} else {
			throw new Exception ("(setViewPosition)there are no this planet position (" + position + ")") ;
		}
		
		view.setLayoutParams(layoutPara);
	}
	
}
