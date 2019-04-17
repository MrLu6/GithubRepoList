package com.example.bestteamever.githubrepolist.handler;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bestteamever.githubrepolist.R;
import com.example.bestteamever.githubrepolist.adapter.ItemAdapter;
import com.example.bestteamever.githubrepolist.controller.MainActivity;
import com.example.bestteamever.githubrepolist.sharePre.SharedPrefManager;

import static com.example.bestteamever.githubrepolist.handler.networkDataHandler.NULL_RESPONSE;
import static com.example.bestteamever.githubrepolist.handler.networkDataHandler.SOME_RESPONSE;
import static com.example.bestteamever.githubrepolist.handler.networkDataHandler.ZERO_RESPONSE;
import static com.example.bestteamever.githubrepolist.handler.networkDataHandler.convertJsonData;

public class viewSateHandler implements viewStateHandlerService{

    private MainActivity mainActivity;
    private SharedPrefManager sharedPrefManager;

    public viewSateHandler(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        sharedPrefManager = new SharedPrefManager(this.mainActivity);
    }

    @Override
    public void initViews() {
        mainActivity.recyclerView=(RecyclerView) mainActivity.findViewById(R.id.recyclerView);
        mainActivity.recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity.getApplicationContext()));
        mainActivity.recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void initSwipeRefresh() {
        mainActivity.swipeContainer = (SwipeRefreshLayout) mainActivity.findViewById(R.id.swipeContainer);
        mainActivity.swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
    }

    @Override
    public void viewDisplay() {
        int viewType = sharedPrefManager.getResponseStatus();
        switch(viewType) {
            case NULL_RESPONSE:

                break;
            case ZERO_RESPONSE:

                break;
            case SOME_RESPONSE:
                mainActivity.recyclerView.setAdapter(new ItemAdapter(mainActivity, convertJsonData(sharedPrefManager.getAPIResponse())));
                mainActivity.recyclerView.smoothScrollToPosition(0);
                break;
        }
    }
}
