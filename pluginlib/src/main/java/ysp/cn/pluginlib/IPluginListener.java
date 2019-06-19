package ysp.cn.pluginlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 用于规范PluginActivity应该具备哪些方法
 */
public interface IPluginListener {

    //区分是作为插件让其他app打开还是作为单独apk让系统打开
    int FROM_INTERNAL = 0;//系统
    int FROM_EXTERNAL = 1;

    void attach(AppCompatActivity activity);//传递上下文

    void onCreate(Bundle saveInstanceState);

    void onDestroy();
}
