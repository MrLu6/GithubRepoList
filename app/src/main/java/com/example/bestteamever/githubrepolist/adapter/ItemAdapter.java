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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapters provide a binding from an app-specific data set to views that are displayed within a RecyclerView.
 *
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private String TAG = "ItemAdapte";
    private List<GitHubRepo> gitHubRepos;
    private Context context;


    public ItemAdapter(Context applicationContext, List<GitHubRepo> gitHubReposList) {
        this.context = applicationContext;
        this.gitHubRepos = gitHubReposList;
    }


    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        //view: each individual box contains avatar picture, repo name , star count and star image.
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_repo, viewGroup, false);

        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * update the contents of the RecyclerView.ViewHolder.itemView to reflect the item at the given position.
     */
    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int i) {
        if(gitHubRepos != null) {

            //Pass response data(repo name, star count and avatar url) to the view
            viewHolder.name.setText(gitHubRepos.get(i).getName());
            viewHolder.numStar.setText(Integer.toString(gitHubRepos.get(i).getStargazers_count()));

            //use picasso to load image from given url
            Picasso.with(context)
                    .load(gitHubRepos.get(i).getOwner().getAvatarUrl())
                    .placeholder(R.drawable.load)
                    .into(viewHolder.avatarImage);
        }
    }

    //Returns the total number of items in the data set held by the adapter(base on number of repos).
    @Override
    public int getItemCount() {
        if (gitHubRepos == null){
            return 0;
        }
        Log.d(TAG,"repos.size" + String.valueOf(gitHubRepos.size()));
        return gitHubRepos.size();
    }


    //use view holder to get the reference for all attributes show on the screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, numStar;
        private ImageView avatarImage;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.repoName);
            numStar = (TextView) view.findViewById(R.id.numStar);
            avatarImage = (ImageView) view.findViewById(R.id.avatar);
        }
    }

}

