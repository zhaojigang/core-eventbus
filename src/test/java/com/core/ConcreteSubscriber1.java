package com.core;

public class ConcreteSubscriber1 implements Subscriber {
    @Override
    public void onEvent(Event event) {
        if (event instanceof ConcreteEvent1) {
            ((ConcreteEvent1) event).sayHello();
        }
    }
}
