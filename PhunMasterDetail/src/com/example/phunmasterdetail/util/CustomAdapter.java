package com.example.phunmasterdetail.util;

import java.util.List;

import com.example.phunmasterdetail.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter implements OnClickListener {
	private Context mContext;
	private List<Venue> venueList;

	public CustomAdapter(Context mContext, List<Venue> venueList) {
		this.mContext = mContext;
		this.venueList=venueList;
	}

	public int getCount() {
		return venueList.size();
	}

	public Object getItem(int position) {
		return venueList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup viewGroup) {
		Venue mList = venueList.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_row, null);
			ViewHolder mViewHolder = new ViewHolder();
			mViewHolder.mNameHolder = (TextView) convertView
					.findViewById(R.id.name);
			mViewHolder.mAddressHolder = (TextView) convertView
					.findViewById(R.id.address);
			convertView.setTag(mViewHolder);
		}

		ViewHolder mHolder = (ViewHolder) convertView.getTag();

		mHolder.mNameHolder.setText(mList.getName());
		mHolder.mAddressHolder.setText(mList.getAddress()+","+mList.getCity()+","+mList.getState());

		return convertView;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	static class ViewHolder {
		public TextView mNameHolder;
		public TextView mAddressHolder;
	}
}