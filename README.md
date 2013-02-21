# Satelleit Menu #

![](https://github.com/yvelabs/satellite_menu/blob/master/satellite_0.jpg?raw=true)


**Idea from** [siyamed](https://github.com/siyamed/android-satellite-menu "siyamed")

You can easily integrate it into your project. <br>
Just Import the [yvelabe_satellite_menu_v1.jar](https://github.com/yvelabs/satellite_menu/blob/master/yvelabe_satellite_menu_v1.jar "yvelabe_satellite_menu_v1.jar")

you can set the originAngle and endAngle to change position of the satellite.

## Usage ##

### Layout

```xml
<com.yvelabs.satellitemenu.SatelliteMenu
        android:id="@+id/satellite_menu"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:background="@drawable/red" />

```

### Java

``` java
        	satelliteMenu = (SatelliteMenu) this.findViewById(R.id.satellite_menu);
		satllites = new ArrayList<SatelliteItemModel>();
		satllites.add(new SatelliteItemModel(1, R.drawable.satellite_1));
		satllites.add(new SatelliteItemModel(2, R.drawable.satellite_2));
		satllites.add(new SatelliteItemModel(3, R.drawable.satellite_3));
		satllites.add(new SatelliteItemModel(4, R.drawable.satellite_4));
		satllites.add(new SatelliteItemModel(5, R.drawable.satellite_5));

		SettingPara para = new SettingPara(90, 270, 200, R.drawable.planet_menu, satllites);
		
		AbstractAnimation anim = new DefaultAnimation();
		para.setMenuAnimation(anim);
		
		/*DefaultAnimation2 anim2 = new DefaultAnimation2();
		anim2.setSatelliteStartOffsetMax(200);
		anim2.setSatelliteStartOffsetMin(0);
		para.setMenuAnimation(anim2);*/
		
		try {
			satelliteMenu.setting(para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		satelliteMenu.setOnPlanetClickedListener(new OnPlanetClickedListener() {
			@Override
			public void onClick(View view) {
				
			}
		});
		
		satelliteMenu.setOnSatelliteClickedListener(new SatelliteMenu.OnSatelliteClickedListener() {
			@Override
			public void onClick(View v) {
				String a = "getLeft: " + v.getLeft() + ", getTop: "
						+ v.getTop() + ", getRight: " + v.getRight()
						+ ", getBottom: " + v.getBottom();
				Toast.makeText(MainActivity.this,  a, Toast.LENGTH_SHORT ).show();
			}
		});

```
