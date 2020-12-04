package com.yangc.IOTest;

import java.io.*;

/**
 * 字节流在操作时本身不会用到缓冲区（内存），是文件本身直接操作的，而字符流在操作时使用了缓冲区，通过缓冲区再操作文件
 */
public class StreamAndChar {

    public static void stream() throws IOException {
        // 第1步：使用File类找到一个文件
        File file = new File("d:" + File.separator + "testxx.txt"); // 声明File对象
        // 第2步：通过子类实例化父类对象
        OutputStream out = null;
        // 准备好一个输出的对象
        out = new FileOutputStream(file);
        // 通过对象多态性进行实例化
        // 第3步：准备一个字符串
        String str = "Hello World!!!";
        // 字符串转byte数组
        byte b[] = str.getBytes();
        // 将内容输出,进行写操作
        out.write(b);
        // 第4步：关闭输出流(此时没有关闭)
        // out.close();
    }

    public static void charac() throws IOException {
        // 第1步：使用File类找到一个文件
        File f = new File("d:" + File.separator + "test.txt");// 声明File 对象
        // 第2步：准备好一个输出的对象 通过子类实例化父类对象
        Writer out = null;
        // 通过对象多态性进行实例化
        out = new FileWriter(f);
        // 第3步：准备一个字符串
        String str = "Hello World!!!";
        // 进行写操作
        out.write(str);
        // 将内容输出
        // 强制性清空缓冲区中的内容
        //out.flush();

        // 第4步：关闭输出流(此时没有关闭)
        // out.close();
    }


    public static void main(String[] args) {
        try {
            stream();
//            charac();

            System.out.println("end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
