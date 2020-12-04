package com.yangc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    Integer id;
    String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void testChild() {
        System.out.println("parent");
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

class Child extends User {
    public void testChild() {
        System.out.println("child");
    }

    public static void main(String[] args) {
        User child = new Child();
        child.testChild();

        ConcurrentHashMap<String,String> premiumPhone =
                new ConcurrentHashMap<String,String>();
        premiumPhone.put("Apple", "iPhone");
        premiumPhone.put("HTC", "HTC one");
        premiumPhone.put("Samsung","S5");

        Iterator iterator = premiumPhone.keySet().iterator();

        while (iterator.hasNext())
        {
            System.out.println(premiumPhone.get(iterator.next()));
            premiumPhone.put("ab", "Xperia Z1");
            premiumPhone.put("sa", "Xperia Z2");
            premiumPhone.put("xa", "Xperia Z3");
            premiumPhone.put("ma", "Xperia Z4");
            premiumPhone.put("ha", "Xperia Z5");
        }
//
//        iterator = premiumPhone.keySet().iterator();
//
//        while (iterator.hasNext())
//        {
//            System.out.println(premiumPhone.get(iterator.next()));
//        }
    }
}
