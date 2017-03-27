package com.woof.gankist.dao.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Woof on 3/21/2017.
 */

public class ZhiHuComment implements Serializable {

    public List<Comments> mComments;


    public static class Comments {
        /**
         * author : 沉默中走来
         * content : 看完心中一股气息升腾，这样的文章放在大误里？这个标题会让多少人错过？毫不犹豫转发到朋友圈里了，对比一下朋友圈整天发的都是什么乱七八糟的玩意
         * avatar : http://pic1.zhimg.com/da8e974dc_im.jpg
         * time : 1485413572
         * id : 27937255
         * likes : 0
         * reply_to : {"content":"看的热泪盈眶了，应该放在小事里呀，怎么放大误了，图也配得不好，对小编提出批评，有点敷衍了","status":0,"id":27936877,"author":"Yuki"}
         */
        public String author;
        public String content;
        public String avatar;
        public int time;
        public int id;
        public int likes;
        public ReplyTo reply_to;
    }

    public static class ReplyTo {
        public String content;
        public int status;
        public int id;
        public String author;
    }
}
