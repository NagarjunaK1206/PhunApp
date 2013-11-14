package com.example.phunmasterdetail;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.phunmasterdetail.util.PhunMasterConstants;

import android.content.Intent;
import android.os.Bundle;

public class ItemListActivity extends SherlockFragmentActivity implements
		ItemListFragment.Callbacks {

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
}
