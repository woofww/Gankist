package com.woof.gankist.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Woof on 3/21/2017.
 */

public class GlideHelper {

    /**
     * diskCacheStrategy(DiskCacheStrategy.SOURCE)
     * 该类主要用于图片的加载以及清除，优化内存的占用
     */

    public static void imageLoad(Context context, String Url, ImageView view){
        Glide
                .with(context)
                .load(Url)
                .asBitmap()
                .skipMemoryCache(true)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(view);
    }

    public static void imageClear(Context context){

        Glide.get(context).clearMemory();
    }
}
