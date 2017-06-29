package com.jeff.ttxs.greendaodemo;

/**
 * Created by ttxs on 2017/6/29.
 */

public class BaseUtils
{
    public static String combinString(String str1,String str2){
        StringBuilder builder = new StringBuilder();
        builder.append(str1);
        builder.append(" : ");
        builder.append(str2);
        return builder.toString();
    }
}
