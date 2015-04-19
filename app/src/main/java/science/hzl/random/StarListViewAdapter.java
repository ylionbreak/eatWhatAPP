package science.hzl.random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by YLion on 2015/4/19.
 */
public class StarListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<String> starName;
	ListView restaurantListView;
	ListView starListView;
	DBManager dataManager;
	public StarListViewAdapter(Context context, List<String> _starName,ListView _starListView ,ListView _restaurantListView) {
		this.mInflater = LayoutInflater.from(context);
		this.starName = _starName;
		this.restaurantListView = _restaurantListView;
		this.starListView = _starListView;
		dataManager = new DBManager(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return 0;
		return starName.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.starbutton, null);
			holder.button = (Button) convertView.findViewById(R.id.star_listview_button);
			holder.button.setText(starName.get(position));
			holder.button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MainActivity.isStar=false;
					MyAnimation myAnimation = new MyAnimation();

					//placeListView.startAnimation(myAnimation.getToLeftAnimation());
					//placeListView.setVisibility(View.INVISIBLE);
					//MainActivity.selectPlace = starName.get(position).id;


					final Point p = getLocationInView(MainActivity.revealColorView, MainActivity.getStarButton);
					MainActivity.revealColorView.hide(p.x, p.y, Color.parseColor("#FFFFFF"), 0, 300, null);

					MainActivity.restaurantList.clear();
					for (Iterator iter = dataManager.restaurantQuery(dataManager.getStarRestaurant(position+1)).iterator(); iter.hasNext(); ) {
						MainActivity.restaurantList.add( (Restaurant) iter.next() ) ;

					}
					//MainActivity.restaurantList=dataManager.restaurantQuery(dataManager.getStarRestaurant(position+1));
					Log.e("x", MainActivity.restaurantList.get(1).name);
					MainActivity.checkBoxAdapter.notifyDataSetChanged();

					restaurantListView.setVisibility(View.VISIBLE);
					restaurantListView.startAnimation(myAnimation.getBeBigAndAppear());


					starListView.startAnimation(myAnimation.getToLeftAnimation());
					starListView.setVisibility(View.GONE);

					MainActivity.getResult.setVisibility(View.VISIBLE);
					MainActivity.getResult.startAnimation(myAnimation.getButtonUp());
					MainActivity.setStarButton.setVisibility(View.VISIBLE);
					MainActivity.setStarButton.startAnimation(myAnimation.getButtonUp());
					MainActivity.getStarButton.startAnimation(myAnimation.getButtonDown());
					MainActivity.getStarButton.setVisibility(View.INVISIBLE);

					//circleListView.setVisibility(View.VISIBLE);
					//circleListView.startAnimation(myAnimation.getBeBigAndAppear());
				}

			});
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	public static class ViewHolder {
		public Button button;
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
}
