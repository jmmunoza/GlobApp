package com.globapp.globapp.view.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.data.repositories.UserDataManager;
import com.globapp.globapp.data.remote.UserGetterMongo;
import com.globapp.globapp.data.remote.UserInserterMongo;
import com.globapp.globapp.model.Comment;
import com.globapp.globapp.util.UserNameGetter;
import com.globapp.globapp.view.fragments.FragmentOnNotification;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.bson.types.ObjectId;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>{

    private final ArrayList<Comment> newsComments;
    private final LayoutInflater      inflater;

    private BottomSheetDialogFragment parent;
    private final UserDataManager     userDataManager ;
    private final FragmentOnNotification.OnNotificationListener onNotificationListener;

    public CommentListAdapter(Context context, ArrayList<Comment> newsComments, FragmentOnNotification.OnNotificationListener onNotificationListener){
        this.inflater     = LayoutInflater.from(context);
        this.newsComments = newsComments;
        this.onNotificationListener = onNotificationListener;
        this.userDataManager = new UserDataManager(
                new UserInserterMongo(),
                new UserGetterMongo()
        );
    }

    public CommentListAdapter(Context context, ArrayList<Comment> newsComments, FragmentOnNotification.OnNotificationListener onNotificationListener, BottomSheetDialogFragment parent){
        this.inflater     = LayoutInflater.from(context);
        this.newsComments = newsComments;
        this.parent       = parent;
        this.onNotificationListener = onNotificationListener;
        this.userDataManager = new UserDataManager(
                new UserInserterMongo(),
                new UserGetterMongo()
        );
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(Preferences.getDarkMode()){
            return new ViewHolder(inflater.inflate(R.layout.fragment_news_item_comment_item_dark, parent, false));
        } else {
            return new ViewHolder(inflater.inflate(R.layout.fragment_news_item_comment_item, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = newsComments.get(position);
        userDataManager.getUser(new ObjectId(comment.getCommentUser()), user -> {
            holder.commentContent.setText(comment.getCommentContent());
            holder.commentTime.setText(comment.getCommentDate().toString());
            holder.commentUsername.setText(UserNameGetter.getUserName(user));

            if(user.getUserImage() != null) {
                holder.commentUserImage.setImageURI(user.getUserImage());
            } else {
                holder.commentUserImage.setImageResource(R.drawable.user);
            }
        });
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

            commentUserImage.setOnClickListener(v -> {
                if(parent != null) parent.dismiss();
                ObjectId userClickedID = new ObjectId(newsComments.get(getAdapterPosition()).getCommentUser());
                onNotificationListener.onUserImageClicked(userClickedID);
            });
        }
    }
}