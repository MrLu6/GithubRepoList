package com.example.bestteamever.githubrepolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bestteamever.githubrepolist.model.GitHubRepo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<GitHubRepo> gitHubRepos;
    private Context context;


    public ItemAdapter(Context applicationContext, List<GitHubRepo> gitHubReposList) {
        this.context = applicationContext;
        this.gitHubRepos = gitHubReposList;
    }


    //https://www.reddit.com/r/androiddev/comments/3bjnxi/best_way_to_handle_recyclerview_empty_state/
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_repo, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int i) {
        if(gitHubRepos != null) {
            viewHolder.name.setText(gitHubRepos.get(i).getName());
            viewHolder.numStar.setText(Integer.toString(gitHubRepos.get(i).getStargazers_count()));

            Picasso.with(context)
                    .load(gitHubRepos.get(i).getOwner().getAvatarUrl())
                    .placeholder(R.drawable.load)
                    .into(viewHolder.avatarImage);
        }
    }

    @Override
    public int getItemCount() {
        if (gitHubRepos == null){
            return 0;
        }
        return gitHubRepos.size();
    }

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

