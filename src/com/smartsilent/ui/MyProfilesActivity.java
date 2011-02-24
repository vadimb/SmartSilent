package com.smartsilent.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Handler;
import android.accounts.OnAccountsUpdateListener;
import android.content.Context;

import com.smartsilent.R;



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
public class MyProfilesActivity extends ListActivity
{
	
}
