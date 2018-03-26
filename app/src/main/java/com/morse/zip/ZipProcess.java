package com.morse.zip;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 2018/3/26.
 */

public class ZipProcess {
    /*
      0 No error
      1 Warning (Non fatal error(s)). For example, one or more files were locked by some other application,
        so they were not compressed.
      2 Fatal error
      7 Command line error
      8 Not enough memory for operation
      255 User stopped the process
   */
    private static final int RET_SUCCESS = 0;
    private static final int RET_WARNING = 1;
    private static final int RET_FAULT = 2;
    private static final int RET_COMMAND = 7;
    private static final int RET_MEMORY = 8;
    private static final int RET_USER_STOP = 255;

    Context context = null;
    Thread thread = null;
    ProgressDialog dialog = null;
    Handler handler = null;
    String command = null;

    public ZipProcess(Context context, String command) {
        // TODO Auto-generated method stub
        this.context = context;
        this.command = command;

        dialog = new ProgressDialog(context);
        dialog.setTitle("message");
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d("morse", "操作结果:" + msg.what);
                // TODO Auto-generated method stub
                dialog.dismiss();

//                int retMsgId = R.string.msg_ret_success;
                switch (msg.what) {
                    case RET_SUCCESS:
                        Toast.makeText(ZipProcess.this.context, "成功", Toast.LENGTH_SHORT).show();
                        break;
                    case RET_WARNING:
                        Toast.makeText(ZipProcess.this.context, "警告", Toast.LENGTH_SHORT).show();
                        break;
                    case RET_FAULT:
                        Toast.makeText(ZipProcess.this.context, "RET_FAULT", Toast.LENGTH_SHORT).show();
                        break;
                    case RET_COMMAND:
                        Toast.makeText(ZipProcess.this.context, "RET_COMMAND", Toast.LENGTH_SHORT).show();
                        break;
                    case RET_MEMORY:
                        Toast.makeText(ZipProcess.this.context, "RET_MEMORY", Toast.LENGTH_SHORT).show();
                        break;
                    case RET_USER_STOP:
                        Toast.makeText(ZipProcess.this.context, "RET_USER_STOP", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
//                Toast.makeText(ZipProcess.this.context, retMsgId, Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        thread = new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int ret = ZipUtils.excuteCommand(ZipProcess.this.command);
                handler.sendEmptyMessage(ret); //send back return code
                super.run();
            }
        };
    }

    void start() {
        dialog.show();
        thread.start();
    }
}
