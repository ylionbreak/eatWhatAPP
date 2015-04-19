package science.hzl.random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.RevealColorView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

//star页面

//方法首字母小写
//变量全小写
//常量全大写加下划线_

public class MainActivity extends ActionBarActivity {
	public static int selectPlace = 0;
	public static int selectCircle = 0;
	static PlaceListViewAdapter placeListViewAdapter;
	static CircleListViewAdapter circleListViewAdapter;
	static CheckBoxAdapter checkBoxAdapter;
	static StarListViewAdapter starListViewAdapter;
	ListView restaurantListView;
	ListView placeListView;
	ListView circleListView;
	ListView starListView;
	MyAnimation myAnimation;
	static Button getResult;
	static Button setStarButton;
	static Button getStarButton;
	static public List<Restaurant> restaurantList;
	static private List<Circle> circleList;
	static private List<Place> placeList;
	static private DBManager dataManager;
	static public int currentPage =1;
	static public boolean isStar=false;
	TextView RandomResult;
	Toast toast;
	static public RevealColorView revealColorView;
	private int backgroundColor;
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1: {
					RandomChoose Randomchoose = new RandomChoose();
					RandomResult.setText(Randomchoose.getRandomChooseResult(dataManager.selectCheckedToList()));
				}break;
			}
			super.handleMessage(msg);
		}

	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bmob.initialize(this, "12c8bc8a266c15500eae632ad0aabe8f");

		placeList =new ArrayList<>();
		circleList = new ArrayList<>();
		restaurantList = new ArrayList<>();
		dataManager = new DBManager(this);
		myAnimation=new MyAnimation();
		//判断是否第一次使用
		SharedPreferences sharedPreferences= getSharedPreferences("first",ActionBarActivity.MODE_PRIVATE);
		Boolean firstUsed =sharedPreferences.getBoolean("RandomFirstUsed",false);
		if(!firstUsed) {
			UserData userData =new UserData("creat");
			userData.save(this);
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean("firstUsed", true);
			editor.apply();
			dataManager.add();
			Toast toastTell;
			toastTell=Toast.makeText(getApplicationContext(), "你是不是经常有纠结去吃什么时候"+'\n'+"没关系！有吃什么帮你决定^.^", Toast.LENGTH_LONG);
			toastTell.setGravity(Gravity.TOP, 0, 600);
			toastTell.show();
			toast=Toast.makeText(getApplicationContext(), "下面的按钮是选择已收藏的"+'\n'+"右上角是更新数据~"+'\n'+"接下来请选择你要去哪里吧~", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
		//初始化界面
		getResult          = (Button)   findViewById(R.id.random_choose);
		getStarButton      = (Button)   findViewById(R.id.get_star_button);
		setStarButton      = (Button)   findViewById(R.id.set_star_button);
		RandomResult       = (TextView) findViewById(R.id.result_text);
		starListView       = (ListView) findViewById(R.id.star_listview);
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
		starListView.setVisibility(View.GONE);
		//
		revealColorView = (RevealColorView) findViewById(R.id.reveal);
		backgroundColor = Color.parseColor("#FFFFFF");
		BmobQuery<MyWord> query4 = new BmobQuery<>();
		query4.setLimit(1);
		query4.findObjects(this, new FindListener<MyWord>() {
			@Override
			public void onSuccess(List<MyWord> object) {
				// TODO Auto-generated method stub
				if(object.size()==1) {
					toast = Toast.makeText(getApplicationContext(), object.get(0).word, Toast.LENGTH_SHORT);
					toast.show();
				}
			}
			@Override
			public void onError(int code, String msg) {

			}
		});
		//设置按钮
		getResult.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getResult.setEnabled(false);
				UserData userData =new UserData("random");
				userData.save(MainActivity.this);
				final int color = getColor(v);
				final Point p = getLocationInView(revealColorView, v);
				revealColorView.reveal(p.x, p.y, color, v.getHeight() / 2, 340, null);
				restaurantListView.setVisibility(View.GONE);
				RandomResult.setVisibility(View.VISIBLE);
				getResult.setText("再选一次");
				currentPage=4;
				MyAnimation myAnimation = new MyAnimation();
				Timer timer = new Timer();
				TimerTask task = new TimerTask(){
					public void run() {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				};
				timer.schedule(task, 0, 20);
				RandomResult.startAnimation(myAnimation.getRandomEffect(timer,getResult));
			}
		});
		setStarButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					dialog();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		getStarButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if(!isStar){
						currentPage=5;
						isStar=true;
						final int color = getColor(v);
						final Point p = getLocationInView(revealColorView, v);
						revealColorView.reveal(p.x, p.y, color, v.getHeight() / 2, 340, null);
						placeListView.startAnimation(myAnimation.getBeSmallAndDisappear());
						placeListView.setVisibility(View.GONE);
						starListViewAdapter = new StarListViewAdapter(MainActivity.this, dataManager.getStarName(), starListView, restaurantListView);
						starListView.setAdapter(starListViewAdapter);
						starListView.setVisibility(View.VISIBLE);
						starListView.startAnimation(myAnimation.getUpAndBig());
					}
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
			getStarButton.startAnimation(myAnimation.getButtonUp());
			getStarButton.setVisibility(View.VISIBLE);
		}else if(currentPage ==3 && !isStar){
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
		}else if(currentPage ==4){
			getResult.setText("吃什么?");
			final Point p = getLocationInView(revealColorView, getResult);
			revealColorView.hide(p.x, p.y, backgroundColor, 0, 300, null);
			currentPage =1;
			setPlace();
			placeListView.setVisibility(View.VISIBLE);
			RandomResult.setVisibility(View.GONE);
			placeListView.startAnimation(myAnimation.getBeBigAndAppear());
			circleListView.setVisibility(View.GONE);
			restaurantListView.setVisibility(View.GONE);
			getResult.setVisibility(View.INVISIBLE);
			getResult.startAnimation(myAnimation.getButtonDown());
			setStarButton.setVisibility(View.INVISIBLE);
			setStarButton.startAnimation(myAnimation.getButtonDown());
			getStarButton.startAnimation(myAnimation.getButtonUp());
			getStarButton.setVisibility(View.VISIBLE);
		}else if(currentPage == 5 && isStar){
			isStar=false;
			final Point p = getLocationInView(revealColorView, getStarButton);
			revealColorView.hide(p.x, p.y, backgroundColor, 0, 300, null);
			currentPage =1;
			setPlace();
			placeListView.setVisibility(View.VISIBLE);
			placeListView.startAnimation(myAnimation.getToRightAnimation());
			starListView.startAnimation(myAnimation.getBeSmallAndDisappearAndDown());
			starListView.setVisibility(View.GONE);
			circleListView.setVisibility(View.GONE);
			restaurantListView.setVisibility(View.GONE);
		}else if(currentPage == 5 && !isStar){
			isStar=false;
			currentPage =1;
			setPlace();
			placeListView.setVisibility(View.VISIBLE);
			placeListView.startAnimation(myAnimation.getToRightAnimation());
			restaurantListView.startAnimation(myAnimation.getBeSmallAndDisappearAndDown());
			restaurantListView.setVisibility(View.GONE);
			circleListView.setVisibility(View.GONE);
			starListView.setVisibility(View.GONE);
			getResult.setVisibility(View.INVISIBLE);
			getResult.startAnimation(myAnimation.getButtonDown());
			setStarButton.setVisibility(View.INVISIBLE);
			setStarButton.startAnimation(myAnimation.getButtonDown());
			getStarButton.startAnimation(myAnimation.getButtonUp());
			getStarButton.setVisibility(View.VISIBLE);
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		//getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.refresh:{
				UserData userData =new UserData("update");
				userData.save(this);
				BmobQuery<Place> query1 = new BmobQuery<>();
				query1.setLimit(1000);
				query1.findObjects(this, new FindListener<Place>() {
					@Override
					public void onSuccess(List<Place> object) {
						// TODO Auto-generated method stub
						dataManager.refreshPlaceData(object);
						toast=Toast.makeText(getApplicationContext(), "成功更新数据 请重启", Toast.LENGTH_SHORT);
						toast.show();
						if(currentPage==1){
							setPlace();
							placeListViewAdapter.notifyDataSetChanged();
						}
					}
					@Override
					public void onError(int code, String msg) {
						// TODO Auto-generated method stub
						toast=Toast.makeText(getApplicationContext(), "更新数据失败", Toast.LENGTH_SHORT);
						toast.show();
					}
				});
				BmobQuery<Circle> query2 = new BmobQuery<>();
				query2.setLimit(1000);
				query2.findObjects(this, new FindListener<Circle>() {
					@Override
					public void onSuccess(List<Circle> object) {
						// TODO Auto-generated method stub
						dataManager.refreshCircleData(object);
						toast=Toast.makeText(getApplicationContext(), "成功更新数据 请重启", Toast.LENGTH_SHORT);
						toast.show();
						if(currentPage==2) {
							setCircle();
							circleListViewAdapter.notifyDataSetChanged();
						}
					}
					@Override
					public void onError(int code, String msg) {
						// TODO Auto-generated method stub
						toast=Toast.makeText(getApplicationContext(), "更新数据失败", Toast.LENGTH_SHORT);
						toast.show();
					}
				});
				BmobQuery<Restaurant> query3 = new BmobQuery<>();
				query3.setLimit(1000);
				query3.findObjects(this, new FindListener<Restaurant>() {
					@Override
					public void onSuccess(List<Restaurant> object) {
						// TODO Auto-generated method stub
						dataManager.refreshRestaurantData(object);
						toast=Toast.makeText(getApplicationContext(), "成功更新数据 请重启", Toast.LENGTH_SHORT);
						toast.show();
						if(currentPage==3){
							setRestaurant();
							checkBoxAdapter.notifyDataSetChanged();
						}
					}
					@Override
					public void onError(int code, String msg) {
						// TODO Auto-generated method stub
						toast=Toast.makeText(getApplicationContext(), "更新数据失败", Toast.LENGTH_SHORT);
						toast.show();
					}
				});
			}
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

	protected void dialog() {
		final EditText editText=new EditText(this);
		Dialog alertDialog = new AlertDialog.Builder(this).
				setTitle("输入收藏名字").
				setView(editText).
				setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dataManager.addStar(editText.getText().toString());
						toast=Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_SHORT);
						toast.show();
					}
				})
				.setNegativeButton("取消", null).create();
				alertDialog.show();
	}

}
