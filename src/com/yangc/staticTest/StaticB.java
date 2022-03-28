package com.yangc.staticTest;

/**
 * @ClassName StaticB
 * @Description TODO
 * @Author yc
 * @Date 2021/8/5 10:49
 * @Version 1.0
 */
public class StaticB extends StaticA {
    public static String staticStr = "B改写后的静态属性";
    public String nonStaticStr = "B改写后的非静态属性";
    public static void staticMethod(){
        System.out.println("B改写后的静态方法");
    }
}
