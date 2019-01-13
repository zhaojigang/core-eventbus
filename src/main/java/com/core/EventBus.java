package com.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 事件总线
 */
public class EventBus {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBus.class);
    /**
     * 事件订阅器集合
     * key: Class<? extends Event> - 事件类型
     * value: CopyOnWriteArrayList<Subscriber> - 事件订阅器
     */
    private static final Map<Class<? extends Event>, CopyOnWriteArrayList<Subscriber>> EVENT_SUBSCRIBERS = new ConcurrentHashMap<>();

    /**
     * 注册事件类型和处理器
     *
     * @param eventClass 事件类型
     * @param subscriber 事件订阅器
     */
    public static void register(Class<? extends Event> eventClass, Subscriber subscriber) {
        CopyOnWriteArrayList<Subscriber> subscribers = EVENT_SUBSCRIBERS.get(eventClass);
        if (subscribers == null) {
            subscribers = new CopyOnWriteArrayList<>();
            EVENT_SUBSCRIBERS.putIfAbsent(eventClass, subscribers);
        }
        EVENT_SUBSCRIBERS.get(eventClass).addIfAbsent(subscriber);
    }

    /**
     * 反注册事件类型和处理器
     *
     * @param eventClass 事件类型
     * @param subscriber 事件订阅器
     */
    public static void unRegister(Class<? extends Event> eventClass, Subscriber subscriber) {
        CopyOnWriteArrayList<Subscriber> subscribers = EVENT_SUBSCRIBERS.get(eventClass);
        if (subscribers == null) {
            return;
        }
        subscribers.remove(subscriber);
    }

    /**
     * 发布事件
     * 事件总线调用订阅器依次对发布的事件进行处理
     *
     * @param event 具体事件
     */
    public static void post(Event event) {
        CopyOnWriteArrayList<Subscriber> subscribers = EVENT_SUBSCRIBERS.get(event.getClass());
        if (subscribers == null) {
            return;
        }
        subscribers.forEach(x -> {
            try {
                x.onEvent(event);
            } catch (Exception e) {
                LOGGER.error("handle event {} error, ", event.getClass(), e);
            }
        });
    }
}
