package com.example.bestteamever.githubrepolist.sharePre;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.bestteamever.githubrepolist.R;

public class SharedPrefManager {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    public Resources res;
    public Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
        res = context.getResources();
        sharedPref = context.getSharedPreferences(res.getString(R.string.user_prefs), context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.apply();
    }

    /*Search User Information*/
    public void setSearchUserName(String userName) {
        editor.putString(res.getString(R.string.search_user_name),userName);
        editor.apply();
    }

    public String getSearchUserName(){
        return sharedPref.getString(res.getString(R.string.search_user_name),res.getString(R.string.emptyString));
    }

}
