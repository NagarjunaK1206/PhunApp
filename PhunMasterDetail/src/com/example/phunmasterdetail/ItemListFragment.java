package com.example.phunmasterdetail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.example.phunmasterdetail.util.CustomAdapter;
import com.example.phunmasterdetail.util.PhunMasterConstants;
import com.example.phunmasterdetail.util.Venue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ItemListFragment extends ListFragment {

	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	private Callbacks mCallbacks = sDummyCallbacks;

	private int mActivatedPosition = ListView.INVALID_POSITION;
	public static List<Venue> venueList;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// venueList = null;
		if (ItemListActivity.mTwoPane == true) {
			setHasOptionsMenu(true);
		}
	}

	public interface Callbacks {
		public void onItemSelected(String id);
	}

	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(String id) {
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (checkForConnectivity()) {
			AsyncTask<Void, Void, String> backgroundService = new ServiceRequestThread().execute();
		} else {
			showAlert();
		}
	}

	private void showAlert() {
		AlertDialog.Builder errorAlert = new AlertDialog.Builder(getActivity());
		errorAlert.setTitle("No Internet Connection");
		errorAlert
				.setMessage("There is no internet connectivity."
						+ "Please switch on your wifi or data connection and try again");
		errorAlert.setCancelable(true);
		errorAlert.setIcon(android.R.drawable.ic_dialog_alert);
		errorAlert.setNegativeButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						getActivity().finish();
					}
				});

		AlertDialog alert11 = errorAlert.create();
		alert11.show();
	}

	private boolean checkForConnectivity() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getBaseContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

		return (activeNetwork != null && activeNetwork
				.isConnectedOrConnecting());
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		mCallbacks.onItemSelected("" + (position + 1));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	public void setActivateOnItemClick(boolean activateOnItemClick) {

		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}

	public class ServiceRequestThread extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub

			CustomAdapter mAdapter = new CustomAdapter(getActivity()
					.getBaseContext(), venueList);
			setListAdapter(mAdapter);
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(Void... params) {

			DefaultHttpClient client = new DefaultHttpClient();

			HttpGet getRequest = new HttpGet(
					PhunMasterConstants.JSON_SERVICE_LINK);

			try {

				HttpResponse getResponse = client.execute(getRequest);
				final int statusCode = getResponse.getStatusLine()
						.getStatusCode();

				if (statusCode != HttpStatus.SC_OK) {
					Log.w(getClass().getSimpleName(), "Error " + statusCode
							+ " for URL "
							+ PhunMasterConstants.JSON_SERVICE_LINK);
					return null;
				}
				HttpEntity entity = getResponse.getEntity();
				InputStream content = entity.getContent();
				Gson gson = new Gson();
				try {
					Reader reader = new InputStreamReader(content);

					Type type = new TypeToken<List<Venue>>() {
					}.getType();
					venueList = gson.fromJson(reader, type);
				} catch (Exception ex) {
					Log.e("Phunware", "Failed to parse JSON due to: " + ex);
				}
				return null;

			} catch (IOException e) {
				getRequest.abort();
				Log.w(getClass().getSimpleName(), "Error for URL "
						+ PhunMasterConstants.JSON_SERVICE_LINK, e);
				e.printStackTrace();
				return null;
			}

		}
	}
}
