package science.hzl.random;

import android.app.ActionBar;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//声明在前还是在后
//对象数组
//什么要自己开一个类
//主方法很多怎么办

//更新+数据库完善 actionbar 配色 种类 app更新bmob logo star页面

//方法首字母小写
//变量全小写
//常量全大写加下划线_

public class MainActivity extends ActionBarActivity {
	public static int selectPlace = 0;
	public static int selectCircle = 0;
	static PlaceListViewAdapter placeListViewAdapter;
	static CircleListViewAdapter circleListViewAdapter;
	static CheckBoxAdapter checkBoxAdapter;
	RestaurantListView restaurantListView;
	ListView placeListView;
	ListView circleListView;
	MyAnimation myAnimation;
	static Button getResult;
	static Button setStarButton;
	static Button getStarButton;
	static private List<String> restaurantList;
	static private List<Circle> circleList;
	static private List<Place> placeList;
	static private DBManager dataManager;
	static public int currentPage =1;
	TextView RandomResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		placeList =new ArrayList<>();
		circleList = new ArrayList<>();
		restaurantList = new ArrayList<>();
		dataManager = new DBManager(this);
		myAnimation=new MyAnimation();
		add();
		//初始化界面
		RandomResult = (TextView) findViewById(R.id.result_text);
		getResult = (Button) findViewById(R.id.random_choose);
		getResult.setVisibility(View.INVISIBLE);
		getStarButton =(Button)findViewById(R.id.get_star_button);
		setStarButton =(Button)findViewById(R.id.set_star_button);
		setStarButton.setVisibility(View.INVISIBLE);
		//设置
		placeListView = (ListView) findViewById(R.id.place_listview);
		circleListView = (ListView) findViewById(R.id.circle_listview);
		restaurantListView = (RestaurantListView) findViewById(R.id.checkbox_listview);
		setPlace();
		placeListViewAdapter = new PlaceListViewAdapter(this, placeList, placeListView,circleListView);
		placeListView.setAdapter(placeListViewAdapter);
		//设置circleListView
		circleListViewAdapter = new CircleListViewAdapter(this, circleList, circleListView, restaurantListView);
		circleListView.setAdapter(circleListViewAdapter);
		//设置restaurantListView
		checkBoxAdapter = new CheckBoxAdapter(this, restaurantList);
		restaurantListView.setAdapter(checkBoxAdapter);
		//设置按钮
		getResult.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RandomChoose Randomchoose = new RandomChoose();
				restaurantListView.setVisibility(View.GONE);
				RandomResult.setVisibility(View.VISIBLE);
				RandomResult.setText(Randomchoose.getRandomChooseResult(dataManager.selectChecked()));
			}
		});
		//////
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
		////////
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

	//加入数据库
	public void add() {
		dataManager.delete();
		XmlPullParser xmlReader = getResources().getXml(R.xml.store);
		try {
			while (xmlReader.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (xmlReader.getEventType() == XmlResourceParser.START_TAG) {
					String tagName = xmlReader.getName();
					if (tagName.equals("place")) {
						Place place = new Place(Integer.parseInt(xmlReader.getAttributeValue(0)), xmlReader.getAttributeValue(1));
						dataManager.addPlace(place);
					} else if (tagName.equals("circle")) {
						Circle circle = new Circle(Integer.parseInt(xmlReader.getAttributeValue(0)), xmlReader.getAttributeValue(1), Integer.parseInt(xmlReader.getAttributeValue(2)));
						dataManager.addCircle(circle);
					} else if (tagName.equals("restaurant")) {
						Restaurant restaurant = new Restaurant(Integer.parseInt(xmlReader.getAttributeValue(0)), xmlReader.getAttributeValue(1), Integer.parseInt(xmlReader.getAttributeValue(2)));
						dataManager.addRestaurant(restaurant);
					}
				}
				xmlReader.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setPlace() {
		placeList.clear();
		for (Iterator iter = dataManager.placeQuery().iterator(); iter.hasNext(); ) {
			Place place  =(Place)iter.next();
			placeList.add(place);
		}
	}

	public static void setCircle() {
		circleList.clear();
		for (Iterator iter = dataManager.circleQuery().iterator(); iter.hasNext(); ) {
			Circle circle = (Circle) iter.next();
			if (circle.belongPlace == selectPlace) {
				circleList.add(circle);
			}
		}
	}

	public static void setRestaurant() {
		restaurantList.clear();
		for (Iterator iter = dataManager.restaurantQuery().iterator(); iter.hasNext(); ) {
			Restaurant restaurant = (Restaurant) iter.next();
			if (restaurant.belongCircle == selectCircle) {
				restaurantList.add(restaurant.name);
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
			circleList =new ArrayList<>();
			circleListViewAdapter = new CircleListViewAdapter(this, circleList, circleListView, restaurantListView);
			circleListView.setAdapter(circleListViewAdapter);
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
			restaurantList = new ArrayList<>();
			checkBoxAdapter = new CheckBoxAdapter(this, restaurantList);
			restaurantListView.setAdapter(checkBoxAdapter);
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

}
