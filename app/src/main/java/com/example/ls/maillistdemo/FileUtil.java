package com.example.ls.maillistdemo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by LS on 2018/1/9.
 */

public class FileUtil {


    /**
     * 从assets中读取txt
     */
    public static String readFromAssets(Context context, String fileName) {
        String text = "";
        synchronized (FileUtil.class) {
            try {
                InputStream is = context.getAssets().open(fileName);
                text = readTextFromSDcard(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return text;
        }
    }

    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

}