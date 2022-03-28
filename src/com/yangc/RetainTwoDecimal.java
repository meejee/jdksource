package com.yangc;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @ClassName RetainTwoDecimal
 * @Description TODO
 * @Author yc
 * @Date 2021/12/27 10:02
 * @Version 1.0
 */
public class RetainTwoDecimal {
    double retainTwoDecimal = 111231.5585;
    public void m1() {
        BigDecimal bg = new BigDecimal(retainTwoDecimal);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }
    /**
     * DecimalFormat转换最简便
     */
    public void m2() {
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(retainTwoDecimal));
    }
    /**
     * String.format打印最简便
     */
    public void m3() {
        System.out.println(String.format("%.2f", retainTwoDecimal));
    }
    public void m4() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(retainTwoDecimal));
    }
    public static void main(String[] args) {
        RetainTwoDecimal retainTwoDecimal = new RetainTwoDecimal();
        retainTwoDecimal.m1();
        retainTwoDecimal.m2();
        retainTwoDecimal.m3();
        retainTwoDecimal.m4();
    }
}
