package uk.co.santander.marketprice.service

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.context.ActiveProfiles
import spock.lang.Shared
import spock.lang.Specification
import uk.co.santander.marketprice.repository.PriceFeedRepository
import uk.co.santander.marketprice.resolver.KafkaResolver
import uk.co.santander.marketprice.service.csv.CsvGenerator
import uk.co.santander.marketprice.service.mq.producer.PriceProducer

import static java.time.Duration.ofMillis

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
class PriceFeedKafkaSpec extends Specification {

    public static final def TOPICS = ['priceFeedTest']

    @Autowired
    EmbeddedKafkaBroker kafka
    @Autowired
    KafkaResolver kafkaResolver
    @Autowired
    CsvGenerator csvGenerator
    @Autowired
    PriceProducer priceProducer
    @Autowired
    PriceFeedRepository priceFeedRepository
    @Shared
    Consumer priceDataConsumer

    def setup() {

        def configs = KafkaTestUtils.consumerProps(UUID.randomUUID().toString(), "false", kafka)
        priceDataConsumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(), new StringDeserializer()).createConsumer()
        kafka.doWithAdmin({ a -> a.deleteTopics(TOPICS) })
        kafka.doWithAdmin({ a -> a.createTopics(TOPICS.collect { TopicBuilder.name(it).build() }) })
        priceDataConsumer.subscribe([kafkaResolver.topic])
    }

    def "Price data is published into Kafka topic"() {

        priceFeedRepository.deleteAll()

        given: "There is price data update"
        final String data = csvGenerator.generateCSV()

        when: "The update is stored in repository"
        priceProducer.sendMessage(TOPICS.get(0), data)

        and: "Kafka topic is subscribed"
        def records = priceDataConsumer.poll(ofMillis(500))

        then:
        records.size() > 0
    }


}
