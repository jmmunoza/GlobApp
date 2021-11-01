package com.globapp.globapp.view.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.listeners.OnUserImageClickedListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.bson.types.ObjectId;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView commentUserImage;
    public TextView        commentUsername;
    public TextView        commentContent;
    public TextView        commentTime;

    /*   En caso de que la lista de comentarios se abra desde un fragmento flotante. Sirve
         para cerrar el fragmento si se clickea la imagen de algun usuario    */
    private final BottomSheetDialogFragment parent;

    // Data
    private ObjectId userWhoCommentedID;

    // Listener
    private final OnUserImageClickedListener onUserImageClickedListener;

    public void setUserWhoCommentedID(ObjectId userWhoCommentedID){
        this.userWhoCommentedID = userWhoCommentedID;
    }

    public CommentListViewHolder(@NonNull View itemView,
                                 OnUserImageClickedListener onUserImageClickedListener,
                                 BottomSheetDialogFragment parent) {
        super(itemView);

        this.parent = parent;
        this.onUserImageClickedListener = onUserImageClickedListener;

        commentUserImage = itemView.findViewById(R.id.comment_item_user_image);
        commentUsername  = itemView.findViewById(R.id.comment_item_username);
        commentContent   = itemView.findViewById(R.id.comment_item_content);
        commentTime      = itemView.findViewById(R.id.comment_item_time);

        userImageFunction();
    }

    private void userImageFunction(){
        commentUserImage.setOnClickListener(v -> {
            if(parent != null) parent.dismiss();
            onUserImageClickedListener.onUserImageClicked(userWhoCommentedID);
        });
    }
}