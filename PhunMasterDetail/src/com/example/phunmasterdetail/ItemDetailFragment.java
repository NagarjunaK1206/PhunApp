package com.example.phunmasterdetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phunmasterdetail.util.PhunMasterConstants;
import com.example.phunmasterdetail.util.ScheduleItem;
import com.example.phunmasterdetail.util.Venue;
import com.squareup.picasso.Picasso;

public class ItemDetailFragment extends Fragment {

	private Venue mVenue;
	private ProgressBar mProgressBar;
	private Context context;
	private View mRootView;

	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(PhunMasterConstants.ARG_ITEM_ID)) {
			mVenue = ItemListFragment.venueList.get(Integer
					.parseInt(getArguments().getString(
							PhunMasterConstants.ARG_ITEM_ID)) - 1);

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_item_detail, container,
				false);
		context = mRootView.getContext();
		SimpleDateFormat mDateFormat1 = new SimpleDateFormat(
				PhunMasterConstants.DATE_FORMAT_1);
		SimpleDateFormat mDateFormat2 = new SimpleDateFormat(
				PhunMasterConstants.DATE_FORMAT_2);

		// Show the dummy content as text in a TextView.
		if (mVenue != null) {
			LinearLayout mTextLayout = (LinearLayout) mRootView
					.findViewById(R.id.text_layout);
			if (!mVenue.getAddress().isEmpty() && mVenue.getAddress() != null) {
				TextView mAddress1 = new TextView(context);
				mAddress1.setPadding(0, 5, 0, 0);
				mAddress1.setText(mVenue.getAddress());
				mTextLayout.addView(mAddress1);
			}

			TextView mAddress2 = new TextView(context);
			mAddress2.setPadding(0, 0, 0, 5);
			if (!mVenue.getCity().isEmpty())
				mAddress2.setText(mVenue.getCity() + ", " + mVenue.getState()
						+ " " + mVenue.getZip());
			mTextLayout.addView(mAddress2);

			((TextView) mRootView.findViewById(R.id.item_detail))
					.setText(mVenue.getName());

			loadImages();

			List<ScheduleItem> mScheduleList = ItemListFragment.venueList.get(
					Integer.parseInt(getArguments().getString(
							PhunMasterConstants.ARG_ITEM_ID)) - 1)
					.getSchedule();
			Log.i("phun",
					"========mScheduleList length=====" + mScheduleList.size());
			for (ScheduleItem mSchedule : mScheduleList) {
				String mStartDate = printInLocalTime(mSchedule.getStart_date(),
						mDateFormat1);
				String mEndDate = printInLocalTime(mSchedule.getEnd_date(),
						mDateFormat2);
				TextView mDate = new TextView(context);
				mDate.setPadding(0, 5, 0, 5);
				mDate.setText("" + mStartDate + " to " + mEndDate);
				mTextLayout.addView(mDate);
			}
		}

		return mRootView;
	}

	private void loadImages() {
		if (!mVenue.getImageUrl().isEmpty() && mVenue.getImageUrl() != null) {
			mProgressBar = (ProgressBar) mRootView
					.findViewById(R.id.progressBar);
			mProgressBar.setVisibility(View.VISIBLE);
			Picasso.with(context).load(mVenue.getImageUrl()).into((ImageView) mRootView.findViewById(R.id.imageView1),
							new ImageLoadedCallback(mProgressBar) {
								@Override
								public void onSuccess() {
									if (this.progressBar != null) {
										this.progressBar
												.setVisibility(View.GONE);
									}
								}
							});
		} else {
			Picasso.with(context).load(R.drawable.no_image)
					.into((ImageView) mRootView.findViewById(R.id.imageView1));
		}
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
