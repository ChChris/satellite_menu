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
	 * ������ǰ�ť������
	 * @author Yve
	 *
	 */
	public interface OnSatelliteClickedListener {
		public void onClick(View view);
	}
	/**
	 * �������ǰ�ť������
	 * @param onSatelliteClickedListener
	 */
	public void setOnSatelliteClickedListener(OnSatelliteClickedListener onSatelliteClickedListener) {
		this.onSatelliteClickedListener = onSatelliteClickedListener;
	}

	/**
	 * ��ʼ��
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
		
		//���ض�����
		loadingAnimation(new DefaultAnimation());
		
		//����������
		planetMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (plusAnimationActive.compareAndSet(false, true)) {
					try {
						if (launched == false) {
							//TODO sun ying ��������
							menuAnimation.createPlanetLaunchAnimation(planetMenu);
							//���Ƿ��䶯��
							for (SatelliteItemModel model : satelliteList) {
								menuAnimation.createSatelliteLaunchAnimation(model.getView());
							}
						} else {
							//TODO sun ying �ջ�����
							menuAnimation.createPlanetDrawBackAnimation(planetMenu);
							//�����ջض���
							for (SatelliteItemModel model : satelliteList) {
								menuAnimation.createSatelliteDrawBackAnimation(model.getView());
							}
						}
						launched = !launched;
						Toast.makeText(SatelliteMenu.this.getContext(), "���������� launched:" + launched, Toast.LENGTH_SHORT ).show();
					} finally {
						plusAnimationActive.set(false);
					}
				}
			}

		});
	}
	
	/**
	 * ������ǰ�ť
	 * @param satelliteItemList
	 * @throws Exception
	 */
	public void addSatelliteItem (List<SatelliteItemModel> satelliteList){
		this.satelliteList = satelliteList;
		this.removeView(planetMenu);
		
		//�������
		
		//�����յ�
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

			//������ǰ�ť������
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//TODO sun ying ��������¼�
					if (plusAnimationActive.compareAndSet(false, true)) {
						try {
							//�������� ���� ����
							menuAnimation.createPlanetItemClickedAnimation(planetMenu);
							menuAnimation.createSatelliteItemClickedAnimation(view);
							
							//���ü�����
							if (onSatelliteClickedListener != null) 
								onSatelliteClickedListener.onClick(view);
							
							//��Ϊ�ջ�״̬
							launched = false;
							
							Toast.makeText(SatelliteMenu.this.getContext(), "������ǰ�ť������", Toast.LENGTH_SHORT ).show();
						
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
	 * װ�ض���
	 * @param animationModel
	 */
	public void loadingAnimation (AbstractAnimation menuAnimation) {
		menuAnimation.init(satelliteList, this);
		
		this.menuAnimation = menuAnimation;
	}
	
	/**
	 * ����layout �߿�
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//TODO sun ying ����ؼ���, ��
		super.onMeasure(500, 500);
		
		setMeasuredDimension(500, 500);
		
	}
	
}
