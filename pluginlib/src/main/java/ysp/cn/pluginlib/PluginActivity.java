package ysp.cn.pluginlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static ysp.cn.pluginlib.ProxyActivity.PROXY_FROM;

/**
 * 替代系统的activity
 */
public class PluginActivity extends AppCompatActivity implements IPluginListener {

    private int FROM = FROM_INTERNAL;

    private AppCompatActivity proxyActivity;

    @Override
    public void attach(AppCompatActivity activity) {
        this.proxyActivity = activity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState != null) {
            FROM = saveInstanceState.getInt(PROXY_FROM);
        }
        if (FROM == FROM_INTERNAL) {
            super.onCreate(saveInstanceState);
            proxyActivity = this;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (FROM == FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            proxyActivity.setContentView(layoutResID);
        }

    }

    @Override
    public void onDestroy() {
        if (FROM == FROM_INTERNAL) {
            super.onDestroy();
        }
    }
}
