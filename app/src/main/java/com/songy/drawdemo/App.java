package com.songy.drawdemo;

import android.app.Application;
import android.content.Context;

/**
 * Description:
 *
 * @author by song on 2019-08-09.
 * email：bjay20080613@qq.com
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.instance().init(this);
    }

}
