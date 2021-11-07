package com.globapp.globapp.view.dialogs;

import android.content.Context;

import com.globapp.globapp.R;
import com.globapp.globapp.data.local.Preferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AboutSettingsDialog {
    Context context;

    public AboutSettingsDialog(Context context){
        this.context = context;
    }

    public BottomSheetDialog createDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.fragment_settings_about);
        return bottomSheetDialog;
    }
}
