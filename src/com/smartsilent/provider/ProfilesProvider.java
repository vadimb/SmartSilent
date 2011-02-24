/**
 * 
 */
package com.smartsilent.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Provider that stores {@link ProfilesContract} data.
 */
public class ProfilesProvider extends ContentProvider {

	private static final String TAG = "ProfilesProvider";
	private static final boolean LOGV = Log.isLoggable(TAG, Log.VERBOSE);

	private ProfilesDatabase mOpenHelper;
	private static final UriMatcher sUriMatcher = buildUriMatcher();

	private static final int PROFILES=101;
	private static final int USERS=102;
	
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = ProfilesContract.CONTENT_AUTHORITY;
		matcher.addURI(authority, ProfilesContract.PATH_PROFILES, PROFILES);
		matcher.addURI(authority, ProfilesContract.PATH_USERS, USERS);
		return matcher;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int match = sUriMatcher.match(uri);
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();

		switch (match) {
		case USERS: {			
			long row_ID = db.insert(ProfilesDatabase.Tables.USERS, null, values);
			if (row_ID > 0) {
				Uri userUri = ContentUris.withAppendedId(
						ProfilesContract.Users.CONTENT_URI, row_ID);
				getContext().getContentResolver().notifyChange(userUri, null);
				return userUri;
			}
			throw new SQLException("Couldn't add a row into " + uri);
		}
		default:
			throw new IllegalArgumentException("Couldn't add a row into " + uri
					+ ".Unknown URI.");
		}
	}

	@Override
	public boolean onCreate() {
		mOpenHelper=new ProfilesDatabase(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projectionIn, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
		Cursor c;
		int match=sUriMatcher.match(uri);
		switch (match) {
		case PROFILES: {
			qBuilder.setTables("profiles");

			break;
		}
		case USERS: {
			qBuilder.setTables(ProfilesDatabase.Tables.USERS);
			break;
		}
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
		c = qBuilder.query(mOpenHelper.getReadableDatabase(), projectionIn,
				selection, selectionArgs, null, null, sortOrder);
		return c;
	}

	@Override 
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
