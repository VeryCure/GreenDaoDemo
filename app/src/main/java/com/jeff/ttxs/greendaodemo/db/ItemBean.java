package com.jeff.ttxs.greendaodemo.db;

import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by ttxs on 2017/6/28.
 */
@Entity
public class ItemBean
{
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;
}
