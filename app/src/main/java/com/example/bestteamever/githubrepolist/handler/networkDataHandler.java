package com.example.bestteamever.githubrepolist.handler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.bestteamever.githubrepolist.R;
import com.example.bestteamever.githubrepolist.adapter.ItemAdapter;
import com.example.bestteamever.githubrepolist.api.Client;
import com.example.bestteamever.githubrepolist.api.GitHubClient;
import com.example.bestteamever.githubrepolist.controller.MainActivity;
import com.example.bestteamever.githubrepolist.model.GitHubRepo;
import com.example.bestteamever.githubrepolist.sharePre.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class networkDataHandler implements handlerService{

    private String TAG = "networkDataHandler";
    private Context context;
    private SharedPrefManager sharedPrefManager;
    public final static int NULL_RESPONSE = 0;
    public final static int ZERO_RESPONSE = 1;
    public final static int SOME_RESPONSE = 2;

    public networkDataHandler(Context context){
        this.context = context;
        sharedPrefManager = new SharedPrefManager(this.context);
    }

    /**
     * Make get Request to user repo info and get response
     * tutorial: https://www.youtube.com/watch?v=R4XU8yPzSx0
     * @param userName: gitHub userName that user want to search
     */
    @Override
    public int loadJSON(String userName) {

            try{

                //Use Retrofit to Handle Http get request
                GitHubClient client =  Client.getClient().create(GitHubClient.class);

                // Call -> An invocation of a Retrofit method that sends a request to a webserver and returns a response.
                Call<List<GitHubRepo>> call = client.reposForUser(userName);

                /**
                 * Asynchronously send the request and notify callback of its response or if an error
                 * occurred talking to the server, creating the request, or processing the response.
                 */
                call.enqueue(new Callback<List<GitHubRepo>>() {
                    @Override
                    public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {

                        List<GitHubRepo> repos = response.body();

                        //Check if response in Logcat
                        Log.d(TAG,"onResponse Success" );

                        //Check if response is null (api limit rate can cause 403 forbidden and return null)
                        if(repos == null){
                            Log.d(TAG,"response is null" );
                            sharedPrefManager.setResponseStatus(NULL_RESPONSE);

                        }else if(repos.size() == 0){
                            Log.d(TAG,"response.size == 0" );
                            sharedPrefManager.setResponseStatus(ZERO_RESPONSE);
                        }else {
                            //set Adapter if we get some response
                            sharedPrefManager.setResponseStatus(SOME_RESPONSE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                        Log.d(TAG + "Error", t.getMessage());
                        Toast.makeText(context, context.getResources().getString(R.string.error_fecth), Toast.LENGTH_SHORT).show();

                    }
                });

            }catch (Exception e){
                Log.d(TAG + "Error", e.getMessage());
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

        return 0;
    }

    public static List<GitHubRepo> convertJsonData(String json){

        Gson gson = new Gson();
        Type type = new TypeToken<List<GitHubRepo>>() {}.getType();
        List<GitHubRepo> repoList = gson.fromJson(json, type);

        return  repoList;
    }
}
