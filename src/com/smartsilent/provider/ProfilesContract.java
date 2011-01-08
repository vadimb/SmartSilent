package com.smartsilent.provider;


import android.app.SearchManager;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.DateUtils;

/**
 * Contract class for interacting with {@link ProfilesProvider}. 
 */

public class ProfilesContract {

	/**
     * Special value for {@link SyncColumns#UPDATED} indicating that an entry
     * has never been updated, or doesn't exist yet.
     */
    public static final long UPDATED_NEVER = -2;

    /**
     * Special value for {@link SyncColumns#UPDATED} indicating that the last
     * update time is unknown, usually when inserted from a local file source.
     */
    public static final long UPDATED_UNKNOWN = -1;

    //The columns we'll include in our DB.
    
    public interface SyncColumns {
        /** Last time this entry was updated or synchronized. */
        String UPDATED = "updated";
    }
    
    interface ProfileColumns {    	
		String PROFILE_ID = "profile_id";
		String OFF_OR_ON = "off_or_on";
		String DESC = "desc";
		String PUBLIC_OR_PRIVATE = "public_or_private";
		String USER_ID = "user_id";
		String USER_NR = "user_nr";
		String USER_NAME = "user_name";
		String EVENT_ID="event_id";

	}

	interface EventColumns {
		String EVENT_ID = "event_id";
		String DATE_TIME = "date_time";
	}

	interface ActionColumns {
		String ACTION_ID = "action_id";
		String WiFi_ON_OR_OFF = "wifi_on_or_off";
		String GPS_ON_OR_OFF = "gps_on_or_off";

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

	interface BookmarkColumns {
		String BOOKMARK_ID = "bookmark_id";
		String DATE_TIME = "date_time";
	}
	
	interface TagColumns {
		String TAG_ID = "tag_id";
		String TAG = "tag";
	}
	
	
	public static final String CONTENT_AUTHORITY = "com.smartsilent";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //TODO : Add paths as we have an impl for them.
    
    private static final String PATH_PROFILES = "profiles";
    private static final String PATH_TAGS = "tags";
    private static final String PATH_BOOKMARKS = "bookmarks";
    private static final String PATH_EVENTS = "events";
    private static final String PATH_ACTIONS = "actions";
    private static final String PATH_LOCATIONS = "locations";
    private static final String PATH_ALERTS = "alerts";
    
    
    //TODO : Add table's definition as we progress.
    
    public static class Profiles implements ProfileColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROFILES).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.smartsilent.profiles";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.smartsilent.profiles";          
          
    } 
    
    public static class Tags implements TagColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAGS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.smartsilent.tags";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.smartsilent.tags";          
          
    }
    
    public static class Bookmarks implements BookmarkColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BOOKMARKS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.smartsilent.bookmarks";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.smartsilent.bookmarks";          
          
    }
    
    public static class Events implements EventColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EVENTS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.smartsilent.events";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.smartsilent.events";          
          
    }
    
    public static class Actions implements ActionColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ACTIONS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.smartsilent.actions";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.smartsilent.actions";          
          
    }
    
    public static class Locations implements LocationColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATIONS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.smartsilent.locations";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.smartsilent.locations";          
          
    }
    
    public static class Alerts implements AlertColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ALERTS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.smartsilent.alerts";
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.smartsilent.alerts";          
          
    }
    
    


}
