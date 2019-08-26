package com.songy.drawdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.songy.drawdemo.mina.MinaService;
import com.songy.drawdemo.mina.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static  final  String TAG="isEmulator";
    private TextView tvText;
    private EditText edtAccount;
    private Button btnSubmit;
    private Button btnClear;
    private String inputString;

    private MessageBroadReceiver receiver=new MessageBroadReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvText=(TextView) findViewById(R.id.tv_text);
        edtAccount=(EditText) findViewById(R.id.et_account);
        btnSubmit=(Button) findViewById(R.id.btn_submit);
        btnClear=(Button) findViewById(R.id.btn_clear);

        tvText.setText("android killer 牛逼大了");

        btnSubmit.setOnClickListener(this);
        btnClear.setOnClickListener(this);

//        Log.d(TAG,"checkDeviceIDS=>"+ user.getName());

        registerBroadcast();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MinaService.class));
        unRegisterBroadcast();
    }

    @Override
    public void onClick(View view) {
          if (view.getId()==R.id.btn_submit){
              Intent intent=new Intent(this,MinaService.class);
              startService(intent);


          }else if (view.getId()==R.id.btn_clear){
              inputString=edtAccount.getText().toString().trim();
              SessionManager.getInstance().writeToServer(inputString);

          }
    }

    /**
     * 动态隐藏软键盘
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputManger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManger != null) {
                inputManger.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void registerBroadcast(){
        IntentFilter filter=new IntentFilter("com.songy.drawdemo.broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
    }

    private void unRegisterBroadcast(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    public class MessageBroadReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            tvText.setText(intent.getStringExtra("message"));
        }
    }
}
