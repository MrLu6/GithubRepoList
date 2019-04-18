package com.example.bestteamever.githubrepolist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bestteamever.githubrepolist.R;
import com.example.bestteamever.githubrepolist.model.GitHubRepo;
import com.example.bestteamever.githubrepolist.sharePre.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView.
 *
 */
public class RepoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String TAG = "ItemAdapte";
    private List<GitHubRepo> repoList;
    private Context context;
    private static final int VIEW_TYPE_NORMAL = 2;
    private SharedPrefManager sharedPrefManager;

    public RepoListAdapter(Context context, List<GitHubRepo> gitHubReposList) {
        this.context = context;
        this.repoList = gitHubReposList;
        sharedPrefManager = new SharedPrefManager(this.context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Log.d(TAG,"numviewType -> " + String.valueOf(viewType));
        if (sharedPrefManager.getResponseStatus() == VIEW_TYPE_NORMAL) {
            //view: each individual box contains avatar picture, repo name , star count and star image.
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_repo, viewGroup, false);
            return new RepoViewHolder(view);
        } else {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.no_repo_item, viewGroup, false);
            return new NoRepoViewHolder(view);
        }

    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * update the contents of the RecyclerView.ViewHolder.itemView to reflect the item at the given position.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (repoList != null) {

            if(sharedPrefManager.getResponseStatus() == VIEW_TYPE_NORMAL) {
                //Pass response data(repo name, star count and avatar url) to the view
                RepoViewHolder repoViewHolder = (RepoViewHolder) holder;
                repoViewHolder.name.setText(repoList.get(position).getName());
                repoViewHolder.numStar.setText(Integer.toString(repoList.get(position).getStargazers_count()));

                //use picasso to load image from given url
                Picasso.with(context)
                        .load(repoList.get(position).getOwner().getAvatarUrl())
                        .placeholder(R.drawable.load)
                        .into(repoViewHolder.avatarImage);

            }else {
                //empty state -> repo not found
                NoRepoViewHolder noRepoViewHolder = (NoRepoViewHolder) holder;
                noRepoViewHolder.noRepoTextView.setText(context.getString(R.string.repo_not_found));
            }

        }
    }

    //Returns the total number of items in the data set held by the adapter(base on number of repos).
    @Override
    public int getItemCount() {
        return repoList == null ? 0 : repoList.size();
    }

    //use view holder to get the reference for all attributes show on the screen
    public class RepoViewHolder extends RecyclerView.ViewHolder {

        private TextView name, numStar;
        private ImageView avatarImage;

        public RepoViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.repoName);
            numStar =  itemView.findViewById(R.id.numStar);
            avatarImage = itemView.findViewById(R.id.avatar);
        }
    }

    //empty sate: Report Repo Not Found
    public class NoRepoViewHolder extends RecyclerView.ViewHolder {

        private TextView noRepoTextView;

        public NoRepoViewHolder(View itemView) {
            super(itemView);
            noRepoTextView = itemView.findViewById(R.id.noRepoTextView);
        }

    }


}

