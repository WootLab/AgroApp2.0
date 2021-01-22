package com.mystic.movieshub;

import android.content.Context;
import android.preference.PreferenceManager;

public class AccSharedPref {
    private static final String PREF_EMAIL = "";
    private static final String FIRST_TIME = "FirstTime";

    public static String getStoredEmail(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_EMAIL, null);
    }
    public static void setStoredEmail(Context context, String query){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(PREF_EMAIL, query)
                .apply();
    }
    public static void setUserState(Context context, boolean state){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(FIRST_TIME, state)
                .apply();
    }
    public  static boolean getUserState(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(FIRST_TIME, true);
    }
}
