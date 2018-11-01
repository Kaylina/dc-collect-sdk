package com.bingo.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * @Author: lemon
 * @Date: 2018/10/30 11:19 AM
 * 多消费者,多个work线程,难保证分区消息消费的顺序性
 */

public class ConsumerThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ConsumerThread.class);

    private ConsumerRecords<String, String> records;
    private KafkaConsumer<String, String> consumer;

    public ConsumerThread(ConsumerRecords<String, String> records,
                          KafkaConsumer<String, String> consumer) {
        this.records = records;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String, String>> partitionRecords = records
                    .records(partition);
            for (ConsumerRecord<String, String> record : partitionRecords) {
                logger.info("当前线程:" + Thread.currentThread() + ","
                        + "偏移量:" + record.offset() + "," + "主题:"
                        + record.topic() + "," + "分区:" + record.partition()
                        + "," + "获取的消息:" + record.value());
                try {
                    LogHandle.analyzeLog(record.value());
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            // 消费者自己手动提交消费的offest,确保消息正确处理后再提交
            long lastOffset = partitionRecords.get(partitionRecords.size() - 1)
                    .offset();
            consumer.commitSync(Collections.singletonMap(partition,
                    new OffsetAndMetadata(lastOffset + 1)));
        }
    }

}
