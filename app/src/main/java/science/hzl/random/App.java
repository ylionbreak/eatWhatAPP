package science.hzl.random;

import android.app.Application;
import android.content.Context;

/**
 * Created by YLion on 2015/4/17.
 */
public class App extends Application{
	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
	}

	public static Context getContext() {
		return mContext;
	}
	
}
