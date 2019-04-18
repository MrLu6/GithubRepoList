package com.example.bestteamever.githubrepolist.handler;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.bestteamever.githubrepolist.R;
import com.example.bestteamever.githubrepolist.adapter.RepoListAdapter;
import com.example.bestteamever.githubrepolist.controller.MainActivity;
import com.example.bestteamever.githubrepolist.sharePre.SharedPrefManager;

import static com.example.bestteamever.githubrepolist.handler.NetworkDataHandler.convertJsonData;

public class ViewSateHandler implements viewStateHandlerService{

    private static String TAG = " ViewSateHandler";
    public static MainActivity mainActivity;
    public static SharedPrefManager sharedPrefManager;

    public ViewSateHandler(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        sharedPrefManager = new SharedPrefManager(this.mainActivity);
    }

    @Override
    public void initViews() {
        mainActivity.recyclerView= mainActivity.findViewById(R.id.recyclerView);
        mainActivity.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity.getApplicationContext()));
        mainActivity.recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void initSwipeRefresh() {
        mainActivity.swipeContainer = mainActivity.findViewById(R.id.swipeContainer);
        mainActivity.swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
    }


    public static void viewDisplay() {
        Log.d(TAG,"ViewDisplay get Called");
        mainActivity.recyclerView.setAdapter(new RepoListAdapter(mainActivity, convertJsonData(sharedPrefManager.getAPIResponse())));
        mainActivity.recyclerView.smoothScrollToPosition(0);
        mainActivity.swipeContainer.setRefreshing(false);

    }
}
