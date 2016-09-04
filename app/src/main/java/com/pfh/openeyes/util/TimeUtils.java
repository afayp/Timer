package com.pfh.openeyes.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/4.
 */
public class TimeUtils {

    public static String convertTime(int duration){

        int minute = (duration - duration % 60) / 60;

        int second = duration % 60;

        return minute+"'"+second + '"';
    }


    /**
     * 把long转换成Sep.03
     * @param number
     * @return
     */
    public static String convertDate(long number){
        Date date = new Date(number);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        return sdf.format(date);

    }
}
