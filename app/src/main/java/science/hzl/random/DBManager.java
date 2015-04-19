package science.hzl.random;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YLion on 2015/4/12.
 */
public class DBManager {
	private MySQLite helper;
	static private SQLiteDatabase db;
	private Context context;
	public DBManager(Context context, int version) {
		helper = new MySQLite(context, version);
		//因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
		//所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}

	public DBManager(Context context) {
		this.context=context;
		helper = new MySQLite(context);
		db = helper.getWritableDatabase();
	}

	public void addPlace(Place place) {
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO place VALUES(?, ?)", new Object[]{place.id, place.name});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public void addCircle(Circle circle) {
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO circle VALUES(?, ?, ?)", new Object[]{circle.id, circle.name, circle.belongPlace});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public void addRestaurant(Restaurant restaurant) {
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO restaurant VALUES(?, ?, ?, ?)", new Object[]{restaurant.id, restaurant.name, restaurant.belongCircle,"1"});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public void addStar(String name){
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO star VALUES(null, ?,?)", new Object[]{name,selectCheckedIdToString()});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public List<String> getStarRestaurant(int id){
		String string =new String();
		Cursor c = db.rawQuery("SELECT * FROM star where _id = ?", new String[]{String.valueOf(id) } );
		while (c.moveToNext()) {
			string=c.getString( c.getColumnIndex("starrestaurant") );
		}
		c.close();
		List<String> strings= new ArrayList<>();
		for(int i=0;i<=string.length()-2;i=i+2){
			strings.add(string.substring(i,i+2));
		}
		return strings;
	}

//	public List<String> getStarName(){
//		String string =new String();
//		Cursor c = db.rawQuery("SELECT * FROM star ", null );
//		while (c.moveToNext()) {
//			string=c.getString( c.getColumnIndex("name") );
//		}
//		c.close();
//		List<String> strings= new ArrayList<>();
//		for(int i=0;i<=string.length()-2;i=i+2){
//			strings.add(string.substring(i,i+2));
//		}
//		return strings;
//	}

	public List<String> getStarName(){
		String string = new String();
		List<String> strings= new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM star ", null );
		while (c.moveToNext()) {
			string=c.getString( c.getColumnIndex("name") );
			strings.add(string);
		}
		c.close();
		return strings;
	}

	public List<Place> placeQueryAll() {
		ArrayList<Place> places = new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM place", null);
		while (c.moveToNext()) {
			Place place = new Place();
			place.id = c.getInt(c.getColumnIndex("_id"));
			place.name = c.getString(c.getColumnIndex("name"));
			places.add(place);
		}
		c.close();
		return places;
	}

	public List<Circle> circleQueryAll() {
		ArrayList<Circle> circles = new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM circle", null);
		while (c.moveToNext()) {
			Circle circle = new Circle();
			circle.id = c.getInt(c.getColumnIndex("_id"));
			circle.name = c.getString(c.getColumnIndex("name"));
			circle.belongPlace = c.getInt(c.getColumnIndex("belongPlace"));
			circles.add(circle);
		}
		c.close();
		return circles;
	}

	public List<Restaurant> restaurantQueryAll() {
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM restaurant ",null);
		while (c.moveToNext()) {
			Restaurant restaurant = new Restaurant();
			restaurant.id = c.getInt(c.getColumnIndex("_id"));
			restaurant.name = c.getString(c.getColumnIndex("name"));
			restaurant.belongCircle = c.getInt(c.getColumnIndex("belongCircle"));
			restaurants.add(restaurant);
		}
		c.close();
		return restaurants;
	}

	public List<Restaurant> restaurantQuery(List<String> strings) {
		ArrayList<Restaurant> strings1 = new ArrayList<>();

		for (Iterator iter = strings.iterator(); iter.hasNext(); ) {

			Cursor c = db.rawQuery("SELECT * FROM restaurant where _id =?", new String[]{(String) iter.next()});
			while (c.moveToNext()) {
				Restaurant restaurant=new Restaurant();
				restaurant.name=c.getString(c.getColumnIndex("name"));
				strings1.add(restaurant);
			}
			c.close();

		}

		return strings1;
	}

	public List<String> selectCheckedToList() {
		List<String> strings =new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM restaurant where belongCircle = ? and isCheck = '1' ",new String[]{String.valueOf(MainActivity.selectCircle)});
		while (c.moveToNext()) {
			strings.add(c.getString(c.getColumnIndex("name")));
		}
		c.close();
		return strings;
	}

	public String selectCheckedIdToString() {
		Cursor c = db.rawQuery("SELECT * FROM restaurant where belongCircle = ? and isCheck = '1' ",new String[]{String.valueOf(MainActivity.selectCircle)});
		StringBuilder stringBuilder =new StringBuilder();
		while (c.moveToNext()) {
			if(c.getInt(c.getColumnIndex("_id"))<10){
				stringBuilder.append(  "0"+String.valueOf( c.getInt(c.getColumnIndex("_id")) )  );
			}else {
				stringBuilder.append(  String.valueOf( c.getInt(c.getColumnIndex("_id")) )  );
			}

		}
		c.close();
		return stringBuilder.toString();
	}

	public static boolean isChecked(String name){
		Cursor c = db.rawQuery("SELECT * FROM restaurant where name = ? ",new String[]{name});
		c.moveToNext();
			if(Integer.parseInt( c.getString(c.getColumnIndex("isCheck")) )==1){
				c.close();
				return true;
			}else{
				c.close();
				return false;
			}
	}

	static public void changeChecked(String string1,String string2){
			db.execSQL("update restaurant set isCheck = ? where name = ? ",new String[]{string2,string1});
	}

	public void closeDB() {
		db.close();
	}

	public void delete() {
		db.execSQL("drop TABLE IF EXISTS place ");
		db.execSQL("drop TABLE IF EXISTS circle");
		db.execSQL("drop TABLE IF EXISTS restaurant");
		db.execSQL("drop TABLE IF EXISTS star");
		db.execSQL("CREATE TABLE IF NOT EXISTS place" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS circle" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR ,belongPlace INT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS restaurant" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR ,belongCircle INT,isCheck VARCHAR)");
		db.execSQL("CREATE TABLE IF NOT EXISTS star" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR, starrestaurant VARCHAR)");
	}
	//加入数据库
	public void add() {
		delete();
		XmlPullParser xmlReader = context.getResources().getXml(R.xml.store);
		try {
			while (xmlReader.getEventType() != XmlResourceParser.END_DOCUMENT) {
				if (xmlReader.getEventType() == XmlResourceParser.START_TAG) {
					String tagName = xmlReader.getName();
					if (tagName.equals("place")) {
						Place place = new Place(Integer.parseInt(xmlReader.getAttributeValue(0)), xmlReader.getAttributeValue(1));
						addPlace(place);
					} else if (tagName.equals("circle")) {
						Circle circle = new Circle(Integer.parseInt(xmlReader.getAttributeValue(0)), xmlReader.getAttributeValue(1), Integer.parseInt(xmlReader.getAttributeValue(2)));
						addCircle(circle);
					} else if (tagName.equals("restaurant")) {
						Restaurant restaurant = new Restaurant(Integer.parseInt(xmlReader.getAttributeValue(0)), xmlReader.getAttributeValue(1), Integer.parseInt(xmlReader.getAttributeValue(2)));
						addRestaurant(restaurant);
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

	public void refreshPlaceData(List<Place> object){
		db.execSQL("drop TABLE IF EXISTS place ");
		db.execSQL("CREATE TABLE IF NOT EXISTS place" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR)");
		for (Iterator iter = object.iterator(); iter.hasNext(); ) {
			Place place  =(Place)iter.next();
			addPlace(place);
		}
	}

	public void refreshCircleData(List<Circle> object){
		db.execSQL("drop TABLE IF EXISTS circle");
		db.execSQL("CREATE TABLE IF NOT EXISTS circle" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR ,belongPlace INT)");
		for (Iterator iter = object.iterator(); iter.hasNext(); ) {
			Circle circle  =(Circle)iter.next();
			addCircle(circle);
		}
	}

	public void refreshRestaurantData(List<Restaurant> object){
		db.execSQL("drop TABLE IF EXISTS restaurant");
		db.execSQL("CREATE TABLE IF NOT EXISTS restaurant" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR ,belongCircle INT,isCheck VARCHAR)");
		for (Iterator iter = object.iterator(); iter.hasNext(); ) {
			Restaurant restaurant =(Restaurant)iter.next();
			addRestaurant(restaurant);
		}
	}


}
