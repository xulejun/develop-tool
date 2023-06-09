package com.legend.mq.rabbitmq.client.direct;

import com.legend.mq.rabbitmq.client.RabbitMqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

import static com.legend.mq.rabbitmq.client.direct.DirectProducer.*;


/**
 * Routing之订阅模型-Direct直连-消费者1
 *
 * @author xlj
 * @date 2020/11/29 16:27
 */
public class DirectConsumer1 {
    public static void main(String[] args) throws Exception {
        // 创建连接
        Connection connection = RabbitMqUtil.connectFactory();
        // 获取通道
        Channel channel = connection.createChannel();

        // 声明交换机
        channel.exchangeDeclare(DIRECT_EXCHANGE, "direct");
        // 获取临时队列
        String queue = channel.queueDeclare().getQueue();
        // 绑定交换机和路由键    1.队列 2.交换机名 3.路由键
        channel.queueBind(queue, DIRECT_EXCHANGE, ROUTING_KEY_ERROR);

        // 消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Direct直连-消费者1\t" + new String(body));
            }
        });
    }
}
