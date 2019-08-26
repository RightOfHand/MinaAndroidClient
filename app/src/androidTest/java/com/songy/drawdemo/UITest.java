package com.songy.drawdemo;

import android.app.Instrumentation;
import android.os.RemoteException;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Description:
 *
 * @author by song on 2019-08-15.
 * email：bjay20080613@qq.com
 */
@RunWith(AndroidJUnit4.class)
public class UITest {

    public Instrumentation instrumentation;
    public UiDevice uiDevice;

    @Before
    public void  instance(){
        instrumentation= InstrumentationRegistry.getInstrumentation();
        uiDevice=UiDevice.getInstance(instrumentation);
    }

    @Test
    public void goBack() throws RemoteException {
//        uiDevice.pressRecentApps();
//        uiDevice.findObject(By.res("com.songy.drawdemo:id/et_account")).click();
        uiDevice.findObject(By.res("com.songy.drawdemo:id/btn_submit")).click();

    }
}

