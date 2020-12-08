package com.yangc;


import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class AppMain {

    public static User getUser() {
        User user = new User();
        try {
            user.setId(1);
            user.setName("yc");

            return user;
        } finally {
            user.setId(2);
            user.setName("yangc");
        }
    }

    public static void main(String[] args) throws IOException {
        User user = AppMain.getUser();

//        String s = new String("18888") + new String("1");
//        s.intern();
//        String s2 = "188881";
//        System.out.println(s == s2);

        String s = new StringBuilder("18888").append("1").toString();
        s.intern();
        String s2 = "188881";
        System.out.println(s == s2);


        System.out.println(user.toString());
        // 普通内部类不能这样使用 需要在InnerTest中使用
        // InnerTest.Inner inner = new InnerTest.Inner();
        // 静态内部内可以在其它类中这样使用
        InnerTest.InnerStatic innerStatic = new InnerTest.InnerStatic();
        innerStatic.testInnerStatic();

        float f = (float) 3.4;
        System.out.println(f);

        short s1 = 1;
        // 隐式转换
        s1 += 1;
        // 需要强转
        // s1 = (short)(s1 +1);



    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try
    {
        System.out.println(br.readLine());
    } catch(IOException e)
    {
        e.printStackTrace();
    }
}
}


class InnerTest {
    private Integer mi = 1;

    public static User getUser() {
        User user = new User();
        try {
            user.setId(1);
            user.setName("yc");

            return user;
        } finally {
            user.setId(2);
            user.setName("yangc");
        }
    }

    public class Inner {
        public void testInner() {
            System.out.println("testInner:" + mi);
            getUser();
        }
    }

    public static class InnerStatic {
        public void testInnerStatic() {
            System.out.println("testInnerStatic:");
        }
    }
}
