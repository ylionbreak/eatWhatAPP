package science.hzl.random;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by YLion on 2015/4/12.
 */
public class DBManager {
	private MySQLite helper;
	static private SQLiteDatabase db;

	public DBManager(Context context, int version) {
		helper = new MySQLite(context, version);
		//因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
		//所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}

	public DBManager(Context context) {
		helper = new MySQLite(context);
		db = helper.getWritableDatabase();
	}

	public void addPlace(Place place) {
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO place VALUES(?, ?)", new Object[]{place._id, place.name});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public void addCircle(Circle circle) {
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO circle VALUES(?, ?, ?)", new Object[]{circle._id, circle.name, circle.belongPlace});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public void addRestaurant(Restaurant restaurant) {
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO restaurant VALUES(?, ?, ?, ?)", new Object[]{restaurant._id, restaurant.name, restaurant.belongCircle,"1"});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public void addStar(){
		db.beginTransaction();
		try {
			db.execSQL("INSERT INTO star VALUES(null, ?)", new Object[]{selectCheckedRestaurantData()});
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	public List<String> getStar(){
		String string =new String();
		Cursor c = db.rawQuery("SELECT * FROM star ", null );
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

	public List<Place> placeQuery() {
		ArrayList<Place> places = new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM place", null);
		while (c.moveToNext()) {
			Place place = new Place();
			place._id = c.getInt(c.getColumnIndex("_id"));
			place.name = c.getString(c.getColumnIndex("name"));
			places.add(place);
		}
		c.close();
		return places;
	}

	public List<Circle> circleQuery() {
		ArrayList<Circle> circles = new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM circle", null);
		while (c.moveToNext()) {
			Circle circle = new Circle();
			circle._id = c.getInt(c.getColumnIndex("_id"));
			circle.name = c.getString(c.getColumnIndex("name"));
			circle.belongPlace = c.getInt(c.getColumnIndex("belongPlace"));
			circles.add(circle);
		}
		c.close();
		return circles;
	}

	public List<Restaurant> restaurantQuery() {
		ArrayList<Restaurant> restaurants = new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM restaurant ",null);
		while (c.moveToNext()) {
			Restaurant restaurant = new Restaurant();
			restaurant._id = c.getInt(c.getColumnIndex("_id"));
			restaurant.name = c.getString(c.getColumnIndex("name"));
			restaurant.belongCircle = c.getInt(c.getColumnIndex("belongCircle"));
			restaurants.add(restaurant);
		}
		c.close();
		return restaurants;
	}

	public List<String> restaurantQuery(List<String> strings) {
		ArrayList<String> strings1 = new ArrayList<>();

		for (Iterator iter = strings.iterator(); iter.hasNext(); ) {

			Cursor c = db.rawQuery("SELECT * FROM restaurant where _id =?", new String[]{(String) iter.next()});
			while (c.moveToNext()) {
				strings1.add(c.getString(c.getColumnIndex("name")));
			}
			c.close();

		}

		return strings1;
	}

	public List<String> selectChecked() {
		List<String> strings =new ArrayList<>();
		Cursor c = db.rawQuery("SELECT * FROM restaurant where belongCircle = ? and isCheck = '1' ",new String[]{String.valueOf(MainActivity.selectCircle)});
		while (c.moveToNext()) {
			strings.add(c.getString(c.getColumnIndex("name")));
		}
		c.close();
		return strings;
	}

	public String selectCheckedRestaurantData() {
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
		db.execSQL("CREATE TABLE IF NOT EXISTS star" + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, starrestaurant VARCHAR)");
	}
}
