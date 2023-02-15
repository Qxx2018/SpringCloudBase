package com.itheima.demo.rabbit2.exceptions;

/**
 * 自定义RabbitMQ异常
 */
public class RabbitMQException extends Exception {
    public RabbitMQException() {
        super();
    }

    public RabbitMQException(String message) {
        super(message);
    }

    public RabbitMQException(String message, Throwable cause) {
        super(message, cause);
    }

    public RabbitMQException(Throwable cause) {
        super(cause);
    }

    protected RabbitMQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
