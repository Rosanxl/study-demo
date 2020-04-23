package com.xue.studydemo.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Sender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 简单队列：一对一
     */
    public void send_hello(){
        String context = "hello " + LocalDateTime.now();
        System.out.println("Sender : " + context);
        amqpTemplate.convertAndSend("hello", context);
    }
    /**
     * work模式：一对多
     */
    public void send_work() throws InterruptedException {
        String context = "这是work模式";
        System.out.println("Sender : " + context);
        for (int i = 0; i < 10; i++) {
            amqpTemplate.convertAndSend("queue_work", context+i);
        }
        Thread.sleep(5000);
    }

    /**
     * 发布订阅模式
     */
    public void send_sub() throws InterruptedException {
        String context = "这是发布订阅模式";
        System.out.println("Sender : " + context);
        for (int i = 0; i < 10; i++) {
            //第二个参数要写
            amqpTemplate.convertAndSend("exchange_sub", "",context+i);
        }
        Thread.sleep(5000);
    }

    /**
     * 路由模式
     */
    public void send_rout() throws InterruptedException {
        String context = "这是路由模式";
        System.out.println("Sender : " + context);
        for (int i = 0; i < 10; i++) {
            //第二个参数要写
            amqpTemplate.convertAndSend("exchange_direct", "direct",context+i);
        }
        Thread.sleep(5000);
    }
    /**
     * 主题模式
     */
    public void send_topic() throws InterruptedException {
        String context = "这是主题模式";
        System.out.println("Sender : " + context);

        amqpTemplate.convertAndSend("exchange_topic", "person.insert", "增加人员");
        amqpTemplate.convertAndSend("exchange_topic", "person.delete", "删除人员");
        amqpTemplate.convertAndSend("exchange_topic", "money.insert", "加钱");
        amqpTemplate.convertAndSend("exchange_topic", "money.delete", "减钱");

        Thread.sleep(5000);
    }


}
