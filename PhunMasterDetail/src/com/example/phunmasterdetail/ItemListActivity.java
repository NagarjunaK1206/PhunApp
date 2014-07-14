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

public class ItemListActivity extends ActionBarActivity implements
		ItemListFragment.Callbacks {

	private ShareActionProvider mShareActionProvider;
	private String mItemSlected = null;
	static boolean mTwoPane = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		if (findViewById(R.id.item_detail_container) != null) {
			mTwoPane = true;
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setHomeButtonEnabled(true);
			((ItemListFragment) getSupportFragmentManager().findFragmentById(
					R.id.item_list)).setActivateOnItemClick(true);
		}
	}

	@Override
	public void onItemSelected(String id) {
		mItemSlected = id;
		ItemDetailFragment fragment = new ItemDetailFragment();
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(PhunMasterConstants.ARG_ITEM_ID, id);
			fragment.setArguments(arguments);
			fragment.setHasOptionsMenu(true);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();

		} else {
			fragment.setHasOptionsMenu(false);
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(PhunMasterConstants.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		mItemSlected = null;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mTwoPane) {
			getMenuInflater().inflate(R.menu.menu, menu);
			MenuItem mMenuItem = menu.findItem(R.id.menu_item_share);
			if (mItemSlected != null) {
				mMenuItem.setEnabled(true);
				mMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				mShareActionProvider = new ShareActionProvider(this);
				MenuItemCompat.setActionProvider(mMenuItem,
						mShareActionProvider);
				mShareActionProvider
						.setShareHistoryFileName("custom_share_history.xml");
				Venue mVenue = ItemListFragment.venueList.get(Integer
						.parseInt(mItemSlected) - 1);
				Intent mSendIntent = new Intent();
				mSendIntent.setAction(Intent.ACTION_SEND);
				mSendIntent.putExtra(
						Intent.EXTRA_TEXT,
						"Venue Name: " + mVenue.getName() + " Venue address:  "
								+ mVenue.getAddress() + "," + mVenue.getCity()
								+ "," + mVenue.getState() + " "
								+ mVenue.getZip());
				mSendIntent.setType("text/plain");
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
				doShare(mSendIntent);
			} else {
				mMenuItem.setEnabled(false);
				mMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
				mShareActionProvider = new ShareActionProvider(this);
				MenuItemCompat.setActionProvider(mMenuItem,
						mShareActionProvider);
				getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			}
		}
		return true;
	}

	public void doShare(Intent shareIntent) {
		if (mShareActionProvider != null)
			mShareActionProvider.setShareIntent(shareIntent);
	}

}
