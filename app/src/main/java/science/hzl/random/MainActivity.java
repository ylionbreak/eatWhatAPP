package science.hzl.random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.markushi.ui.RevealColorView;

//更新+数据库完善+bmob actionbar 配色 logo star页面

//方法首字母小写
//变量全小写
//常量全大写加下划线_

public class MainActivity extends ActionBarActivity {
	public static int selectPlace = 0;
	public static int selectCircle = 0;
	static PlaceListViewAdapter placeListViewAdapter;
	static CircleListViewAdapter circleListViewAdapter;
	static CheckBoxAdapter checkBoxAdapter;
	ListView restaurantListView;
	ListView placeListView;
	ListView circleListView;
	MyAnimation myAnimation;
	static Button getResult;
	static Button setStarButton;
	static Button getStarButton;
	static private List<Restaurant> restaurantList;
	static private List<Circle> circleList;
	static private List<Place> placeList;
	static private DBManager dataManager;
	static public int currentPage =1;
	TextView RandomResult;

	private View selectedView;
	private RevealColorView revealColorView;
	private int backgroundColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		placeList =new ArrayList<>();
		circleList = new ArrayList<>();
		restaurantList = new ArrayList<>();
		dataManager = new DBManager(this);
		myAnimation=new MyAnimation();
		//判断是否第一次使用
		SharedPreferences sharedPreferences= getSharedPreferences("first",ActionBarActivity.MODE_PRIVATE);
		Boolean firstUsed =sharedPreferences.getBoolean("firstUsed",false);
		if(!firstUsed) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean("firstUsed", true);
			Log.e("x","write!!!!!!!!!!");
			editor.apply();
			dataManager.add();
		}
		//初始化界面
		getResult          = (Button)   findViewById(R.id.random_choose);
		getStarButton      = (Button)   findViewById(R.id.get_star_button);
		setStarButton      = (Button)   findViewById(R.id.set_star_button);
		RandomResult       = (TextView) findViewById(R.id.result_text);
		placeListView      = (ListView) findViewById(R.id.place_listview);
		circleListView     = (ListView) findViewById(R.id.circle_listview);
		restaurantListView = (ListView) findViewById(R.id.checkbox_listview);
		setStarButton.setVisibility(View.INVISIBLE);
		getResult.setVisibility(View.INVISIBLE);
		//设置
		setPlace();
		placeListViewAdapter = new PlaceListViewAdapter(this, placeList, placeListView,circleListView);
		placeListView.setAdapter(placeListViewAdapter);
		circleListViewAdapter = new CircleListViewAdapter(this, circleList, circleListView, restaurantListView);
		circleListView.setAdapter(circleListViewAdapter);
		checkBoxAdapter = new CheckBoxAdapter(this, restaurantList);
		restaurantListView.setAdapter(checkBoxAdapter);
		//
		revealColorView = (RevealColorView) findViewById(R.id.reveal);
		backgroundColor = Color.parseColor("#FFFFFF");
		//设置按钮
		getResult.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("x","xxxxx");
				final int color = getColor(v);
				final Point p = getLocationInView(revealColorView, v);

				if (selectedView == v) {
					revealColorView.hide(p.x, p.y, backgroundColor, 0, 300, null);
					selectedView = null;
				} else {
					revealColorView.reveal(p.x, p.y, color, v.getHeight() / 2, 340, null);
					selectedView = v;
				}

				RandomChoose Randomchoose = new RandomChoose();
				restaurantListView.setVisibility(View.GONE);
				RandomResult.setVisibility(View.VISIBLE);
				RandomResult.setText(Randomchoose.getRandomChooseResult(dataManager.selectCheckedToList()));
			}
		});
		setStarButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					dataManager.addStar();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		getStarButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					RandomChoose Randomchoose = new RandomChoose();
					//restaurantListView.setVisibility(View.GONE);
					RandomResult.setVisibility(View.VISIBLE);
					circleListView.setVisibility(View.GONE);
					RandomResult.setText(Randomchoose.getRandomChooseResult(dataManager.restaurantQuery(dataManager.getStar())));
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		dataManager.closeDB();
	}



	private void setPlace() {
		placeList.clear();
		for (Iterator iter = dataManager.placeQueryAll().iterator(); iter.hasNext(); ) {
			Place place  =(Place)iter.next();
			placeList.add(place);
		}
	}

	public static void setCircle() {
		circleList.clear();
		for (Iterator iter = dataManager.circleQueryAll().iterator(); iter.hasNext(); ) {
			Circle circle = (Circle) iter.next();
			if (circle.belongPlace == selectPlace) {
				circleList.add(circle);
			}
		}
	}

	public static void setRestaurant() {
		restaurantList.clear();
		for (Iterator iter = dataManager.restaurantQueryAll().iterator(); iter.hasNext(); ) {
			Restaurant restaurant = (Restaurant) iter.next();
			if (restaurant.belongCircle == selectCircle) {
				restaurantList.add(restaurant);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(currentPage ==1) {
			return super.onKeyDown(keyCode, event);
		}else if(currentPage ==2){
			RandomResult.setVisibility(View.GONE);
			currentPage =1;
			setPlace();
			circleListView.startAnimation(myAnimation.getBeSmallAndDisappear());
			circleListView.setVisibility(View.INVISIBLE);
			placeListView.startAnimation(myAnimation.getToRightAnimation());
			placeListView.setVisibility(View.VISIBLE);
//			circleList =new ArrayList<>();
//			circleListViewAdapter = new CircleListViewAdapter(this, circleList, circleListView, restaurantListView);
//			circleListView.setAdapter(circleListViewAdapter);
		}else if(currentPage ==3){
			RandomResult.setVisibility(View.GONE);
			currentPage =2;
			setCircle();
			circleListView.startAnimation(myAnimation.getToRightAnimation());
			circleListView.setVisibility(View.VISIBLE);
			restaurantListView.startAnimation(myAnimation.getBeSmallAndDisappear());
			restaurantListView.setVisibility(View.GONE);
			getResult.setVisibility(View.INVISIBLE);
			getResult.startAnimation(myAnimation.getButtonDown());
			setStarButton.setVisibility(View.INVISIBLE);
			setStarButton.startAnimation(myAnimation.getButtonDown());
			getStarButton.startAnimation(myAnimation.getButtonUp());
			getStarButton.setVisibility(View.VISIBLE);
//			restaurantList = new ArrayList<>();
//			checkBoxAdapter = new CheckBoxAdapter(this, restaurantList);
//			restaurantListView.setAdapter(checkBoxAdapter);
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// 当ActionBar图标被点击时调用
				break;
		}
		return super.onOptionsItemSelected(item);
	}



	private Point getLocationInView(View src, View target) {
		final int[] l0 = new int[2];
		src.getLocationOnScreen(l0);

		final int[] l1 = new int[2];
		target.getLocationOnScreen(l1);

		l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
		l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

		return new Point(l1[0], l1[1]);
	}

	private int getColor(View view) {
		return Color.parseColor((String) view.getTag());
	}





}
