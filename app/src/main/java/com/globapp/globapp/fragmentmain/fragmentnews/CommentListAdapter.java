package com.globapp.globapp.fragmentmain.fragmentnews;

import android.annotation.SuppressLint;
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
import com.globapp.globapp.classes.User;
import com.globapp.globapp.fragmentmain.FragmentMain;
import com.globapp.globapp.fragmentmain.fragmentme.FragmentMe;
import com.globapp.globapp.fragmentmain.fragmentuser.FragmentUser;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.bson.Document;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder>{

    private ArrayList<Document>       newsComments;
    private LayoutInflater            inflater;
    Context                           context;
    private BottomSheetDialogFragment parent;

    public CommentListAdapter(Context context, ArrayList<Document> newsComments){
        this.inflater     = LayoutInflater.from(context);
        this.context      = context;
        this.newsComments = newsComments;
    }

    public CommentListAdapter(Context context, ArrayList<Document> newsComments, BottomSheetDialogFragment parent){
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = new Comment(
                newsComments.get(position).getString("content"),
                newsComments.get(position).getDate("date"),
                newsComments.get(position).getObjectId("user_id")
        );

        Document userQuery = new Document("_id", comment.getCommentUser());
        ((MainActivity)context).userCollection.findOne(userQuery).getAsync(userData -> {
            if(userData.isSuccess()){
                User user = new User(
                        userData.get().getObjectId("_id"),
                        userData.get().getString("firstName"),
                        userData.get().getString("secondName"),
                        userData.get().getString("lastName"),
                        userData.get().getString("description"),
                        null,
                        null,
                        new ArrayList<>(),
                        userData.get().getInteger("credits",0),
                        userData.get().getInteger("stars",0));

                holder.commentContent.setText(comment.getCommentContent());
                if(user.getUserSecondName() != null){
                    holder.commentUsername.setText(
                            user.getUserFirstName()  + " " +
                            user.getUserSecondName() + " " +
                            user.getUserLastName());
                } else {
                    holder.commentUsername.setText(
                            user.getUserFirstName() + " " +
                            user.getUserLastName());
                }

                if(user.getUserImage() != null) {
                    holder.commentUserImage.setImageURI(user.getUserImage());
                } else {
                    holder.commentUserImage.setImageResource(R.drawable.user);
                }

                holder.commentTime.setText(comment.getCommentDate().toString());
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
                if(((MainActivity)context).me.getUserID().equals(newsComments.get(getAdapterPosition()).getObjectId("user_id"))){
                    if(((MainActivity)context).getSupportFragmentManager().getBackStackEntryCount() == 1){
                        ((MainActivity)context).fragmentMain.mainViewPager.setCurrentItem(FragmentMain.ME);
                    } else {
                        ((MainActivity)context).addFragmentUp(new FragmentMe());
                    }
                } else {
                    ((MainActivity)context).addFragmentUp(
                            new FragmentUser(newsComments.get(getAdapterPosition()).getObjectId("user_id")));
                }
            });
        }
    }
}