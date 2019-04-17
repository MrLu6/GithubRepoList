package com.example.bestteamever.githubrepolist.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bestteamever.githubrepolist.R;
import com.example.bestteamever.githubrepolist.handler.NetworkDataHandler;
import com.example.bestteamever.githubrepolist.handler.ViewSateHandler;
import com.example.bestteamever.githubrepolist.sharePre.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    /* Use recycleView when needs to display a scrolling list of elements based on large data sets */
    public RecyclerView recyclerView;

    /* Used whenever the user can refresh the contents of a view via a vertical swipe gesture. */
    public SwipeRefreshLayout swipeContainer;

    /*Use for store data locally*/
    private SharedPrefManager sharedPrefManager;

    private ViewSateHandler viewSateHandler;
    private NetworkDataHandler networkDataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new SharedPrefManager(this.getApplicationContext());
        viewSateHandler = new ViewSateHandler(this);
        networkDataHandler = new NetworkDataHandler(this);

        viewSateHandler.initViews();
        viewSateHandler.initSwipeRefresh();

    }

    private void swipeRefresh(){
        //Refresh search(request and get respond base on the user input)
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                networkDataHandler.loadJSON(sharedPrefManager.getSearchUserName());
                Toast.makeText(MainActivity.this, getResources().getString(R.string.refresh_promt), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search_repo);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //search given username's gitHub repo
            @Override
            public boolean onQueryTextSubmit(String s) {
                sharedPrefManager.setSearchUserName(s);
                networkDataHandler.loadJSON(sharedPrefManager.getSearchUserName());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}


