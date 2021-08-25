package com.globapp.globapp.classes;

public class Recognition {
    private String recognitionTitle;
    private int    recognitionImage;

    public Recognition(String recognitionTitle, int recognitionImage){
        this.recognitionTitle = recognitionTitle;
        this.recognitionImage = recognitionImage;
    }

    public int getRecognitionImage() {
        return recognitionImage;
    }

    public String getRecognitionTitle() {
        return recognitionTitle;
    }
}
