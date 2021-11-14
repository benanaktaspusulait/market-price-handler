package uk.co.santander.marketprice.service.mq.producer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.co.santander.marketprice.resolver.KafkaResolver;
import uk.co.santander.marketprice.service.csv.CsvGenerator;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceProducer {

    private @NonNull KafkaResolver kafkaResolver;
    private @NonNull CsvGenerator csvGenerator;
    private @NonNull KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 10)
    public void sendMessage() {

        String message = csvGenerator.generateCSV();
        log.info("sending payload='{}' to topic='{}'", message, kafkaResolver.getTopic());
        sendMessage(kafkaResolver.getTopic(), message);
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }


}