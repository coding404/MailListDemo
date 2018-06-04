package com.example.ls.maillistdemo

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by LS on 2018/1/9.
 */

object FileUtil {


    /**
     * 从assets中读取txt
     */
    fun readFromAssets(context: Context, fileName: String): String {
        var text = ""
        synchronized(FileUtil::class.java) {
            try {
                val content = context.assets.open(fileName)
                text = readTextFromSDcard(content)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return text
        }
    }

    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun readTextFromSDcard(content: InputStream): String {
        val reader = InputStreamReader(content)
        var bufferedReader = BufferedReader(reader)
        val buffer = StringBuffer("")
        var str: String
        str = bufferedReader.readLine()
        try {
            while (str != null) {
                buffer.append(str)
                buffer.append("\n")
                str = bufferedReader.readLine()
            }
        } catch (e: Exception) {
        } finally {
        }
        return buffer.toString()
    }

}