package com.yangc.staticTest;

/**
 * @ClassName StaticA
 * @Description TODO
 * @Author yc
 * @Date 2021/8/5 10:49
 * @Version 1.0
 */
public class StaticA {
    public static String staticStr = "A静态属性";
    public String nonStaticStr = "A非静态属性";
    public static void staticMethod(){
        System.out.println("A静态方法");
    }
    public void nonStaticMethod(){
        System.out.println("A非静态方法");
    }
}
