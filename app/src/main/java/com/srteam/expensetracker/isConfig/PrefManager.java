package com.srteam.expensetracker.isConfig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefManager {

    private static final String PREF_NAME = "yoga_workout";
    private static final String REMOVE_ADS = "isRemoveAd";
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private final Context _context;
    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }


    public boolean isRemoveAd(){
        pref = PreferenceManager.getDefaultSharedPreferences(_context);
        return  pref.getBoolean(REMOVE_ADS, false);
    }

    public void setIsRemoveAd(boolean value){
        pref = PreferenceManager.getDefaultSharedPreferences(_context);
        editor = pref.edit();
        editor.putBoolean(REMOVE_ADS,value);
        editor.apply();
    }

}
