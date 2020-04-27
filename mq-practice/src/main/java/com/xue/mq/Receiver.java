package com.xue.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    //------------------------------HelloWorld-----------------------------------
//    @RabbitHandler
//    @RabbitListener(queues = "hello")
    @RabbitListener(queuesToDeclare = @Queue("hello"))
    public void process1(String msg) {
        System.out.println("简单队列收到消息 : " + msg);
    }

    //------------------------------Work-----------------------------------
    /**
     * 一对多：模拟两个用户接收
     * */
    @RabbitListener(queuesToDeclare = @Queue("queue_work"))
    public void process2(String msg) {
        System.out.println("Receiver_work1 : " + msg);
    }
    @RabbitListener(queues = "queue_work")
    public void process3(String msg) {
        System.out.println("Receiver_work2 : " + msg);
    }

    //-------------------------发布订阅--------------------------------------
    // 队列1
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue1_sub", durable = "true"),
            exchange = @Exchange(
                    value = "exchange_sub",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.FANOUT
            )
    ))
    public void process4(String msg) {
        System.out.println("订阅模式1 接收到消息：" + msg);
    }

    // 队列2（第二个人），同样能接收到消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue2_sub", durable = "true"),
            exchange = @Exchange(
                    value = "exchange_sub",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.FANOUT
            )
    ))
    public void process5(String msg) {
        System.out.println("订阅模式2 接收到消息：" + msg);
    }

    //-------------------------路由模式--------------------------------------
    // 队列1
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue1_direct", durable = "true"),
            exchange = @Exchange(
                    value = "exchange_direct",
                    ignoreDeclarationExceptions = "true"
            ),
            key = {"direct"}
    ))
    public void process6(String msg) {
        System.out.println("路由模式1 接收到消息：" + msg);
    }

    // 队列2 key值不同，接收不到消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue2_direct", durable = "true"),
            exchange = @Exchange(
                    value = "exchange_direct",
                    ignoreDeclarationExceptions = "true"
            ),
            key = {"direct_test"}
    ))
    public void process7(String msg) {
        System.out.println("路由模式2 接收到消息：" + msg);
    }


    //-------------------------topic模式--------------------------------------------
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_topic1", durable = "true"),
            exchange = @Exchange(
                    value = "exchange_topic",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"person.*"}
    ))
    public void process8(String msg) {
        System.out.println("person 接收到消息：" + msg);
    }

    // 通配规则不同，接收不到消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue_topic2", durable = "true"),
            exchange = @Exchange(
                    value = "exchange_topic",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"money.*"}
    ))
    public void process9(String msg) {
        System.out.println("money 接收到消息：" + msg);
    }


}
