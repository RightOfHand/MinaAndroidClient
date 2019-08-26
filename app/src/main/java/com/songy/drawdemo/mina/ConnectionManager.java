package com.songy.drawdemo.mina;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;

/**
 * Description:mina 客户端与服务端连接管理器
 *
 * @author by song on 2019-08-21.
 * email：bjay20080613@qq.com
 */
public class ConnectionManager {
    public static final String BROADCAST_ACTION="com.songy.drawdemo.broadcast";
    public static final String MESSAGE="message";
    private ConnectionConfig mConfig;
    private WeakReference<Context> mContext;
    private NioSocketConnector mConnector;
    private IoSession mSession;
    private InetSocketAddress mAddress;

    public ConnectionManager(ConnectionConfig mConfig) {
        this.mConfig = mConfig;
        mContext=new WeakReference<Context>(mConfig.getContext());
        init();
    }

    private void init() {
        mAddress=new InetSocketAddress(mConfig.getIp(),mConfig.getPort());
        mConnector=new NioSocketConnector();

        mConnector.getSessionConfig().setReceiveBufferSize(mConfig.getReadBufferSize());
        mConnector.getFilterChain().addLast("logging",new LoggingFilter());
        mConnector.getFilterChain().addLast("codec",new ProtocolCodecFilter(
                new ObjectSerializationCodecFactory()
        ));
        mConnector.setHandler(new DefaultHandler(mContext.get()));
        mConnector.setDefaultRemoteAddress(mAddress);

    }

    /**
     * Description: 取得与服务器连接
     */
    public boolean connect(){
        try {
            ConnectFuture future=mConnector.connect();
            future.awaitUninterruptibly();
            mSession=future.getSession();
            SessionManager.getInstance().setSession(mSession);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return mSession != null;
    }

    public void disconnect(){
        mConnector.dispose();
        mConnector=null;
        mSession=null;
        mAddress=null;
        mContext=null;
    }

    private static class DefaultHandler extends IoHandlerAdapter{
        private Context mContext;

        public DefaultHandler(Context context) {
            this.mContext=context;
        }

        @Override
        public void sessionCreated(IoSession session) throws Exception {
            super.sessionCreated(session);

        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            super.sessionOpened(session);

        }

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            super.sessionClosed(session);
        }

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            super.messageReceived(session, message);
            Intent intent=new Intent(BROADCAST_ACTION);
            intent.putExtra(MESSAGE,message.toString());

            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }

        @Override
        public void messageSent(IoSession session, Object message) throws Exception {
            super.messageSent(session, message);
        }

    }

}
