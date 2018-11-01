package com.bingo.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lemon
 * @Date: 2018/10/25 11:53 AM
 */

public class MyConsumer {

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    private static final String TOPIC = "test1030";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "consumer-test";
    public final KafkaConsumer<String, String> consumer;
    private ExecutorService executorService;

    public MyConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        //group id : producer-consumer-demo1
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        //是否后台自动提交offset 到kafka
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        //消费者偏移自动提交到Kafka的频率（以毫秒为单位enable.auto.commit）设置为true
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //故障检测，心跳检测机制 的间隔时间，，在该值范围内，没有接收到心跳，则会删除该消费者
        //并启动再平衡（rebanlance）,值必须在group.min.session.timeout 和 group.max.session.timeout.ms之间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //key - value 的序列化类
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(TOPIC));
    }

    public void execute() {
        executorService = Executors.newFixedThreadPool(3);
        logger.info("ConsumerThread------run-----");
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            if (null != records) {
                executorService.submit(new ConsumerThread(records, consumer));
            }
        }
    }

    public void shutdown() {
        try {
            if (consumer != null) {
                consumer.close();
            }
            if (executorService != null) {
                executorService.shutdown();
            }
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                logger.info("Timeout");
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }


    public static void handle() {
        MyConsumer myConsumer = new MyConsumer();
        try {
            myConsumer.execute();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            myConsumer.shutdown();
        }

    }
}
