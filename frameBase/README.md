# SpringCloudBase
2022.08.31（RabbitMQ）
<br/>1、延迟队列两种方式TTL+DXL   rabbitmq_delayed_message_exchange插件
<br/>2、消息队列手动确认Ack
<br/>3、rabbitmq 配置 .yml
<br/>4、延迟队列Config配置类=>声明交换机、队列、路由，交换机与队列绑定
<br/>2022.11.11
<br/>1、网关动态路由+配置调整

<br/>==========================<br/>
2022.11.24（oauth）
<br/>==========================<br/>
2023.01.22（RabbitMQ-consumer/producer）
<br/>==========================<br/>
2023.01.29（自定义声明交换机 new DirectExchange() 声明队列QueueBuilder）
<br/>==========================<br/>
2023.01.30（队列和交换机绑定）
<br/>==========================<br/>
2023.02.02
<br/>1、消息手动ack的yaml配置
<br/>2、RabbitMQ提供设置Ack的方法 channel.basicAck(deliveryTag, multiple); channel.basicNack(deliveryTag, multiple, requeue);channel.basicReject(deliveryTag, requeue);
<br/>3、消息重试机制: 消息消费异常后，通过重试机制，当重试次数超过规定的值时推入死信队列，处理死信队列进行补偿 (如何既保证重试又能不丢失消息)
<br/>4、https://www.yuque.com/qianxinxing/ga4iqn/xkgrkyhve3dr4vxw
<br/>==========================<br/>
2023.02.14
<br/>1、rabbitmq 消息发布确认和消息回退
<br/>2、rabbitmq 备份交换机（备份对列+报警队列）
<br/>==========================<br/>
2023.02.15
<br/>1、Spring Cloud Stream 方式的消息驱动 【rabbit2 demo 项目】
<br/>2、Spring Cloud Stream 消费失败后的处理策略