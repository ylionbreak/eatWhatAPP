package science.hzl.random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by YLion on 2015/4/12.
 */
public class PlaceListViewAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Place> buttons;
	ListView placeListView;
	ListView circleListView;

	public PlaceListViewAdapter(Context context, List<Place> _buttons, ListView _placeListView, ListView _circleListView) {
		this.mInflater = LayoutInflater.from(context);
		this.buttons = _buttons;
		this.placeListView = _placeListView;
		this.circleListView = _circleListView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return 0;
		return buttons.size();
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
			convertView = mInflater.inflate(R.layout.button_layout, null);
			holder.button = (Button) convertView.findViewById(R.id.listview_button);
			holder.button.setText(buttons.get(position).name);
			holder.button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MainActivity.currentPage =2;
					MyAnimation myAnimation = new MyAnimation();
					placeListView.startAnimation(myAnimation.getToLeftAnimation());
					placeListView.setVisibility(View.INVISIBLE);
					MainActivity.selectPlace = buttons.get(position).id;
					MainActivity.setCircle();
					MainActivity.circleListViewAdapter.notifyDataSetChanged();
					MainActivity.getStarButton.startAnimation(myAnimation.getButtonDown());
					MainActivity.getStarButton.setVisibility(View.INVISIBLE);
					circleListView.setVisibility(View.VISIBLE);
					circleListView.startAnimation(myAnimation.getBeBigAndAppear());
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

}
