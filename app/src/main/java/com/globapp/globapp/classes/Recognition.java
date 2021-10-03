package com.globapp.globapp.classes;

import android.net.Uri;

import java.util.Date;

public class Recognition {
    private String recognitionTitle;
    private Uri    recognitionImage;
    private Date   recognitionDate;
    private String recognitionReason;

    public Recognition(String recognitionTitle, String recognitionReason, Date recognitionDate, Uri recognitionImage){
        this.recognitionTitle  = recognitionTitle;
        this.recognitionDate   = recognitionDate;
        this.recognitionReason = recognitionReason;
        this.recognitionImage  = recognitionImage;
    }

    public Date getRecognitionDate() {
        return recognitionDate;
    }

    public String getRecognitionReason() {
        return recognitionReason;
    }

    public Uri getRecognitionImage() {
        return recognitionImage;
    }

    public String getRecognitionTitle() {
        return recognitionTitle;
    }
}
