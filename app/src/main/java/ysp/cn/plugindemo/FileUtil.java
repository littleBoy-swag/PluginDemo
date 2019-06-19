package ysp.cn.plugindemo;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtil {
    public static String copyAndWriteFile(Context context, String fileName) {
        File outFile = null;
        try {
            File cacheDir = context.getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            outFile = new File(cacheDir, fileName);
            if (!outFile.exists()) {
                boolean res = outFile.createNewFile();
                if (res) {
                    InputStream is = context.getAssets().open(fileName);
                    FileOutputStream fos = new FileOutputStream(outFile);
                    byte[] buffer = new byte[is.available()];
                    int byteCount = 0;
                    while ((byteCount = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, byteCount);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                    Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                    return outFile.getAbsolutePath();
                }
            } else {
                Toast.makeText(context, "文件已存在", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile.getAbsolutePath();
    }
}
