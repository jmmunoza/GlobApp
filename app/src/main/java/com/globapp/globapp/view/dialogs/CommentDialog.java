package com.globapp.globapp.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.DataRepository;
import com.globapp.globapp.data.listeners.OnCommentAddedListener;
import com.globapp.globapp.data.listeners.OnUserImageClickedListener;
import com.globapp.globapp.model.Comment;
import com.globapp.globapp.util.KeyboardManager;
import com.globapp.globapp.util.ToastMaker;
import com.globapp.globapp.view.adapters.CommentListAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Objects;

public class CommentDialog extends BottomSheetDialogFragment implements OnCommentAddedListener {
    private CommentListAdapter  commentListAdapter;
    private TextInputEditText   commentInput;
    private ImageButton         commentSendButton;
    private RecyclerView        commentList;
    private BottomSheetBehavior commentBehavior;

    // DATA
    private final ObjectId            newsID;

    // LISTENER
    private OnUserImageClickedListener onUserImageClickedListener;

    @NonNull @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setStyle(STYLE_NORMAL, R.style.SheetDialog);
        View view = View.inflate(getContext(), R.layout.fragment_news_item_comment, null);

        commentInput      = view.findViewById(R.id.comment_input);
        commentSendButton = view.findViewById(R.id.comment_send_button);
        commentList       = view.findViewById(R.id.comment_list);

        sendButtonFunction();
        commentListFunction();
        loadComments();

        DataRepository.subscribeComment(this, newsID);

        BottomSheetDialog parent = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        parent.setContentView(view);
        commentBehavior = BottomSheetBehavior.from((View) view.getParent());
        return parent;
    }

    private void sendButtonFunction(){
        commentSendButton.setOnClickListener(v -> {
            KeyboardManager.hide(requireContext(), commentInput.getWindowToken());
            String commentContent = Objects.requireNonNull(commentInput.getText()).toString();
            if(commentContent.length() > 0){
                commentInput.setText("");
                DataRepository.insertComment(newsID, commentContent, newComment -> {
                    ToastMaker.show("comentario publicado");
                    commentListAdapter.insertComment(newComment);
                    commentList.scrollToPosition(commentListAdapter.getItemCount()-1);
                });
            }
        });
    }

    private void commentListFunction(){
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        commentListAdapter = new CommentListAdapter(getContext(), new ArrayList<>(), onUserImageClickedListener, this);
        commentList.setLayoutManager(verticalLayoutManager);
        commentList.setAdapter(commentListAdapter);
    }

    public CommentDialog(ObjectId newsID) {
        this.newsID = newsID;
    }

    private void loadComments() {
        DataRepository.getNewsComments(newsID, commentList -> {
            commentListAdapter = new CommentListAdapter(getContext(), commentList, onUserImageClickedListener, this);
            CommentDialog.this.commentList.setAdapter(commentListAdapter);
        });
    }

    public void addOnUserImageClickedListener(OnUserImageClickedListener onUserImageClickedListener){
        this.onUserImageClickedListener = onUserImageClickedListener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        DataRepository.unsubscribeComment(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        commentBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void update(Comment comment) {
        getActivity().runOnUiThread(() -> commentListAdapter.insertComment(comment));
    }
}