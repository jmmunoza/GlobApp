package com.globapp.globapp.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.listeners.OnUserImageClickedListener;
import com.globapp.globapp.model.Comment;
import com.globapp.globapp.util.DateTextGetter;
import com.globapp.globapp.util.ImageConverter;
import com.globapp.globapp.util.UserNameGetter;
import com.globapp.globapp.view.viewholders.CommentListViewHolder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListViewHolder>{

    private final ArrayList<Comment> newsComments;
    private final LayoutInflater      inflater;
    private final Context             context;
    private BottomSheetDialogFragment parent;
    private final OnUserImageClickedListener onUserImageClickedListener;

    public CommentListAdapter(Context context, ArrayList<Comment> newsComments, OnUserImageClickedListener onUserImageClickedListener){
        this.context      = context;
        this.inflater     = LayoutInflater.from(context);
        this.newsComments = newsComments;
        this.onUserImageClickedListener = onUserImageClickedListener;
    }

    public CommentListAdapter(Context context, ArrayList<Comment> newsComments, OnUserImageClickedListener onUserImageClickedListener, BottomSheetDialogFragment parent){
        this.context      = context;
        this.inflater     = LayoutInflater.from(context);
        this.newsComments = newsComments;
        this.parent       = parent;
        this.onUserImageClickedListener = onUserImageClickedListener;
    }

    public void insertComment(Comment newComment){
        newsComments.add(newComment);
        notifyItemInserted(newsComments.size()-1);
    }

    @NonNull @Override
    public CommentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CommentListViewHolder(
                    inflater.inflate(R.layout.fragment_news_item_comment_item, parent, false),
                    onUserImageClickedListener,
                    CommentListAdapter.this.parent);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentListViewHolder holder, int position) {
        Comment comment = newsComments.get(position);
        holder.setUserWhoCommentedID(comment.getCommentUser());

        DataRepository.getUser(comment.getCommentUser(), user -> {
            holder.commentContent.setText(comment.getCommentContent());
            holder.commentTime.setText(DateTextGetter.getDateText(comment.getCommentDate()));
            holder.commentUsername.setText(UserNameGetter.getUserName(user));

            if(user.getUserImage() != null) {
                holder.commentUserImage.setImageBitmap(ImageConverter.ByteArrayToBitmap(user.getUserImage()));
            } else {
                holder.commentUserImage.setImageResource(R.drawable.user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsComments.size();
    }
}