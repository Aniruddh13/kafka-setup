package com.myapp.kafka;

import com.myapp.kafka.schemaregistry.Product;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class JavaKafkaAvroApplicationConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers","127.0.0.1:9092");

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"app1");
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,"http://127.0.0.1:8081");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,true);

//        props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG,"2000");
//        props.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "org.apache.kafka.clients.consumer.RangeAssignor");


        KafkaConsumer<String, Product> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("products"));

        while(true) {
            ConsumerRecords<String, Product> records = consumer.poll(Duration.ofMillis(100));
            records.forEach(record -> System.out.println(
                    "Key = " + record.key() + " , value=" + record.value() + ", topic=" + record.topic()
                            + "Partitions = " + record.partition() + ", offset=" + record.offset()
            ));
        }

//        consumer.commitAsync(new OffsetCommitCallback() {
//            @Override
//            public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {
//                if(e!=null){
//                    System.out.println("Commit failed");
//                }
//            }
//        });
//        }

    }
}

// Partition Assignment Strategy
// RoundRobin
// Range