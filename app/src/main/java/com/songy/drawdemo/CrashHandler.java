package com.songy.drawdemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:Global Exception Caught
 *
 * @author by song on 2019-08-16.
 * email：bjay20080613@qq.com
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context mContext;
    private static CrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //存储设备及异常信息
    private Map<String, String> mInfo = new HashMap<>();
    private DateFormat fileDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CrashHandler() {

    }

    public static CrashHandler instance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                mInstance = new CrashHandler();
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        //1.收集错误信息
        //2.保存错误信息
        //3.上传到服务器

        if (!handlerException(throwable)) {
            //未处理 调用系统默认的处理器处理
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(thread, throwable);
            }
        } else {
            //人为处理
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Process.killProcess(Process.myPid());
            System.exit(1);
        }

    }

    /**
     * des: 处理异常信息
     *
     * @return true 已处理，false未处理
     */
    private boolean handlerException(Throwable e) {
        if (e == null) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "uncaughtException", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //收集异常信息
        collectException();
        //保存异常信息
        saveException(e);
        return false;
    }

    private void collectException() {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (info != null) {
                String versionName = TextUtils.isEmpty(info.versionName) ? "android unknow versionName" : info.versionName;
                String versionCode = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    versionCode = String.valueOf(info.getLongVersionCode());
                } else {
                    versionCode = String.valueOf(info.versionCode);
                }

                mInfo.put("versionName",versionName);
                mInfo.put("versionCode",versionCode);

                Field[] fields=Build.class.getFields();
                if (fields!=null && fields.length>0){
                    for (Field field:fields){
                        field.setAccessible(true);
                        mInfo.put(field.getName(),field.get(null).toString());
                    }
                }


            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void saveException(Throwable e) {
        StringBuffer  saveInfo=new StringBuffer();
        for (Map.Entry<String,String> entry:mInfo.entrySet()){
            saveInfo.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        }

        Writer writer=new StringWriter();
        PrintWriter  printWriter=new PrintWriter(writer);
        e.printStackTrace(printWriter);

        Throwable cause=e.getCause();
        while (cause!=null){
            cause.printStackTrace(printWriter);
            cause=e.getCause();
        }

        printWriter.close();
        String result=writer.toString();
        saveInfo.append(result);


        String time=fileDate.format(new Date());

        String fileName="crash_"+time+".log";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path="/sdcard/crash";
            File dir=new File(path);
            if (!dir.exists()){
                dir.mkdir();
            }
            FileOutputStream fos = null;
            try {
                fos=new FileOutputStream(path+fileName);
                fos.write(saveInfo.toString().getBytes());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            finally {
                if (fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        }


    }


}
