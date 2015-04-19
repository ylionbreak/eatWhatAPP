package science.hzl.random;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by YLion on 2015/4/12.
 */
public class CircleListViewAdapter extends BaseAdapter {

	ListView circleListView;
	ListView restaurantListView;
	private LayoutInflater mInflater;
	private List<Circle> circleList;

	public CircleListViewAdapter(Context context, List<Circle> _circleList, ListView _circleListView, ListView _restaurantListView) {
		this.mInflater = LayoutInflater.from(context);
		this.circleList = _circleList;
		this.circleListView= _circleListView;
		this.restaurantListView = _restaurantListView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return 0;
		return circleList.size();
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
//		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.button_layout, null);
			holder.button = (Button) convertView.findViewById(R.id.listview_button);
			holder.button.setText(circleList.get(position).name);
			holder.button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MainActivity.currentPage =3;
					MyAnimation myAnimation = new MyAnimation();
					circleListView.startAnimation(myAnimation.getToLeftAnimation());
					circleListView.setVisibility(View.GONE);
					MainActivity.selectCircle = circleList.get(position).id;
					MainActivity.setRestaurant();
					MainActivity.checkBoxAdapter.notifyDataSetChanged();
					restaurantListView.setVisibility(View.VISIBLE);
					restaurantListView.startAnimation(myAnimation.getBeBigAndAppear());
					MainActivity.getResult.setVisibility(View.VISIBLE);
					MainActivity.getResult.startAnimation(myAnimation.getButtonUp());
					MainActivity.setStarButton.setVisibility(View.VISIBLE);
					MainActivity.setStarButton.startAnimation(myAnimation.getButtonUp());

				}
			});
			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		return convertView;
	}

	public final class ViewHolder {
		public Button button;
	}

}
