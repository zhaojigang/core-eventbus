package com.core;

/**
 * 事件订阅器（即事件的真正处理器）
 */
public interface Subscriber {
    /**
     * 真正的处理事件的逻辑
     *
     * @param event 具体事件
     */
    void onEvent(Event event);
}
