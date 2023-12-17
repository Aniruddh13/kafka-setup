package com.myapp.kafka;

import com.myapp.kafka.schemaregistry.Product;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

//@SpringBootApplication
public class KafkaAvroDemo1Application {

	public static void main(String[] args) {
//		SpringApplication.run(KafkaAvroDemo1Application.class, args);
		Properties props = new Properties();
		props.put("bootstrap.servers","127.0.0.1:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
		props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,"http://127.0.0.1:8081");
		props.put(ProducerConfig.ACKS_CONFIG,"all");
		props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true");
		//props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,"32mb");
		props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,"70000");
		props.put(ProducerConfig.LINGER_MS_CONFIG,"3000");
		props.put(ProducerConfig.RETRIES_CONFIG,Integer.toString(5));
		props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,"1");
		Producer<String, Product> producer = new KafkaProducer<>(props);
		Product product = new Product(12345,"Iphone15",85456.5);
		producer.send(new ProducerRecord<>("products",product.getId().toString(),product));





	}

}
