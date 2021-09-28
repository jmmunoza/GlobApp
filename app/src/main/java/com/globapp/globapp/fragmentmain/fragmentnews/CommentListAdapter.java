package com.globapp.globapp.fragmentmain.fragmentnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.MainActivity;
import com.globapp.globapp.R;
import com.globapp.globapp.classes.Comment;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.globapp.globapp.fragmentmain.fragmentme.FragmentMe;
import com.globapp.globapp.fragmentmain.fragmentuser.FragmentUser;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>{

    private ArrayList<Comment>       newsComments;
    private LayoutInflater            inflater;
    Context                           context;
    private BottomSheetDialogFragment parent;

    public CommentListAdapter(Context context, ArrayList<Comment> newsComments){
        this.inflater     = LayoutInflater.from(context);
        this.context      = context;
        this.newsComments = newsComments;
    }

    public CommentListAdapter(Context context, ArrayList<Comment> newsComments, BottomSheetDialogFragment parent){
        this.inflater     = LayoutInflater.from(context);
        this.context      = context;
        this.newsComments = newsComments;
        this.parent       = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(((MainActivity)context).isDarkMode){
            return new ViewHolder(inflater.inflate(R.layout.fragment_news_item_comment_item_dark, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.fragment_news_item_comment_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = newsComments.get(position);

        holder.commentContent.setText(comment.getCommentContent());
        holder.commentUsername.setText(comment.getCommentUser().getMeName());
        holder.commentUserImage.setImageURI(comment.getCommentUser().getMeImage());
    }

    @Override
    public int getItemCount() {
        return newsComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView commentUserImage;
        TextView        commentUsername;
        TextView        commentContent;
        TextView        commentTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentUserImage = itemView.findViewById(R.id.comment_item_user_image);
            commentUsername  = itemView.findViewById(R.id.comment_item_username);
            commentContent   = itemView.findViewById(R.id.comment_item_content);
            commentTime      = itemView.findViewById(R.id.comment_item_time);

            commentUserImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(parent != null) parent.dismiss();
                    if(((MainActivity)context).me.getMeID().equals(newsComments.get(getAdapterPosition()).getCommentUser().getMeID())){
                        if(((MainActivity)context).getSupportFragmentManager().getBackStackEntryCount() == 1){
                            ((MainActivity)context).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                        } else {
                            ((MainActivity)context).addFragmentUp(
                                    new FragmentMe(newsComments.get(getAdapterPosition()).getCommentUser()));
                        }
                    } else {
                        ((MainActivity)context).addFragmentUp(
                                new FragmentUser(newsComments.get(getAdapterPosition()).getCommentUser()));
                    }
                }
            });
        }
    }
}