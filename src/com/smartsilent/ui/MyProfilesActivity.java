package com.smartsilent.ui;

import com.smartsilent.R;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.smartsilent.provider.ProfilesContract;
import com.smartsilent.provider.ProfilesContract.Profiles;
import com.smartsilent.provider.ProfilesContract.Users;

/**
 * This is application's main activity that is used in Android to launch an
 * app. This activity lists all users profiles saved in the local SQLite database.
 * 
 * @author Batin Vadim
 */
public class MyProfilesActivity extends ListActivity implements OnClickListener {
    private static final String TAG = "MyProfilesActivity";
    private static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);
       
    static final int REQUEST_CODE_CHOSE_ACCOUNT=100;
    
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_profiles_layout);
	 
	if (!hasAccount())
	    choseAccount();
	    
             updateTitlebar_Account();
	
	Cursor mCursor = this.getContentResolver().query(Profiles.CONTENT_URI,
		null, null, null, null);
	startManagingCursor(mCursor);

	Button addProfile=(Button) findViewById(R.id.add_account_button);
	addProfile.setText("Add profile");
	// Now create a new list adapter bound to the cursor.
	// SimpleListAdapter is designed for binding to a Cursor.
	ListAdapter adapter = new SimpleCursorAdapter(

	this, // Context.
	      // Specify the row template to use (here, two columns bound to the
	      // two retrieved cursor
	R.layout.my_profiles_list_item_layout, mCursor, // Pass in the
							      // cursor to bind
							      // to.
		new String[] { Profiles.TITLE ,Profiles.DESC}, // Array of cursor columns to
						 // bind to.
		new int[] { R.id.profile_title,R.id.profile_desc}); // Parallel array of which
						   // template objects to bind
						   // to those columns.

	// Bind to our new adapter.
	setListAdapter(adapter);
	
	
    }
    
    void updateTitlebar_Account() {
	TextView googleAccount=(TextView) findViewById(R.id.acount);	
	SharedPreferences settings = this.getSharedPreferences(ProfilesContract.PREFS_NAME,
                ChoseAccountActivity.MODE_PRIVATE);	
	googleAccount.setText(settings.getString(ProfilesContract.Users.NAME," "));
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	switch (requestCode) {
	case REQUEST_CODE_CHOSE_ACCOUNT: {
	    if (resultCode != RESULT_OK) {		
		if (LOGV)
		    Log.e(TAG, "Failed to chose an account."+data.getStringExtra("Msg"));		
		break;
	    }
	    updateTitlebar_Account();
	    break;
	  
	}
	default:
	    break;

	}
    }
       
    protected boolean hasAccount() {
	// Check the global Users.NAME flag variable
	
	SharedPreferences settings = getSharedPreferences(
		ProfilesContract.PREFS_NAME, MODE_PRIVATE);
	String mAdminUser;
	try {
	    mAdminUser = settings.getString(Users.NAME, "");
	} catch (ClassCastException e) {
	    if (LOGV)
		Log.e(TAG, "There is a preference with the same name("
			+ Users.NAME + ") that is not a String.");
	    return false;
	}

	if (mAdminUser.equals(""))
	    return false;

	return true;

    }    
  
    
   void choseAccount() {

	Intent mIntent = new Intent(ChoseAccountActivity.ACTION_CHOSE_ACCOUNT);
	try {
	    startActivityForResult(mIntent, REQUEST_CODE_CHOSE_ACCOUNT);
	} catch (ActivityNotFoundException ex) {
	    if (LOGV)
		Log.e(TAG, "WelcomeActivity not found.", ex);	    
	    finish();	    
	}
    }

    public void onClick(View v) {
	choseAccount();
    }
    
}
