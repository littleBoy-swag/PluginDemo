package ysp.cn.plugindemo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ysp.cn.pluginlib.PluginManager;
import ysp.cn.pluginlib.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.getInstance().init(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            if (i != PackageManager.PERMISSION_GRANTED) {
                showDialogTipUserRequestPermission();
            }
        }

        findViewById(R.id.tv_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = FileUtil.copyAndWriteFile(MainActivity.this, "testaa.apk");
                PluginManager.getInstance().loadAPK(path);
            }
        });
        findViewById(R.id.tv_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProxyActivity.class);
                intent.putExtra(ProxyActivity.PROXY_CLASS_NAME, "ysp.cn.plugindemoapk.MainActivity");
                startActivity(intent);
            }
        });
    }

    private void showDialogTipUserRequestPermission() {
        new AlertDialog.Builder(this)
                .setMessage("申请读写权限")
                .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, permissions, 11);
                    }
                }).show();
    }
}
