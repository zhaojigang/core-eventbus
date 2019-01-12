package com.core;

public class ConcreteEvent1 implements Event {
    private String name;

    public ConcreteEvent1(String name) {
        this.name = name;
    }

    public void sayHello() {
        System.out.println("hi, " + name);
    }
}
