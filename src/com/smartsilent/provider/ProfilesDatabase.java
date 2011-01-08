package com.smartsilent.provider;

import com.smartsilent.provider.ProfilesContract.Actions;
import com.smartsilent.provider.ProfilesContract.Alerts;
import com.smartsilent.provider.ProfilesContract.Bookmarks;
import com.smartsilent.provider.ProfilesContract.Events;
import com.smartsilent.provider.ProfilesContract.Locations;
import com.smartsilent.provider.ProfilesContract.ProfileColumns;
import com.smartsilent.provider.ProfilesContract.Profiles;
import com.smartsilent.provider.ProfilesContract.Tags;

import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Helper for managing {@link SQLiteDatabase} that stores data for
 * {@link ScheduleProvider}.
 */
public class ProfilesDatabase extends SQLiteOpenHelper {
	private static final String TAG = "ProfilesDatabase";

	private static final String DATABASE_NAME = "profiles.db";

	private static final int DATABASE_VERSION = 1;

	interface Tables {
		String PROFILES = "profiles";
		String TAGS = "tags";
		String PROFILES_TAGS = "profiles_tags";
		String BOOKMARKS = "bookmarks";
		String EVENTS = "events";
		String LOCATIONS = "locations";
		String ACTIONS = "actions";
		String ALERTS = "alerts";

	}

	public interface ProfilseTags {
		String PROFILE_ID = "profile_id";
		String TAG_ID = "tag_id";
	}

	/** {@code REFERENCES} clauses. */
	private interface References {
		String PROFILE_ID = "REFERENCES " + Tables.PROFILES + "("
				+ Profiles.PROFILE_ID + ")";
		String TAG_ID = "REFERENCES " + Tables.TAGS + "(" + Tags.TAG_ID + ")";
		String BOOKMARK_ID = "REFERENCES " + Tables.BOOKMARKS + "("
				+ Tags.TAG_ID + ")";
		String EVENT_ID = "REFERENCES " + Tables.EVENTS + "(" + Events.EVENT_ID
				+ ")";
		String ACTIONS_ID = "REFERENCES " + Tables.ACTIONS + "("
				+ Actions.ACTION_ID + ")";
		String LOCATION_ID = "REFERENCES " + Tables.LOCATIONS + "("
				+ Locations.LOCATION_ID + ")";
		String ALERT_ID = "REFERENCES " + Tables.ALERTS + "(" + Alerts.Alert_ID
				+ ")";

	}
	
	public ProfilesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + Tables.PROFILES + " (" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ ProfileColumns.PROFILE_ID + " TEXT NOT NULL,"
				+ ProfileColumns.DESC + " TEXT ," + ProfileColumns.OFF_OR_ON
				+ " TEXT NOT NULL," + ProfileColumns.PUBLIC_OR_PRIVATE
				+ " TEXT NOT NULL," + ProfileColumns.USER_ID
				+ " TEXT NOT NULL," + ProfileColumns.USER_NAME
				+ " TEXT NOT NULL," + ProfileColumns.USER_NR
				+ " INTEGER NOT NULL," + ProfileColumns.EVENT_ID
				+ " TEXT NOT NULL" + References.EVENT_ID + "," + "UNIQUE ("
				+ ProfileColumns.PROFILE_ID + ") ON CONFLICT REPLACE)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
