package com.example.bestteamever.githubrepolist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GitHubOwner {

    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;

    public String getAvatarUrl() {
        return avatar_url;
    }

}
