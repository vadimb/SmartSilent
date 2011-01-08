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

		db.execSQL("CREATE TABLE " + Tables.PROFILES_TAGS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ ProfilseTags.PROFILE_ID + " TEXT NOT NULL"+ References.PROFILE_ID +"," 
				+ ProfilseTags.TAG_ID + " TEXT NOT NULL" + References.TAG_ID + ","	                
				+ "UNIQUE (" + ProfilseTags.PROFILE_ID + "," + ProfilseTags.TAG_ID + ") ON CONFLICT REPLACE)");

		db.execSQL("CREATE TABLE " + Tables.TAGS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Tags.TAG_ID + " TEXT NOT NULL ," 
				+ Tags.TAG + " TEXT NOT NULL ,"
				+ "UNIQUE ("+Tags.TAG_ID + ") ON CONFLICT REPLACE)");

		db.execSQL("CREATE TABLE " + Tables.BOOKMARKS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Bookmarks.BOOKMARK_ID + " TEXT NOT NULL ,"
				+ Bookmarks.DATE_TIME + " TEXT NOT NULL ,"
				+ Bookmarks.PROFILE_ID + " TEXT NOT NULL"+ References.PROFILE_ID +","
				+ "UNIQUE ("+Bookmarks.BOOKMARK_ID + ") ON CONFLICT REPLACE)");


		db.execSQL("CREATE TABLE " + Tables.EVENTS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Events.EVENT_ID + " TEXT NOT NULL ,"
				+ Events.DATE_TIME +  " TEXT NOT NULL ,"
				+ Events.ACTION_ID + " TEXT NOT NULL"+ References.ACTIONS_ID +","
				+ Events.LOCATION_ID + " TEXT NOT NULL"+ References.LOCATION_ID +","
				+ "UNIQUE ("+Events.EVENT_ID + ") ON CONFLICT REPLACE)");
		
		db.execSQL("CREATE TABLE " + Tables.LOCATIONS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Locations.LOCATION_ID +" TEXT NOT NULL ,"
				+ Locations.LON + "INTEGER NOT NULL ,"
				+ Locations.LAT + "INTEGER NOT NULL ,"
				+ "UNIQUE ("+Locations.LOCATION_ID + ") ON CONFLICT REPLACE)");			
				
				
		db.execSQL("CREATE TABLE " + Tables.ACTIONS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Actions.ACTION_ID + " TEXT NOT NULL ,"
				+ Actions.GPS_ON_OR_OFF +  " TEXT NOT NULL ,"
				+ Actions.WiFi_ON_OR_OFF + " TEXT NOT NULL ,"
				+ Actions.Alert_ID + " TEXT NOT NULL"+ References.ALERT_ID +","
				+ "UNIQUE ("+Actions.ACTION_ID + ") ON CONFLICT REPLACE)");
		
		db.execSQL("CREATE TABLE " + Tables.ALERTS + " ("
				+ BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Alerts.Alert_ID + " TEXT NOT NULL ,"
				+ Alerts.TITLE + " TEXT NOT NULL ,"
				+ Alerts.VOLUME + "INTEGER NOT NULL ,"
				+ Alerts.VIBRATOR_ON_OR_OFF + " TEXT NOT NULL ,"
				+ "UNIQUE ("+Alerts.Alert_ID + ") ON CONFLICT REPLACE)");	
				   
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

        // NOTE: This switch statement is designed to handle cascading database
        // updates, starting at the current version and falling through to all
        // future upgrade cases. Only use "break;" when you want to drop and
        // recreate the entire database.
        int version = oldVersion;
        /*switch (version) {
            case VER_LAUNCH:
                // Version 19 added column for session hashtags.
                db.execSQL("ALTER TABLE " + Tables.SESSIONS + " ADD COLUMN "
                        + SessionsColumns.HASHTAG + " TEXT");
                version = VER_SESSION_HASHTAG;
        }*/

        Log.d(TAG, "after upgrade logic, at version " + version);
        if (version != DATABASE_VERSION) {
            Log.w(TAG, "Destroying old data during upgrade");

            db.execSQL("DROP TABLE IF EXISTS " + Tables.ACTIONS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.ALERTS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.BOOKMARKS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.EVENTS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.LOCATIONS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.PROFILES);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.PROFILES_TAGS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.TAGS);          
           
            onCreate(db);
        }

		
	}
}
