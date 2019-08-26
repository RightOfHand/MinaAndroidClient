package com.songy.drawdemo.mina;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Description:后台服务 保持连接
 *
 * @author by song on 2019-08-21.
 * email：bjay20080613@qq.com
 */
public class MinaService extends Service {
    private ConnectionThread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        thread=new ConnectionThread("mina",getApplicationContext());
        thread.start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.disConnection();
        thread=null;
    }

    class ConnectionThread extends HandlerThread{

        private Context context;
        boolean isConnection;
        ConnectionManager mMananger;

        public ConnectionThread(String name,Context context) {
            super(name);
            this.context=context;
            ConnectionConfig config=new ConnectionConfig.Builder(context)
                    .setIP("192.168.0.24")
                    .setPort(10018)
                    .setReadBufferSize(10240)
                    .setConnectionTimeout(10000)
                    .builder();
            mMananger=new ConnectionManager(config);
        }

        @Override
        protected void onLooperPrepared() {
            for (;;){
                isConnection=mMananger.connect();  //连接

                if (isConnection){
                    break;
                }
                try {
                    Thread.sleep(3000);
                }catch (Exception e){

                }
            }
        }

        public void disConnection(){
            mMananger.disconnect();
        }
    }
}
