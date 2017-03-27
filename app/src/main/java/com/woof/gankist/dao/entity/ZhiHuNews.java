package com.woof.gankist.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Woof on 3/20/2017.
 */

public class ZhiHuNews implements Serializable{

    public String date;
    public ArrayList<Story> stories;

    public static class Story {
        public ArrayList<String> images;
        public String ga_prefix;
        public String title;
        public int type;
        public int id;
    }
}
