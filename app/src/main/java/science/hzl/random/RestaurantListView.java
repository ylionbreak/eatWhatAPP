package science.hzl.random;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YLion on 2015/4/12.
 */
public class RestaurantListView extends ListView {

	List<CheckBox> list = new ArrayList<>();
	private List<CheckBox> mData;

	public RestaurantListView(Context context) {
		super(context);
		//this.mData = getData(context);
	}

	public RestaurantListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//this.mData = getData(context);
	}

	public RestaurantListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//this.mData = getData(context);
	}

	//	@Override
//	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//	}
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	}
	public void setData(CheckBox checkBox) {
		list.add(checkBox);
	}

	private List<CheckBox> getData(Context context) {
		List<CheckBox> list = new ArrayList<>();
		CheckBox map = (CheckBox) findViewById(R.id.checkbox);
		return list;
	}

	// ListView 中某项被选中后的逻辑

	protected void onListItemClick(ListView l, View v, int position, long id) {


	}


}
