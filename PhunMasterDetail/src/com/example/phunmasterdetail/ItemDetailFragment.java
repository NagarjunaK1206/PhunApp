package com.example.phunmasterdetail;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.phunmasterdetail.util.PhunMasterConstants;
import com.example.phunmasterdetail.util.ScheduleItem;
import com.example.phunmasterdetail.util.Venue;

public class ItemDetailFragment extends SherlockFragment {


	private Venue mVenue;
	static Bitmap mNoImageBitmap;
	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(PhunMasterConstants.ARG_ITEM_ID)) {
			mVenue = ItemListFragment.venueList.get(Integer
					.parseInt(getArguments().getString(PhunMasterConstants.ARG_ITEM_ID)) - 1);

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mRootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);
		SimpleDateFormat mDateFormat1 = new SimpleDateFormat(PhunMasterConstants.DATE_FORMAT_1);
		SimpleDateFormat mDateFormat2 = new SimpleDateFormat(PhunMasterConstants.DATE_FORMAT_2);

		// Show the dummy content as text in a TextView.
		if (mVenue != null) {
			LinearLayout mTextLayout = (LinearLayout) mRootView
					.findViewById(R.id.text_layout);
			TextView mAddress1 = new TextView(mRootView.getContext());
			mAddress1.setPadding(0, 5, 0, 0);
			mAddress1.setText(mVenue.getAddress());
			mTextLayout.addView(mAddress1);
			
			TextView mAddress2 = new TextView(mRootView.getContext());
			mAddress2.setPadding(0, 0, 0, 5);
			mAddress2.setText(mVenue.getCity()
					+ ", " + mVenue.getState()+ " " + mVenue.getZip());
			mTextLayout.addView(mAddress2);
			
			((TextView) mRootView.findViewById(R.id.item_detail))
					.setText(mVenue.getName());
			new DownloadImageInBackground(
					(ImageView) mRootView.findViewById(R.id.imageView1))
					.execute(mVenue.getImageUrl());
			mNoImageBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.no_image);
			List<ScheduleItem> mScheduleList = ItemListFragment.venueList
					.get(Integer
							.parseInt(getArguments().getString(PhunMasterConstants.ARG_ITEM_ID)) - 1)
					.getSchedule();
			for (ScheduleItem mSchedule : mScheduleList) {
				String mStartDate = printInLocalTime(mSchedule.getStart_date(),
						mDateFormat1);
				String mEndDate = printInLocalTime(mSchedule.getEnd_date(),
						mDateFormat2);
				TextView mDate = new TextView(mRootView.getContext());
				mDate.setPadding(0, 5, 0, 5);
				mDate.setText("" + mStartDate + " to " + mEndDate);
				mTextLayout.addView(mDate);
			}
		}

		return mRootView;
	}

	private String printInLocalTime(String string, SimpleDateFormat reqFormat) {
		// TODO Auto-generated method stub
		String mInput = string;
		Calendar mCal = Calendar.getInstance();
		TimeZone mUtc = mCal.getTimeZone();
		// TimeZone utc = TimeZone.getTimeZone("UTC");
		SimpleDateFormat mDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss z");
		SimpleDateFormat mNewDateFormat = reqFormat;
		mDateFormat.setTimeZone(mUtc);
		Date dt = null;
		// GregorianCalendar mGcal = new GregorianCalendar(utc);
		try {
			mCal.setTime(mDateFormat.parse(mInput));
			dt = mDateFormat.parse(mInput);
			Log.i("phun", "====time====" + dt.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mNewDateFormat.format(dt);
	}

}

class DownloadImageInBackground extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;

	public DownloadImageInBackground(ImageView bmImage) {
		this.bmImage = bmImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String mUrl = urls[0];
		Bitmap mBitmapImage = null;
		try {
			InputStream in = new java.net.URL(mUrl).openStream();
			mBitmapImage = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mBitmapImage;
	}

	protected void onPostExecute(Bitmap result) {
		if(result!=null){
			bmImage.setImageBitmap(result);
		}
		else{
			bmImage.setImageBitmap(ItemDetailFragment.mNoImageBitmap);
		}
	}
}