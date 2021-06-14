package com.ceocho.kakaotalk.Utill;




        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.Drawable;
        import android.util.Log;

        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class BitmapUtil {
    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL("file://"+url);
            // Get connected
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            // set the timeout time is 6000 milliseconds, conn.setConnectionTiem (0); means no time limit
            conn.setConnectTimeout(6000);
            // connection settings data stream is obtained
            conn.setDoInput(true);
            // do not use cache
            conn.setUseCaches(false);
            // phrase dispensable, had no effect
            //conn.connect();
            // get the data stream
            InputStream is = conn.getInputStream();
            // parsed Pictures
            bitmap = BitmapFactory.decodeStream(is);
            // Close the stream
            is.close();




        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;

    }
}