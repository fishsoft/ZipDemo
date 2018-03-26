package com.morse.zip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("p7zip");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());
    }

    public void compress(View v) {
        compressProcess();
    }

    public void decompress(View v) {
        decompressProcess();
    }

    private void compressProcess(){
        StringBuilder sbCmd = new StringBuilder("7z a ");

//        sbCmd.append("-t7z ");   //7z a -t7z = 指定压缩后的文件类型

        sbCmd.append("'/storage/emulated/0/7z_demo/7zdemo.zip' "); //7z a '/storage/emulated/0/7z_demo/7zdemo.zip'
//        sbCmd.append("'/storage/emulated/0/wifi_config.log' "); //7z a '//storage/emulated/0/7z_demo/7zdemo.zip' '/storage/emulated/0/wifi_config.log' = 文件的压缩
        sbCmd.append("'/storage/emulated/0/zp_7100' "); //7z a '//storage/emulated/0/7z_demo/7zdemo.zip' '/storage/emulated/0/zp_7100' = 文件夹的压缩


        new ZipProcess(MainActivity.this, sbCmd.toString()).start();
    }

    private void decompressProcess() {
        StringBuilder sbCmd = new StringBuilder("7z ");
        sbCmd.append("x ");    //7z x

        //input file path
        sbCmd.append("'/storage/emulated/0/7z_demo/7zdemo.zip' "); //7z x '/storage/emulated/0/7z_demo/7zdemo.zip'
        //output path
        sbCmd.append("'-o"   + "/storage/emulated/0/' ");  //7z x '/storage/emulated/0/7z_demo/7zdemo.zip' '-o/storage/emulated/0/'

        sbCmd.append("-aoa "); //-aoa Overwrite All existing files without prompt.
        //7z x '/storage/emulated/0/7z_demo/7zdemo.zip' '-o/storage/emulated/0/' -aoa

        new ZipProcess(MainActivity.this, sbCmd.toString()).start();
    }
}
