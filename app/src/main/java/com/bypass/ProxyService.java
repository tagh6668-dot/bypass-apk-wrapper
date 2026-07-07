package com.bypass;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.io.*;

public class ProxyService extends Service {
    private Process process;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String link = intent != null ? intent.getStringExtra("link") : "";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(getFilesDir(), "android-client-v8a");
                    if (!file.exists()) {
                        InputStream is = getAssets().open("android-client-v8a");
                        OutputStream os = new FileOutputStream(file);
                        byte[] buffer = new byte[1024];
                        int read;
                        while ((read = is.read(buffer)) != -1) {
                            os.write(buffer, 0, read);
                        }
                        is.close();
                        os.close();
                        file.setExecutable(true);
                    }
                    
                    if (process != null) {
                        process.destroy();
                    }
                    
                    String[] cmd = {file.getAbsolutePath(), "-socks-port", "1080", link};
                    process = Runtime.getRuntime().exec(cmd);
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while (reader.readLine() != null) {}
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (process != null) {
            process.destroy();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
