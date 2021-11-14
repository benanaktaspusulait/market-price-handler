package uk.co.santander.marketprice.service.mq.consumer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.repository.PriceFeedRepository;
import uk.co.santander.marketprice.service.csv.CsvParseService;
import uk.co.santander.marketprice.service.price.PriceCalculateService;

import java.text.ParseException;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceConsumer {

    @NonNull private CsvParseService csvParseService;
    @NonNull private PriceCalculateService priceCalculateService;
    @NonNull private PriceFeedRepository priceFeedRepository;

    @KafkaListener(topics = "${kafka.topic}", groupId = "${kafka.groupId}")
    public void onMessage(String priceFeedCSV) throws ParseException {

        log.debug("Received Message in group priceFeed: {}", priceFeedCSV);

        PriceFeed priceFeed = csvParseService.readCSV(priceFeedCSV);

        ofNullable(priceFeed)
                .map(priceCalculateService::calculate)
                .map(priceFeedRepository::save)
                .map(PriceFeed::toString)
                .ifPresent(log::debug);
    }
}
