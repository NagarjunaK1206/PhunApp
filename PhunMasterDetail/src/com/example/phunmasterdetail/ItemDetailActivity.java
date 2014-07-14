package com.example.phunmasterdetail;

import com.example.phunmasterdetail.util.PhunMasterConstants;
import com.example.phunmasterdetail.util.Venue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

public class ItemDetailActivity extends ActionBarActivity {
	private ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {

			Bundle mArguments = new Bundle();
			mArguments.putString(PhunMasterConstants.ARG_ITEM_ID, getIntent()
					.getStringExtra(PhunMasterConstants.ARG_ITEM_ID));
			ItemDetailFragment mFragment = new ItemDetailFragment();
			mFragment.setArguments(mArguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, mFragment).commit();
		}
	}

	@SuppressLint("NewApi") 
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu, menu);
		MenuItem mMenuItem = menu.findItem(R.id.menu_item_share);
		mMenuItem.setEnabled(true);
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
			mMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		mShareActionProvider = new ShareActionProvider(this);
		MenuItemCompat.setActionProvider(mMenuItem, mShareActionProvider);
		mShareActionProvider
				.setShareHistoryFileName("custom_share_history.xml");
		Venue mVenue = ItemListFragment.venueList.get(Integer
				.parseInt(getIntent().getStringExtra(
						PhunMasterConstants.ARG_ITEM_ID)) - 1);
		Intent mSendIntent = new Intent();
		mSendIntent.setAction(Intent.ACTION_SEND);
		mSendIntent.putExtra(Intent.EXTRA_TEXT,
				"Venue Name: " + mVenue.getName() + " Venue address:  "
						+ mVenue.getAddress() + "," + mVenue.getCity() + ","
						+ mVenue.getState() + " " + mVenue.getZip());
		mSendIntent.setType("text/plain");
		doShare(mSendIntent);
		return true;
	}

	public void doShare(Intent shareIntent) {
		mShareActionProvider.setShareIntent(shareIntent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
