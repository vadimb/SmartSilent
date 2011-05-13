package com.smartsilent.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class for interacting with {@link ProfilesProvider}.
 */

public class ProfilesContract {

    // SmartSilent's SharedPreferences file name.
    public static final String PREFS_NAME = "SmartSilentPREFS";

    // The columns we'll include in our DB.
    interface UsersColumns {
	String NAME = "name";
	String TYPE = "type";
    }

    interface ProfileColumns {
	String PROFILE_ID = "profile_id";
	String OFF_OR_ON = "off_or_on";
	String TITLE = "title";
	String DESC = "desc";
	String PUBLIC_OR_PRIVATE = "public_or_private";
	String USER_ID = "user_id";
	String USER_NR = "user_nr";
	String EVENT_ID = "event_id";

    }

    interface EventColumns {
	String EVENT_ID = "event_id";
	String DATE_TIME = "date_time";
	String ACTION_ID = "action_id";
	String LOCATION_ID = "location_id";
    }

    interface ActionColumns {
	String ACTION_ID = "action_id";
	String WiFi_ON_OR_OFF = "wifi_on_or_off";
	String GPS_ON_OR_OFF = "gps_on_or_off";
	String Alert_ID = "alert_id";
    }

    interface LocationColumns {
	String LOCATION_ID = "location_id";
	String LAT = "lat";
	String LON = "lon";
    }

    interface AlertColumns {
	String Alert_ID = "alert_id";
	String TITLE = "title";
	String VOLUME = "volume";
	String VIBRATOR_ON_OR_OFF = "vibrator_on_or_off";
    }

    public static final String CONTENT_AUTHORITY = "com.smartsilent.profilesprovider";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"
	    + CONTENT_AUTHORITY);

    public static final String PATH_USERS = "users";
    public static final String PATH_PROFILES = "profiles";
    public static final String PATH_EVENTS = "events";
    public static final String PATH_ACTIONS = "actions";
    public static final String PATH_LOCATIONS = "locations";
    public static final String PATH_ALERTS = "alerts";

    public static class Users implements UsersColumns, BaseColumns {
	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
	.appendPath(PATH_USERS).build();

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.smartsilent.users";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.smartsilent.users";

    }

    public static class Profiles implements ProfileColumns, BaseColumns {
	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
	.appendPath(PATH_PROFILES).build();

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.smartsilent.profiles";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.smartsilent.profiles";

    }

    public static class Events implements EventColumns, BaseColumns {
	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
	.appendPath(PATH_EVENTS).build();

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.smartsilent.events";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.smartsilent.events";

    }

    public static class Actions implements ActionColumns, BaseColumns {
	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
	.appendPath(PATH_ACTIONS).build();

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.smartsilent.actions";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.smartsilent.actions";

    }

    public static class Locations implements LocationColumns, BaseColumns {
	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
	.appendPath(PATH_LOCATIONS).build();

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.smartsilent.locations";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.smartsilent.locations";

    }

    public static class Alerts implements AlertColumns, BaseColumns {
	public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
	.appendPath(PATH_ALERTS).build();

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.smartsilent.alerts";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.smartsilent.alerts";

    }

}
