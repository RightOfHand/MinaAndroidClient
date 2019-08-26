package com.songy.drawdemo.mina;

import org.apache.mina.core.session.IoSession;

/**
 * Description:
 *
 * @author by song on 2019-08-21.
 * email：bjay20080613@qq.com
 */
public class SessionManager {

    private static SessionManager mInstance=null;

    private IoSession mSession;


    public static  SessionManager getInstance(){
        if (mInstance==null){
            synchronized (SessionManager.class){
                if (mInstance==null){
                    mInstance=new SessionManager();
                }
            }
        }

        return mInstance;
    }

    public SessionManager() {

    }

    public void setmSession(IoSession mSession) {
        this.mSession = mSession;
    }

    public void writeToServer(Object msg){
        if (mSession!=null){
            mSession.write(msg);
        }
    }
    public void closeSession(){
        if (mSession!=null){
            mSession.closeOnFlush();
        }
    }

    public void removeSession(){
        this.mSession=null;
    }
}
