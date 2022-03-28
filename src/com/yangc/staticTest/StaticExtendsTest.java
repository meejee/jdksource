package com.yangc.staticTest;

/**
 * @ClassName StaticExtensTest
 * @Description TODO
 * @Author yc
 * @Date 2021/8/5 10:49
 * @Version 1.0
 */
public class StaticExtendsTest {
    public static void main(String[] args) {
        StaticC c = new StaticC();
        System.out.println(c.nonStaticStr);
        System.out.println(c.staticStr);
        c.staticMethod();//输出的结果都是父类中的非静态属性、静态属性和静态方法,推出静态属性和静态方法可以被继承

        System.out.println("-------------------------------");

        StaticA c1 = new StaticC();
        System.out.println(c1.nonStaticStr);
        System.out.println(c1.staticStr);
        c1.staticMethod();//结果同上，输出的结果都是父类中的非静态属性、静态属性和静态方法,推出静态属性和静态方法可以被继承

        System.out.println("-------------------------------");
        StaticB b = new StaticB();
        System.out.println(b.nonStaticStr);
        System.out.println(b.staticStr);
        b.staticMethod(); //B改写结果

        System.out.println("-------------------------------");
        StaticA b1 = new StaticB();
        System.out.println(b1.nonStaticStr);
        System.out.println(b1.staticStr);
        b1.staticMethod();//结果都是父类的静态方法，说明静态方法不可以被重写，不能实现多态
    }
}
