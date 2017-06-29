package com.jeff.ttxs.greendaodemo;

import android.app.Application;

import com.jeff.ttxs.greendaodemo.db.GreenDaoHelper;

/**
 * Created by ttxs on 2017/6/29.
 */

public class GreenDaoApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        //初始化数据库
        GreenDaoHelper.initDataBase(this);
    }
}
