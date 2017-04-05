package com.woof.gankist.dao.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Woof on 3/20/2017.
 */

public class ZhiHuContent implements Serializable {

    public String body;
    public String image_source;
    public String title;
    public String image;
    public String share_url;
    public String ga_prefix;
    public int type;
    public int id;
    public List<String> images;
    public ArrayList<String> js;
    public ArrayList<String> css;
}
