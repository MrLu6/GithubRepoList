package com.example.bestteamever.githubrepolist.sharePre;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.bestteamever.githubrepolist.R;
import com.google.gson.Gson;

import java.util.List;

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

    /*Store api response*/
    //https://stackoverflow.com/questions/28107647/how-to-save-listobject-to-sharedpreferences/28107791
    public <T> void setAPIResponse(String response, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(res.getString(R.string.api_response),response);
        editor.apply();
    }

    public String getAPIResponse(){
        return sharedPref.getString(res.getString(R.string.api_response),res.getString(R.string.emptyString));
    }

    //set response state
    public void setResponseStatus(int status){
        editor.putInt(res.getString(R.string.response_status),status);
        editor.apply();
    }

    public int getResponseStatus(){
        return sharedPref.getInt(res.getString(R.string.api_response),0);
    }



}
