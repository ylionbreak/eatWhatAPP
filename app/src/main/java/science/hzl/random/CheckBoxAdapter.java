package science.hzl.random;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.List;

/**
 * Created by YLion on 2015/4/12.
 */
public class CheckBoxAdapter extends BaseAdapter {
	List<Restaurant> restaurantsList;
	private LayoutInflater mInflater;


	public CheckBoxAdapter(Context context, List<Restaurant> _restaurantsLis) {
		this.mInflater = LayoutInflater.from(context);
		this.restaurantsList = _restaurantsLis;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return 0;
		return restaurantsList.size();
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
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.checkbox_layout, null);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
			holder.checkBox.setText(restaurantsList.get(position).name);
			if ( DBManager.isChecked(restaurantsList.get(position).name) ) {
				holder.checkBox.setChecked(true);
			}else{
				holder.checkBox.setChecked(false);
			}
			holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						DBManager.changeChecked(restaurantsList.get(position).name,"1");
					}else{
						DBManager.changeChecked(restaurantsList.get(position).name,"0");
					}
				}
			});
			convertView.setTag(holder);
		return convertView;
	}

	public final class ViewHolder {
		public CheckBox checkBox;
	}

}
