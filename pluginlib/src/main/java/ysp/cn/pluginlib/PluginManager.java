package ysp.cn.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 将PluginAPK里面的对象进行创建
 */
public class PluginManager {

    private static final PluginManager instance = new PluginManager();

    public static PluginManager getInstance() {
        return instance;
    }

    private PluginManager() {
    }

    private Context context;
    private PluginAPK pluginAPK;

    public PluginAPK getPluginAPK() {
        return pluginAPK;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    //加载apk
    public void loadAPK(String apkPath) {
        PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) {
            return;
        }
        DexClassLoader classLoader = createDexClassLoader(apkPath);
        AssetManager am = createAssetManager(apkPath);
        Resources resources = createResource(am);
        pluginAPK = new PluginAPK(packageInfo, resources, classLoader);
    }

    //加载dex文件
    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = context.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, context.getClassLoader());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager am = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(am, apkPath);
            return am;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //加载res文件
    private Resources createResource(AssetManager am) {
        Resources resources = context.getResources();
        return new Resources(am, resources.getDisplayMetrics(), resources.getConfiguration());
    }
}
