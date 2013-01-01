package com.yvelabs.satellitemenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.yvelabs.satellitemenu.utils.ImageUtils;
import com.yvelabs.satellitemenu.utils.MyMathUtils;


/**
 * 
 * @author Yve
 *
 */
public class SatelliteMenu extends FrameLayout {
	
	private int originAngle;
	private int endAngle;
	private int satelliteDistance;

	private ImageView planetMenu;
	private AbstractAnimation menuAnimation;
	private OnSatelliteClickedListener onSatelliteClickedListener;

	private List<SatelliteItemModel> satelliteList = new ArrayList<SatelliteItemModel>();
	private boolean launched = false;
	private AtomicBoolean plusAnimationActive = new AtomicBoolean(false);
	
	public int getOriginAngle() {
		return originAngle;
	}

	public void setOriginAngle(int originAngle) {
		this.originAngle = originAngle;
	}

	public int getEndAngle() {
		return endAngle;
	}

	public void setEndAngle(int endAngle) {
		this.endAngle = endAngle;
	}

	public int getSatelliteDistance() {
		return satelliteDistance;
	}

	public void setSatelliteDistance(int satelliteDistance) {
		this.satelliteDistance = satelliteDistance;
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
	 * 点击卫星按钮监听器
	 * @author Yve
	 *
	 */
	public interface OnSatelliteClickedListener {
		public void onClick(View view);
	}
	/**
	 * 设置卫星按钮监听器
	 * @param onSatelliteClickedListener
	 */
	public void setOnSatelliteClickedListener(OnSatelliteClickedListener onSatelliteClickedListener) {
		this.onSatelliteClickedListener = onSatelliteClickedListener;
	}

	/**
	 * 初始化
	 * @param context
	 */
	private void init(Context context) {
		planetMenu = new ImageView(context);
		try {
			new ImageUtils().setImage(getContext(), planetMenu, "image/planet_menu.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		FrameLayout.LayoutParams planetlayoutPara = new FrameLayout.LayoutParams(0, 0);
//		planetMenu.setLayoutParams(planetlayoutPara);
		
//		addView(planetMenu);
		
		//加载动画类
		loadingAnimation(new DefaultAnimation());
		
		//点击星球监听
		planetMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (plusAnimationActive.compareAndSet(false, true)) {
					try {
						if (launched == false) {
							//TODO sun ying 发射卫星
							menuAnimation.createPlanetLaunchAnimation(planetMenu);
							//卫星发射动画
							for (SatelliteItemModel model : satelliteList) {
								menuAnimation.createSatelliteLaunchAnimation(model.getView());
							}
						} else {
							//TODO sun ying 收回卫星
							menuAnimation.createPlanetDrawBackAnimation(planetMenu);
							//卫星收回动画
							for (SatelliteItemModel model : satelliteList) {
								menuAnimation.createSatelliteDrawBackAnimation(model.getView());
							}
						}
						launched = !launched;
						Toast.makeText(SatelliteMenu.this.getContext(), "点击星球监听 launched:" + launched, Toast.LENGTH_SHORT ).show();
					} finally {
						plusAnimationActive.set(false);
					}
				}
			}

		});
	}
	
	/**
	 * 添加卫星按钮
	 * @param satelliteItemList
	 * @throws Exception
	 */
	public void addSatelliteItem (List<SatelliteItemModel> satelliteList){
		this.satelliteList = satelliteList;
		this.removeView(planetMenu);
		
		//设置起点
		
		//设置终点
		new MyMathUtils().calcFinalXY(satelliteList, originAngle, endAngle, satelliteDistance);
		
		//
		for (final SatelliteItemModel itemModel : satelliteList) {
			ImageView itemView = new ImageView(getContext());
			itemView.setTag(itemModel);
			itemView.setVisibility(View.GONE);
			itemView.setX(itemModel.getFinalX());
			itemView.setY(itemModel.getFinalY());
			itemView.setScaleType(ScaleType.CENTER);
			
			//
			//itemModel.setFinalX(100 - 100);
			//itemModel.setFinalY(100 - 100);
			//itemModel.setOriginX(0 - 100);
			//itemModel.setOriginY(0 - 100);
			
			itemModel.setOriginX(itemModel.getOriginX() - itemModel.getFinalX());
			itemModel.setOriginY(itemModel.getOriginY() - itemModel.getFinalY());
			
			itemModel.setView(itemView);
			
			try {
				new ImageUtils().setImage(getContext(), itemView, itemModel);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//点击卫星按钮监听器
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//TODO sun ying 点击卫星事件
					if (plusAnimationActive.compareAndSet(false, true)) {
						try {
							//启动星球 卫星 动画
							menuAnimation.createPlanetItemClickedAnimation(planetMenu);
							menuAnimation.createSatelliteItemClickedAnimation(view);
							
							//调用监听器
							if (onSatelliteClickedListener != null) 
								onSatelliteClickedListener.onClick(view);
							
							//置为收回状态
							launched = false;
							
							Toast.makeText(SatelliteMenu.this.getContext(), "点击卫星按钮监听器", Toast.LENGTH_SHORT ).show();
						
						} finally {
							plusAnimationActive.set(false);
						}
					}
				}
			});
			
			this.addView(itemView);
		}
		menuAnimation.setSatelliteList(satelliteList);
		
		this.addView(planetMenu);
	}
	
	/**
	 * 装载动画
	 * @param animationModel
	 */
	public void loadingAnimation (AbstractAnimation menuAnimation) {
		menuAnimation.init(satelliteList, this);
		
		this.menuAnimation = menuAnimation;
	}
	
	/**
	 * 设置layout 高宽
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//TODO sun ying 计算控件高, 宽
		super.onMeasure(500, 500);
		
		setMeasuredDimension(500, 500);
		
	}
	
}
