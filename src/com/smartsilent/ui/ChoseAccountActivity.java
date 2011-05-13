
package com.smartsilent.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.os.Handler;

import com.smartsilent.R;
import com.smartsilent.provider.ProfilesContract;

import java.io.IOException;

/**
 * <p>
 * The purpose of this activity is to let the user to chose a Google account
 * ,from Android Accounts repository, to be used with this application.
 * </p>
 * <p>
 * Once selected/checked(Google account)-he's persisted in our database and
 * activated as primary account.
 * </p>
 * <p>
 * Later on ,when user will chose to make their profiles Public will use the
 * primary Google account to login to our server.
 * </p>
 * 
 * @author Batin Vadim
 */
public class ChoseAccountActivity extends Activity implements AdapterView.OnItemClickListener,
        View.OnClickListener, OnAccountsUpdateListener {
    static final String TAG = "ChoseAccountActivity";
    static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);

   
    public static final String ACTION_CHOSE_ACCOUNT = "com.smartsilent.CHOSE_ACCOUNT";

    protected int curentAccountPos;
    protected String currentAccountName;

    protected ArrayAdapter<String> accountsAdapter;
    protected ListView mAccountsListView;
    protected AccountManager mAccountManager;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountManager = AccountManager.get(this);

       
        setContentView(R.layout.chose_account_activity);
        mAccountsListView = (ListView) findViewById(R.id.accounts_list);
        mAccountsListView.setOnItemClickListener(this);
        findViewById(R.id.add_account_button).setOnClickListener(
                this);

    }

    
    protected final void onStart() {
        super.onStart();
        
        try {
            mAccountManager
                    .addOnAccountsUpdatedListener(this,
                            new Handler(getMainLooper()), true /* updateImmediately */);

        } catch (IllegalArgumentException ex) {
            if (LOGV) {
                Log.e(TAG, "The OnAccountsUpdateListener listener is null.",
                        ex);
            }
            setResult(RESULT_CANCELED);
            finish();
        } catch (IllegalStateException ex) {
            Log.w(TAG, "The OnAccountsUpdateListener listener was already added.", ex);
            // pass
        }
    }

    @Override
    protected final void onStop() {
        super.onStop();
        try {
            mAccountManager
                    .removeOnAccountsUpdatedListener(this);
        } catch (IllegalArgumentException ex) {
            if (LOGV) {
                Log.e(TAG, "The OnAccountsUpdateListener listener is null.", ex);
                // pass
            }
        } catch (IllegalStateException ex) {
            if (LOGV) {
                Log.w(TAG,
                        "The OnAccountsUpdateListener listener was not already added.",
                        ex);
                // pass
            }
        }
    }

    /**
     * <p>
     * This method is called when an list item is touched/selected by the user.
     * <p>
     * The selected account is persisted to the SQLite database and to
     * preferences . The persisted account is used to sign in the application.
     * 
     * <p>
     * If the account was successfully saved the <code>RESULT_OK</code> flag is
     * set and an Intent with the corresponding account is sent to the caller's onActivityResult() method. 
     * <p>
     * In the case of errors  the <code>RESULT_CANCELED</code> flag is
     * set.
     * 
     * <p>
     * On exit this activity is destroyed.
     * 
     */
    public void onItemClick(final AdapterView<?> parent, final View view, final int position,
            final long id) {

        ContentValues values = new ContentValues();
        values.put(
                ProfilesContract.Users.NAME,
                ((com.smartsilent.ui.AccountArrayAdapter.ViewHolder) view
                        .getTag()).name.getText().toString());
        values.put(ProfilesContract.Users.TYPE, "com.google");
        try {
            ContentResolver  c=this.getContentResolver();
            
            c.insert(ProfilesContract.Users.CONTENT_URI,
                    values);
            SharedPreferences settings = this.getSharedPreferences(ProfilesContract.PREFS_NAME,
                    ChoseAccountActivity.MODE_PRIVATE);
            SharedPreferences.Editor mEditor = settings.edit();
            mEditor.putString(ProfilesContract.Users.NAME,
                    values.getAsString(ProfilesContract.Users.NAME));
            mEditor.commit();
            this.setResult(
                    ChoseAccountActivity.RESULT_OK,
                    new Intent().putExtra(ProfilesContract.Users.NAME,
                            values.getAsString(ProfilesContract.Users.NAME)));
        } catch (SQLException ex) {
            if (ChoseAccountActivity.LOGV) {
                Log.e(ChoseAccountActivity.TAG, ex.getMessage(), ex);
            }
            this.setResult(ChoseAccountActivity.RESULT_CANCELED);
        }

        catch (IllegalArgumentException ex) {
            if (ChoseAccountActivity.LOGV) {
                Log.e(ChoseAccountActivity.TAG, ex.getMessage(), ex);
            }
            this.setResult(ChoseAccountActivity.RESULT_CANCELED);
        }
        

        this.finish();
    }

    class AddGoogleAccountManagerCallback implements
	    AccountManagerCallback<Bundle> {

	public void run(AccountManagerFuture<Bundle> mAccountManagerFuture) {

	    Intent mResultIntent;

	    try {
		Bundle mBundle = mAccountManagerFuture.getResult();
		if (mBundle.containsKey(AccountManager.KEY_INTENT)) {
		    ChoseAccountActivity.this.startActivity((Intent) mBundle
			    .get(AccountManager.KEY_INTENT));
		}
		
	    } catch (AuthenticatorException ex) {
		if (LOGV) {
		    Log.e(ChoseAccountActivity.TAG, ex.getMessage(), ex);
		}
		mResultIntent = new Intent().putExtra("Msg",
			"The Google authenticator failed to respond.");
		mResultIntent.putExtra("AuthenticatorException", ex);
		ChoseAccountActivity.this.setResult(
			ChoseAccountActivity.RESULT_CANCELED, mResultIntent);
		ChoseAccountActivity.this.finish();

	    } catch (OperationCanceledException ex) {
		if (ChoseAccountActivity.LOGV) {
		    Log.e(ChoseAccountActivity.TAG, ex.getMessage(), ex);
		}
		mResultIntent = new Intent().putExtra("Msg",
			"The operation was canceled.");
		mResultIntent.putExtra("OperationCanceledException", ex);
		ChoseAccountActivity.this.setResult(
			ChoseAccountActivity.RESULT_CANCELED, mResultIntent);
		//ChoseAccountActivity.this.finish();
	    } catch (IOException ex) {

		if (LOGV) {
		    Log.e(ChoseAccountActivity.TAG, ex.getMessage(), ex);
		}
		mResultIntent = new Intent()
			.putExtra("Msg",
				"The Google authenticator experienced a network problem.");
		mResultIntent.putExtra("IOException", ex);
		ChoseAccountActivity.this.setResult(
			ChoseAccountActivity.RESULT_CANCELED, mResultIntent);
		ChoseAccountActivity.this.finish();
	    } catch (ActivityNotFoundException ex) {

		if (LOGV) {
		    Log.e(ChoseAccountActivity.TAG, ex.getMessage(), ex);
		}
		mResultIntent = new Intent().putExtra("Msg",
			"The account creation process failed.");
		mResultIntent.putExtra("ActivityNotFoundException", ex);
		ChoseAccountActivity.this.setResult(
			ChoseAccountActivity.RESULT_CANCELED, mResultIntent);
		ChoseAccountActivity.this.finish();
	    }

	}

    }
    
   
    
    /**
     * <p>
     * This method is called to handle the event of account adding .
     * <p>
     * If something goes wrong -the RESULT_CANCELED flag is set, an Intent with
     * a message is sent to the caller's onActivityResult() method and the
     * activity is destroyed.
     */
    public void onClick(final View v) {
	this.mAccountManager.addAccount("com.google", null, null,
		null /* options */, this,
		new AddGoogleAccountManagerCallback(), null /* handler */);

    }

    public void onAccountsUpdated(final Account[] accounts) {

        this.mAccountsListView.setAdapter(new AccountArrayAdapter(this, accounts));
    }

}
