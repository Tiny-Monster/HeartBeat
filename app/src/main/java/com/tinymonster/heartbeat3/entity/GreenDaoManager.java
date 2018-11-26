package com.tinymonster.heartbeat3.entity;


import com.tinymonster.heartbeat3.activity.StartActivity;

/**
 * Created by TinyMonster on 01/03/2018.
 */

public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance;//单例

    private GreenDaoManager(){
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new
                    DaoMaster.DevOpenHelper(StartActivity.StartContext, "user.db", null);//此处为自己需要处理的表
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }
    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {//保证异步处理安全操作

                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }
    public DaoSession getSession() {
        return mDaoSession;
    }
    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

}
