package com.example.bestteamever.githubrepolist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GitHubRepo {

    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    @SerializedName("stargazers_count")
    @Expose
    private int stargazers_count;

    public int getStargazers_count() {
        return stargazers_count;
    }

    @SerializedName("owner")
    @Expose
    private GitHubOwner gitHubOwner;

    public GitHubOwner getOwner() {
        return gitHubOwner;
    }
}
