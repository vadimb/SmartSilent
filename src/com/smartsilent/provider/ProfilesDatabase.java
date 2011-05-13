package com.smartsilent.provider;

import com.smartsilent.provider.ProfilesContract.ProfileColumns;
import com.smartsilent.provider.ProfilesContract.UsersColumns;

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
    static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);

    private static final int DATABASE_VERSION = 6;

    interface Tables {
	String USERS = "users";
	String PROFILES = "profiles";
	String EVENTS = "events";
	String LOCATIONS = "locations";
	String ACTIONS = "actions";
	String ALERTS = "alerts";

    }

    /** {@code REFERENCES} clauses. */
    private interface References {

	String USER_ID = "REFERENCES " + Tables.USERS + "(" + BaseColumns._ID
		+ ")";
	String PROFILE_ID = "REFERENCES " + Tables.PROFILES + "("
		+ BaseColumns._ID + ")";
	String EVENT_ID = "REFERENCES " + Tables.EVENTS + "(" + BaseColumns._ID
		+ ")";
	String ACTIONS_ID = "REFERENCES " + Tables.ACTIONS + "("
		+ BaseColumns._ID + ")";
	String LOCATION_ID = "REFERENCES " + Tables.LOCATIONS + "("
		+ BaseColumns._ID + ")";
	String ALERT_ID = "REFERENCES " + Tables.ALERTS + "(" + BaseColumns._ID
		+ ")";

    }

    /**
     * Creates a new SQLite database.
     * 
     * @param context
     *            The application context.
     */
    public ProfilesDatabase(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

	db.execSQL("CREATE TABLE " + Tables.USERS + " (" + BaseColumns._ID
		+ " INTEGER PRIMARY KEY AUTOINCREMENT," + UsersColumns.NAME
		+ " TEXT NOT NULL," + UsersColumns.TYPE + " TEXT NOT NULL ,"
		+ "CONSTRAINT dublicateUser UNIQUE (" + UsersColumns.NAME
		+ ") ON CONFLICT IGNORE)");

	db.execSQL("CREATE TABLE " + Tables.PROFILES + " (" + BaseColumns._ID
		+ " INTEGER PRIMARY KEY AUTOINCREMENT," + ProfileColumns.TITLE
		+ " TEXT NOT NULL," + ProfileColumns.DESC + " TEXT NOT NULL,"
		+ ProfileColumns.USER_ID + " INTEGER NOT NULL "
		+ References.USER_ID + ","
		/*
		 * + ProfileColumns.OFF_OR_ON + " TEXT NOT NULL," +
		 * ProfileColumns.PUBLIC_OR_PRIVATE + " TEXT NOT NULL,"
		 */
		// + ProfileColumns.USER_NR+ " INTEGER NOT NULL,"
		// + ProfileColumns.EVENT_ID
		// + " TEXT NOT NULL " + References.EVENT_ID + ","
		+ "UNIQUE (" + BaseColumns._ID + ") ON CONFLICT REPLACE)");
	
	db.execSQL("INSERT INTO " + Tables.USERS + " (" + UsersColumns.TYPE
		+ "," + UsersColumns.NAME + ")" + " VALUES ("
		+ "\"com.google\"" + "," + "\"me@g.com \"" + ");");
	
	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+ "\"Short\"" + "," + "\"shorrrrrrrtt\"" + ");");
	
	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+ "\"Short\"" + "," + "\"Loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong \"" + ");");
	
	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+"\"Loooooooooooooooooooooooooooooooooooooooooooooooonnnnnnnnnnnnnnnnnng\"" + "," + "\"shorrrrrrrtt \"" + ");");

	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+"\"Loooooooooooooooooooooooongggggggggggg\"" + "," + "\"Looooooooooooooooooooooooooooooooooooooooooooooooooooooooooong \"" + ");");
	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+"\"Loooooooooooooooooooooooongggggggggggg\"" + "," + "\"Looooooooooooooooooooooooooooooooooooooooooooooooooooooooooong \"" + ");");
	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+"\"Loooooooooooooooooooooooongggggggggggg\"" + "," + "\"Looooooooooooooooooooooooooooooooooooooooooooooooooooooooooong \"" + ");");
	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+"\"Loooooooooooooooooooooooongggggggggggg\"" + "," + "\"Looooooooooooooooooooooooooooooooooooooooooooooooooooooooooong \"" + ");");
	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+"\"Loooooooooooooooooooooooongggggggggggg\"" + "," + "\"Looooooooooooooooooooooooooooooooooooooooooooooooooooooooooong \"" + ");");
	db.execSQL("INSERT INTO " + Tables.PROFILES + " (" + ProfileColumns.USER_ID
		+ "," + ProfileColumns.TITLE+","+ProfileColumns.DESC+ ")" + " VALUES ("
		+"0"+","+"\"Loooooooooooooooooooooooongggggggggggg\"" + "," + "\"Looooooooooooooooooooooooooooooooooooooooooooooooooooooooooong \"" + ");");
	/*
	 * db.execSQL("CREATE TABLE " + Tables.EVENTS + " (" + BaseColumns._ID +
	 * " INTEGER PRIMARY KEY AUTOINCREMENT," + Events.EVENT_ID +
	 * " TEXT NOT NULL ," + Events.DATE_TIME + " TEXT NOT NULL ," +
	 * Events.ACTION_ID + " TEXT NOT NULL "+ References.ACTIONS_ID +"," +
	 * Events.LOCATION_ID + " TEXT NOT NULL "+ References.LOCATION_ID +"," +
	 * "UNIQUE ("+Events.EVENT_ID + ") ON CONFLICT REPLACE)");
	 * 
	 * db.execSQL("CREATE TABLE " + Tables.LOCATIONS + " (" +
	 * BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
	 * Locations.LOCATION_ID +" TEXT NOT NULL ," + Locations.LON +
	 * "INTEGER NOT NULL ," + Locations.LAT + "INTEGER NOT NULL ," +
	 * "UNIQUE ("+Locations.LOCATION_ID + ") ON CONFLICT REPLACE)");
	 * 
	 * 
	 * db.execSQL("CREATE TABLE " + Tables.ACTIONS + " (" + BaseColumns._ID
	 * + " INTEGER PRIMARY KEY AUTOINCREMENT," + Actions.ACTION_ID +
	 * " TEXT NOT NULL ," + Actions.GPS_ON_OR_OFF + " TEXT NOT NULL ," +
	 * Actions.WiFi_ON_OR_OFF + " TEXT NOT NULL ," + Actions.Alert_ID +
	 * " TEXT NOT NULL "+ References.ALERT_ID +"," +
	 * "UNIQUE ("+Actions.ACTION_ID + ") ON CONFLICT REPLACE)");
	 * 
	 * db.execSQL("CREATE TABLE " + Tables.ALERTS + " (" + BaseColumns._ID +
	 * " INTEGER PRIMARY KEY AUTOINCREMENT," + Alerts.Alert_ID +
	 * " TEXT NOT NULL ," + Alerts.TITLE + " TEXT NOT NULL ," +
	 * Alerts.VOLUME + "INTEGER NOT NULL ," + Alerts.VIBRATOR_ON_OR_OFF +
	 * " TEXT NOT NULL ," + "UNIQUE ("+Alerts.Alert_ID +
	 * ") ON CONFLICT REPLACE)");
	 */

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	if (LOGV)
	    Log.d(TAG, "onUpgrade() from " + oldVersion + " to " + newVersion);

	int version = oldVersion;

	if (version != DATABASE_VERSION) {
	    if (LOGV)
		Log.w(TAG, "Destroying old data during upgrade");

	    db.execSQL("DROP TABLE IF EXISTS " + Tables.ACTIONS);
	    db.execSQL("DROP TABLE IF EXISTS " + Tables.USERS);
	    db.execSQL("DROP TABLE IF EXISTS " + Tables.ALERTS);
	    db.execSQL("DROP TABLE IF EXISTS " + Tables.EVENTS);
	    db.execSQL("DROP TABLE IF EXISTS " + Tables.LOCATIONS);
	    db.execSQL("DROP TABLE IF EXISTS " + Tables.PROFILES);

	    onCreate(db);
	}

    }

}