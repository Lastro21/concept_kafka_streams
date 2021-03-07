package com.example.demo_kafka_stream;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class DemoKafkaStreamApplication {

    public static void main(String[] args) throws InterruptedException {

        SpringApplication.run(DemoKafkaStreamApplication.class, args);

        Properties props = new Properties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "hello");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        StreamsConfig streamsConfig = new StreamsConfig(props);

        Serde<String> stringSerde = Serdes.String();

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> simpleFirstStream = builder.stream("src-topic", Consumed.with(stringSerde, stringSerde));

        KStream<String, String> upperCaseStream = simpleFirstStream.mapValues(x -> x.toUpperCase() + " !!!!!!!!! added info");

        upperCaseStream.to("out-topic", Produced.with(stringSerde, stringSerde));
        upperCaseStream.to("out-topic-second", Produced.with(stringSerde, stringSerde));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);

        kafkaStreams.start();

        Thread.sleep(10000);

        kafkaStreams.close();

    }

}
