package com.example.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {
    private static String TAG = "FileUtil";


    /**
     * 解压assets的zip压缩文件到指定目录
     *
     * @throws IOException
     */
    public static boolean unzipFile(String zipPtath, String outputDirectory) {
        try {
            Log.i(TAG, "开始解压的文件： " + zipPtath + "\n" + "解压的目标路径：" + outputDirectory);
            // 创建解压目标目录
            File file = new File(outputDirectory);
            // 如果目标目录不存在，则创建
            if (!file.exists()) {
                file.mkdirs();
            }
            // 打开压缩文件
            InputStream inputStream = new FileInputStream(zipPtath);
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);

            // 读取一个进入点
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            // 使用1Mbuffer
            byte[] buffer = new byte[1024 * 1024];
            // 解压时字节计数
            int count = 0;
            // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
            while (zipEntry != null) {
                Log.i(TAG, "解压文件 入口 1： " + zipEntry);
                if (!zipEntry.isDirectory()) {  //如果是一个文件
                    // 如果是文件
                    String fileName = zipEntry.getName();
                    Log.i(TAG, "解压文件 原来 文件的位置： " + fileName);
//                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);  //截取文件的名字 去掉原文件夹名字
                    Log.i(TAG, "解压文件 的名字： " + fileName);
                    file = new File(outputDirectory + File.separator + fileName);  //放到新的解压的文件路径

                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                } else {
                    File directory = new File(outputDirectory + zipEntry.getName() + File.separator);
                    directory.mkdirs();
                }
                // 定位到下一个文件入口
                zipEntry = zipInputStream.getNextEntry();
                Log.i(TAG, "解压文件 入口 2： " + zipEntry);
            }
            zipInputStream.close();
            Log.i(TAG, "解压完成");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
