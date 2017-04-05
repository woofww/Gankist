package com.woof.gankist.dao.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Woof on 3/20/2017.
 */

public class GankSerachResult implements Serializable {

    public int count;
    public boolean error;
    public List<SearchResult> result;

    private static class SearchResult {
        @SerializedName("desc")
        public String title;
        public String ganhuo_id;
        public String publishedAt;
        public String readability;
        public String type;
        public String url;
        public String who;
    }
}
