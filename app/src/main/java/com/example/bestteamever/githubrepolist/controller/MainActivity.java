package com.example.bestteamever.githubrepolist.controller;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bestteamever.githubrepolist.ItemAdapter;
import com.example.bestteamever.githubrepolist.R;
import com.example.bestteamever.githubrepolist.api.Client;
import com.example.bestteamever.githubrepolist.api.GitHubClient;
import com.example.bestteamever.githubrepolist.model.GitHubRepo;
import com.example.bestteamever.githubrepolist.sharePre.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private TextView Disconnected;
    private SwipeRefreshLayout swipeContainer;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this.getApplicationContext());

        initViews();
        initSwipeRefresh();

    }

    private void initViews(){
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON(sharedPrefManager.getSearchUserName());
    }

    private void initSwipeRefresh(){
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                loadJSON(sharedPrefManager.getSearchUserName());
                Toast.makeText(MainActivity.this, getResources().getString(R.string.refresh_promt), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //https://www.youtube.com/watch?v=R4XU8yPzSx0
    private void loadJSON(String userName){
        Disconnected = (TextView) findViewById(R.id.disconnected);
        try{

            GitHubClient client =  Client.getClient().create(GitHubClient.class);
            Call<List<GitHubRepo>> call = client.reposForUser(userName);

            call.enqueue(new Callback<List<GitHubRepo>>() {
                @Override
                public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                    List<GitHubRepo> repos = response.body();

                    //Check if response in Logcat
                    Log.d(TAG,"onResponse Success" );

                    //Check if response is null (api limit rate can cause 403 forbidden and return null)
                    if(repos == null){
                        Log.d(TAG,"response is null" );

                    }else if(repos.size() == 0){
                        Log.d(TAG,"response.size == 0" );
                    }else {
                        recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), repos));
                        recyclerView.smoothScrollToPosition(0);
                        swipeContainer.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                    Log.d(TAG + "Error", t.getMessage());
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error_fecth), Toast.LENGTH_SHORT).show();
                    Disconnected.setVisibility(View.VISIBLE);

                }
            });

        }catch (Exception e){
            Log.d(TAG + "Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search_repo);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                sharedPrefManager.setSearchUserName(s);
                loadJSON(sharedPrefManager.getSearchUserName());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sharedPrefManager.setSearchUserName(s);
                loadJSON(sharedPrefManager.getSearchUserName());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
}


