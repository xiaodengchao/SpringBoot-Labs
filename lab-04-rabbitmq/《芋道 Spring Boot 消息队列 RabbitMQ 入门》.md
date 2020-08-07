<http://www.iocoder.cn/Spring-Boot/RabbitMQ/?github>


[RabbitMQ 基础概念详解]<http://www.iocoder.cn/RabbitMQ/Detailed-understanding-of-the-basic-concepts-of-RabbitMQ/?self>
[朱小厮的博客]<https://blog.csdn.net/u013256816>
[RabbitMQ 最佳实践]<https://www.cnblogs.com/davenkin/p/rabbitmq-best-practices.html>

rabbitmq-native: 直接通过官方java客户端连接，演示了一些基本用法

rabbitmq-demo: 使用spring-boot-starter-amqp，演示基本注解，同步发送和异步发送，
演示不同Exchange规则的Exchange创建，消息的调用

rabbitmq-demo-batch: 批量发送消息，其实rabbitmq broker没有批量接受消息的接口，
这个批量是spring-amqp做的，它在client层将多条消息封成一条并标记。

rabbitmq-demo-batch-consume: 批量消费消息，其实消费的是一条批量消息，
再根据标记做拆分后消费，依赖rabbitmq-demo-batch发送的批量消息

rabbitmq-demo-batch-consume2: 批量消费消息2不依赖rabbitmq-demo-batch发送的批量消息,
也是在client端等待，拉取到足够的消息条数再消费，用receiveTimeout控制，但是设计很神奇，
代表的是，每次拉取一条消息，最多阻塞等待 receiveTimeout 时长，也就是最多
可能等待 receiveTimeout * batchSize 时长，才会进行批量消费

rabbitmq-consume-retry: 一个 Consumer 消费重试的示例，在消息消费失败的时候，Spring-AMQP 会通过消费重试机制，重新投递该消息给 Consumer ，
让 Consumer 有机会重新消费消息，实现消费成功，当然，Spring-AMQP 并不会无限重新投递消息给 Consumer 重新消费，
而是在默认情况下，达到 N 次重试次数时，Consumer 还是消费失败时，该消息就会进入到死信队列。
后续，我们可以通过对死信队列中的消息进行重发，来使得消费者实例再次进行消费。
RabbitMQ 中，消费重试是由 Spring-AMQP 所封装提供的，死信队列是 RabbitMQ 自带的功能
另外，每条消息的失败重试，是可以配置一定的间隔时间
死信队列参考：[RabbitMQ 之死信队列]<http://www.iocoder.cn/RabbitMQ/dead-letter-queue/?self>

rabbitmq-demo-delay: 一个定时消息的示例，基于 RabbitMQ 的死信队列，实现定时消息的功能。RabbitMQ 提供了过期时间 TTL 机制，
可以设置消息在队列中的存活时长。在消息到达过期时间时，会从当前队列中删除，并被 RabbitMQ 自动转发到对应的死信队列中。
那么，如果我们创建消费者 Consumer ，来消费该死信队列，是不是就实现了延迟队列的效果。如此，便实现了定时消息的功能。
还有更好的官方提供方式，生产中最好用这种:
[Spring Boot RabbitMQ 延迟消息实现完整版]<http://www.iocoder.cn/Fight/Spring-Boot-RabbitMQ-deferred-message-implementation-full-version/?self>


rabbitmq-demo-message-model: 消息模式示例，在消息队列中，有两种经典的消息模式：「点对点」和「发布订阅」，
具体: [消息队列两种模式：点对点与发布订阅]<http://www.iocoder.cn/Fight/There-are-two-modes-of-message-queuing-point-to-point-and-publish-subscription/?self>
