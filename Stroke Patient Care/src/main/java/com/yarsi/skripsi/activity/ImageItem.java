package com.yarsi.skripsi.activity;

import android.graphics.Bitmap;

/**
 * Created by Ocky Aditia Saputra on 01/03/2016.
 */

public class ImageItem {
    private Bitmap image;
    private String title;
    private String id;
    private String id_to_be_deleted;

    public ImageItem(Bitmap image, String title, String id, String id_to_be_deleted) {
        super();
        this.image = image;
        this.title = title;
        this.id = id;
        this.id_to_be_deleted = id_to_be_deleted;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTobeDeleted() {
        return id_to_be_deleted;
    }
}