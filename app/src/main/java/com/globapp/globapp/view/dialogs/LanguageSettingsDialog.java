package com.globapp.globapp.view.dialogs;

import android.content.Context;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.Preferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LanguageSettingsDialog {

    private final Context context;

    public LanguageSettingsDialog(Context context){
        this.context = context;
    }

    public BottomSheetDialog createDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.SheetDialog);
        if(Preferences.getDarkMode()){
            bottomSheetDialog.setContentView(R.layout.fragment_settings_language_dark);
        } else {
            bottomSheetDialog.setContentView(R.layout.fragment_settings_language);
        }

        CardView spanish = bottomSheetDialog.findViewById(R.id.spanish_button);
        CardView english = bottomSheetDialog.findViewById(R.id.english_button);

        if(Preferences.getEnglish()){
            assert english != null;
            english.setCardBackgroundColor(context.getResources().getColor(R.color.globant_main_color));
        } else {
            assert spanish != null;
            spanish.setCardBackgroundColor(context.getResources().getColor(R.color.globant_main_color));
        }

        assert spanish != null;
        spanish.setOnClickListener(v1 -> {
            Preferences.setIsEnglish(false);
            Toast.makeText(context, context.getString(R.string.spanish),Toast.LENGTH_LONG).show();
            bottomSheetDialog.cancel();
        });

        assert english != null;
        english.setOnClickListener(v1 -> {
            Preferences.setIsEnglish(true);
            Toast.makeText(context, context.getString(R.string.english),Toast.LENGTH_LONG).show();
            bottomSheetDialog.cancel();
        });

        return bottomSheetDialog;
    }
}
