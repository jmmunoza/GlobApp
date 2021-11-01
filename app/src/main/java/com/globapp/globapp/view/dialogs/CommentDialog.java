package com.globapp.globapp.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.local.Preferences;
import com.globapp.globapp.util.KeyboardManager;
import com.globapp.globapp.view.adapters.CommentListAdapter;
import com.globapp.globapp.view.fragments.FragmentOnNotification;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class CommentDialog extends BottomSheetDialogFragment {
    private CommentListAdapter  commentListAdapter;
    private TextInputEditText   commentInput;
    private ImageButton         commentSendButton;
    private RecyclerView        commentList;
    private RelativeLayout      commentRoot;
    private BottomSheetBehavior commentBehavior;
    private ObjectId            newsID;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.SheetDialog);
        View view;
        if(Preferences.getDarkMode()){
            view = View.inflate(getContext(), R.layout.fragment_news_item_comment_dark, null);
        } else {
            view = View.inflate(getContext(), R.layout.fragment_news_item_comment, null);
        }

        commentRoot       = view.findViewById(R.id.comment_root);
        commentInput      = view.findViewById(R.id.comment_input);
        commentSendButton = view.findViewById(R.id.comment_send_button);
        commentList       = view.findViewById(R.id.comment_list);

        sendButtonFunction();
        commentListFunction();
        loadComments();


        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setContentView(view);
        commentBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    private void sendButtonFunction(){
        commentSendButton.setOnClickListener(v -> {
            String commentContent = commentInput.getText().toString();
            if(commentContent.length() > 0){
                commentInput.setText("");
                KeyboardManager.hide();

                DataRepository.insertComment(newsID, commentContent, commentsCount -> {
                    //Toast.makeText(getContext(), "COMENTARIO PUBLICADO", Toast.LENGTH_LONG).show();

                    //commentNews.getNewsComments().add(newCommentDocument);
                    //commentListAdapter.notifyItemInserted(commentNews.getNewsComments().size()-1);
                    //commentList.scrollToPosition(commentNews.getNewsComments().size()-1);
                });
            }
        });
    }

    private void commentListFunction(){
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        commentListAdapter = new CommentListAdapter(getContext(), new ArrayList<>(), new FragmentOnNotification.OnNotificationListener() {
            @Override
            public void onUserImageClicked(ObjectId userID) {

            }
        });
        commentList.setLayoutManager(verticalLayoutManager);
        commentList.setAdapter(commentListAdapter);
    }

    public CommentDialog(ObjectId newsID) {
        this.newsID = newsID;
    }

    private void loadComments() {
        //commentListAdapter = new CommentListAdapter(getContext(), commentNews.getNewsComments(), this);
        //commentList.setAdapter(commentListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        commentBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}