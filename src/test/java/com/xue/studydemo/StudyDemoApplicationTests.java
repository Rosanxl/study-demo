package com.xue.studydemo;

import com.xue.studydemo.mq.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyDemoApplicationTests {

    @Autowired
    private Sender sender;

    /**
     * 测试简单模式
     */
    @Test
    void send1() throws InterruptedException {
        sender.send_hello();
    }
    /**
     * 测试work模式
     */
    @Test
    void send2() throws InterruptedException {
        sender.send_work();
    }

    /**
     * 测试订阅模式
     */
    @Test
    void send3() throws InterruptedException {
        sender.send_sub();
    }

    /**
     * 测试路由模式
     */
    @Test
    void send4() throws InterruptedException {
        sender.send_rout();
    }

     /**
     * 测试topic模式
     */
    @Test
    void send5() throws InterruptedException {
        sender.send_topic();
    }



}
