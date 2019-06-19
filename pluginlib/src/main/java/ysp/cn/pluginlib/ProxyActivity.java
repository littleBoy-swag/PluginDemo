package ysp.cn.pluginlib;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 第三方的跳转
 */
public class ProxyActivity extends AppCompatActivity {
    private static final String TAG = "ProxyActivity";
    public static final String PROXY_CLASS_NAME = "className";
    public static final String PROXY_FROM = "FROM";
    private String actName;
    private PluginAPK pluginAPK;
    private IPluginListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actName = getIntent().getStringExtra(PROXY_CLASS_NAME);
        pluginAPK = PluginManager.getInstance().getPluginAPK();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (pluginAPK == null) {
            Log.e(TAG, "apk加载有误");
        }
        try {
            Class<?> clazz = pluginAPK.dexClassLoader.loadClass(actName);
            Object object = clazz.newInstance();
            if (object instanceof IPluginListener) {
                listener = (IPluginListener) object;
                listener.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt(PROXY_FROM, IPluginListener.FROM_EXTERNAL);
                listener.onCreate(bundle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return pluginAPK != null ? pluginAPK.resources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return pluginAPK != null ? pluginAPK.assetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return pluginAPK != null ? pluginAPK.dexClassLoader : super.getClassLoader();
    }
}
