package com.woof.gankist.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Woof on 3/28/2017.
 */

public class MenuUtil {
    /**
     * 利用系统发送分享数据
     *
     * @param context 上下文
     * @param text 需要分享的上下文
     */
    public static void share(Context context, String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "分享到"));
    }

    /**
     * 使用浏览器打开内容
     * @param context 上下文
     * @param uriStr 跳转链接地址
     */
    public static void openWithBrowser(Context context, String uriStr) {
        Uri uri = Uri.parse(uriStr);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 用于链接复制
     * @param context 上下文
     * @param text 复制内容
     * @return
     */
    public static boolean copyText(Context context, String text) {
        ClipboardManager manager = ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE));
        ClipData data = ClipData.newRawUri("Url", Uri.parse(text));
        manager.setPrimaryClip(data);
        return true;
    }

}
