package com.user.canopas.custom_contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.security.Provider;

/**
 * Created by canopas on 26/08/17.
 */

public class UserProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "com.user.canopas.custom_contentprovider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/Users");
    private static final int USERS = 1;
    private static final int USER_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();

    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "Users", USERS);
        uriMatcher.addURI(PROVIDER_NAME, "Users/#", USER_ID);
        return uriMatcher;
    }

    private UserDatabase userDataBase = null;


    @Override
    public boolean onCreate() {
        Context context = getContext();
        userDataBase = new UserDatabase(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String id = null;
        if(uriMatcher.match(uri)==USER_ID)
            id=uri.getPathSegments().get(1);

        return userDataBase.getUsers(id,projection,selection,selectionArgs,sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case USERS:
                return "vnd.android.cursor.dir/vnd.com.user.canopas.custom_contentprovider.users";
            case USER_ID:
                return "vnd.android.cursor.item/vnd.com.user.canopas.custom_contentprovider.users";
        }
        return "";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
       try
       {
           Long id=userDataBase.addNewUser(contentValues);
           Uri returnUri= ContentUris.withAppendedId(CONTENT_URI, id);
           return returnUri;
       }
       catch (Exception e)
       { return null;}

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        String id=null;
        if(uriMatcher.match(uri)==USER_ID)
            id=uri.getPathSegments().get(1);

        return userDataBase.deleteUsers(id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        String id = null;
        if(uriMatcher.match(uri) == USER_ID) {
            //Update is for one single image. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }

        return userDataBase.updateUsers(id, contentValues);
    }
}
