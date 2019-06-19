package ysp.cn.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * 插件APK的实体对象
 */
public class PluginAPK {
    public PackageInfo packageInfo;
    public Resources resources;
    public AssetManager assetManager;
    public DexClassLoader dexClassLoader;

    public PluginAPK(PackageInfo packageInfo, Resources resources, DexClassLoader dexClassLoader) {
        this.packageInfo = packageInfo;
        this.resources = resources;
        this.assetManager = resources.getAssets();
        this.dexClassLoader = dexClassLoader;
    }
}
