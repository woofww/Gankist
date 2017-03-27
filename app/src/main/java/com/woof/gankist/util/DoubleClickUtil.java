package com.woof.gankist.util;

/**
 * Created by Woof on 3/21/2017.
 */

public class DoubleClickUtil {

    private static long mLastClick = 0L;

    private static final int THRESHOLD = 1500; // 暂定1500ms

    /**
     * CC means checkclick
     */
    public static boolean CC() {

        long current = System.currentTimeMillis();
        boolean state = (current - mLastClick) < THRESHOLD; //如果小于阈值
        mLastClick = current;
        return state;
    }
}
