package com.woof.gankist.dao.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Woof on 3/20/2017.
 */

public class GankEntity implements Serializable{
    @SerializedName("error")
    public boolean hasError;

    @SerializedName("results")
    public List<Entity> entities;

    public static class Entity implements Serializable{
        @SerializedName("_id")
        public String id;
        @SerializedName("createdAt")
        public String createdAt;

        @SerializedName("publishedAt")
        public String publishedAt;

        @SerializedName("source")
        public String source;

        @SerializedName("type")
        public String type;

        @SerializedName("url")
        public String url;

        @SerializedName("desc")
        public String title;

        @SerializedName("who")
        public String author;

        @SerializedName("images")
        public List<String> images;

    }
}
