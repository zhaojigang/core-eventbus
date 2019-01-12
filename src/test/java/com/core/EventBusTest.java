package com.core;

import org.testng.annotations.Test;

public class EventBusTest {
    @Test
    public void testMainFunc() {
        // 1. 注册 Event 及其 Subscriber
        EventBus.register(ConcreteEvent1.class, new ConcreteSubscriber1());
        // 2. 发布 Event 到 EventBus
        EventBus.post(new ConcreteEvent1("lili"));
    }
}
