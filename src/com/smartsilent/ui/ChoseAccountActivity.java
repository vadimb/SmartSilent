
package com.smartsilent.ui;

import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Handler;
import android.accounts.OnAccountsUpdateListener;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;

import com.smartsilent.R;
import com.smartsilent.provider.ProfilesContract;



/**
 * 
 * <p>
 * The purpose of this activity is to let the user to chose a Google account
 * ,from Android Accounts repository, to be used with this application.
 * </p>
 * 
 * <p>
 * Once selected/checked(Google account)-he's persisted in our database and
 * activated as primary account.
 * </p>
 * 
 * <p>
 * Later on ,when user will chose to make their profiles Public will use the
 * primary Google account to login to our server.
 * </p>
 */
public class ChoseAccountActivity extends Activity
{

	public static final String ACTION_CHOSE_ACCOUNT="com.smartsilent.CHOSE_ACCOUNT";	
	private static final String TAG = "ChoseAccountActivity";
	private static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);
	protected int curentAccountPos;
	protected String currentAccountName;
	
	protected ArrayAdapter<String> accountsAdapter;  
	protected ListView mAccountsListView;
	protected AccountManager mAccountManager;	
	protected OnGoogleAccountsUpdateListener mGoogleAccountListner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mAccountManager = AccountManager.get(this);		
		mGoogleAccountListner=new OnGoogleAccountsUpdateListener();
		
		setContentView(R.layout.chose_account_activity);		
		mAccountsListView = (ListView) findViewById(R.id.accounts_list);		
		mAccountsListView.setOnItemClickListener(new OnAccountItemClickListener());				
		findViewById(R.id.add_account_button).setOnClickListener(
				new OnAddAccountClickListener());		

	}	
	@Override
	protected void onStart() {
		super.onStart();
		try {
			mAccountManager
					.addOnAccountsUpdatedListener(mGoogleAccountListner,
							new Handler(getMainLooper()), true /* updateImmediately */);
		} catch (IllegalArgumentException ex) {
			if(LOGV)
				Log.e(TAG, "The OnAccountsUpdateListener listener is null.",ex);
			setResult(RESULT_CANCELED);
			finish();
		}
		catch(IllegalStateException ex){
			Log.w(TAG, "The OnAccountsUpdateListener listener was already added.", ex);
			//pass
		}
	}
	@Override
	protected void onStop() {
		super.onStop();
		try {
			mAccountManager
			.removeOnAccountsUpdatedListener(mGoogleAccountListner);
		} catch (IllegalArgumentException ex) {
			if(LOGV)
				Log.e(TAG, "The OnAccountsUpdateListener listener is null.", ex);
			//pass
		} catch (IllegalStateException ex) {
			if(LOGV)
				Log.w(TAG,"The OnAccountsUpdateListener listener was already added.",ex);
			//pass
		}
	}
	
	public class AccountArrayAdapter extends ArrayAdapter<Account> {
		protected LayoutInflater mInflater;

		public AccountArrayAdapter(Context context, Account[] accounts) {
			super(context, R.layout.account_list_item_layout, accounts);
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

	private	class ViewHolder {
			TextView name;			
			Account account;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// A ViewHolder keeps references to children views to avoid
			// unneccessary calls
			// to findViewById() on each row.
			ViewHolder holder;

			// When convertView is not null, we can reuse it directly, there is
			// no need
			// to reinflate it. We only inflate a new View when the convertView
			// supplied
			// by ListView is null.
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.account_list_item_layout,
						null);

				// Creates a ViewHolder and store references to the two children
				// views
				// we want to bind data to.
				holder = new ViewHolder();
				holder.name = (TextView) convertView
						.findViewById(R.id.account_name);			

				convertView.setTag(holder);
			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}

			final Account account = getItem(position);
			holder.account = account;
			
			// Set text field
			holder.name.setText(account.name);
			return convertView;
		}
	}	
	
	class OnGoogleAccountsUpdateListener implements OnAccountsUpdateListener
	{

		public void onAccountsUpdated(Account[] accounts) {			
			mAccountsListView.setAdapter(new AccountArrayAdapter(ChoseAccountActivity.this, accounts));
		}
		
	}

	class OnAccountItemClickListener implements AdapterView.OnItemClickListener {	

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			ContentValues values = new ContentValues();
			values.put(
					ProfilesContract.Users.NAME,
					((com.smartsilent.ui.ChoseAccountActivity.AccountArrayAdapter.ViewHolder) view
							.getTag()).name.getText().toString());
			values.put(ProfilesContract.Users.TYPE, "com.google");
			try {
				getContentResolver().insert(ProfilesContract.Users.CONTENT_URI,
						values);
				setResult(RESULT_OK);
			} catch (SQLException ex) {
				if (LOGV)
					Log.e(TAG, ex.getMessage(), ex);
				setResult(RESULT_CANCELED);
			}

			catch (IllegalArgumentException ex) {
				if (LOGV)
					Log.e(TAG, ex.getMessage(), ex);
				setResult(RESULT_CANCELED);
			}

			finish();
			}
	}

	class OnAddAccountClickListener implements View.OnClickListener {
		public void onClick(View v) {
			AccountManagerFuture<Bundle> mAccountManagerFuture = mAccountManager
					.addAccount("com.google", null, null, null /* options */,
							ChoseAccountActivity.this, null, null /* handler */);
			Intent mResultIntent;
			
			try {
				Bundle mBundle = mAccountManagerFuture.getResult();
				if (mBundle.containsKey(AccountManager.KEY_INTENT))
					startActivity((Intent) mBundle
							.get(AccountManager.KEY_INTENT));
			} catch (AuthenticatorException ex) {
				if (LOGV)
					Log.e(TAG, ex.getMessage(), ex);
				mResultIntent = new Intent().putExtra("Msg",
						"The Google authenticator failed to respond.");
				mResultIntent.putExtra("AuthenticatorException", ex);
				setResult(RESULT_CANCELED, mResultIntent);
				finish();

			} catch (OperationCanceledException ex) {
				if (LOGV)
					Log.e(TAG, ex.getMessage(), ex);
				mResultIntent = new Intent().putExtra("Msg",
						"The operation was canceled.");
				mResultIntent.putExtra("OperationCanceledException", ex);
				setResult(RESULT_CANCELED, mResultIntent);
				finish();
			} catch (IOException ex) {

				if (LOGV)
					Log.e(TAG, ex.getMessage(), ex);
				mResultIntent = new Intent()
						.putExtra("Msg",
								"The Google authenticator experienced a network problem.");
				mResultIntent.putExtra("IOException", ex);
				setResult(RESULT_CANCELED, mResultIntent);
				finish();
			}
			catch (ActivityNotFoundException ex) {

				if (LOGV)
					Log.e(TAG, ex.getMessage(), ex);
				mResultIntent = new Intent()
						.putExtra("Msg",
								"The account creation process failed.");
				mResultIntent.putExtra("ActivityNotFoundException", ex);
				setResult(RESULT_CANCELED, mResultIntent);
				finish();
			}
			
		}
	}

}
