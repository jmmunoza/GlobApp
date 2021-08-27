package com.globapp.globapp.classes;

import java.util.ArrayList;

public class Me extends User {

    private int meCredits;
    private int meStars;

    public Me(String meID,      String meName,          String meDescription,
              int meCredits,    int meStars,            int meImage,
              int meCoverImage, ArrayList<News> meNews, ArrayList<Recognition> meRecognitions){

        super(meID, meName, meDescription, meImage, meCoverImage, meNews, meRecognitions);

        this.meCredits      = meCredits;
        this.meStars        = meStars;
    }

    public Me(String meID, String meName, String meDescription, int meCredits,
              int meStars, int meImage,   int meCoverImage){

        super(meID, meName, meDescription, meImage, meCoverImage);
        this.meCredits      = meCredits;
        this.meStars        = meStars;
    }

    public int getMeCredits() {
        return meCredits;
    }

    public int getMeStars() {
        return meStars;
    }
}
