package com.globapp.globapp.model;

import android.net.Uri;

import org.bson.types.ObjectId;

public class Project {
    ObjectId projectID;
    String   projectName;
    String   projectDescription;
    Uri      projectImage;

    public Project(ObjectId projectID, String projectName, String projectDescription, Uri projectImage){
        this.projectDescription = projectDescription;
        this.projectID          = projectID;
        this.projectName        = projectName;
        this.projectImage       = projectImage;
    }

    public ObjectId getProjectID() {
        return projectID;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getProjectName() {
        return projectName;
    }

    public Uri getProjectImage() {
        return projectImage;
    }
}
