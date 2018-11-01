import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * @Author: lemon
 * @Date: 2018/10/30 2:44 PM
 */

public class SingleConsumer extends Thread {
    private static Logger logger = LoggerFactory.getLogger(SingleConsumer.class);
    public final KafkaConsumer<Integer, String> consumer;

    public SingleConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "collect_consumer");
        //是否后台自动提交offset 到kafka
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //消费者偏移自动提交到Kafka的频率（以毫秒为单位enable.auto.commit）设置为true
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //故障检测，心跳检测机制 的间隔时间，，在该值范围内，没有接收到心跳，则会删除该消费者
        //并启动再平衡（rebanlance）,值必须在group.min.session.timeout 和 group.max.session.timeout.ms之间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        //key - value 的序列化类
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);

    }

    @Override
    public void run() {
        System.out.println("ConsumerThread--run");
        consumer.subscribe(Collections.singleton(("test1030")));
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(200);
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.println("Received message: " + record.value() + " topic: " + record.topic() + " offset" + record.offset() + " partition" + record.partition());

            }
        }
    }

    public static void main(String[] args) {
        SingleConsumer singleConsumer = new SingleConsumer();
        singleConsumer.start();
    }
}
