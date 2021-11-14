package uk.co.santander.marketprice.service.csv;

import com.github.javafaker.Faker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uk.co.santander.marketprice.model.PriceFeed;

import java.util.Date;


@Slf4j
@Service
@RequiredArgsConstructor
public class FeedGenerator {

    @NonNull
    private Faker faker;

    public PriceFeed generatePriceFeed() {

        double rate1 = generateRate();
        double rate2 = generateRate();

        return PriceFeed.builder()
                .id(faker.random().nextLong())
                .instrumentName(generateInstrumentName())
                .bid(Math.min(rate1, rate2))
                .ask(Math.max(rate1, rate2))
                .timestamp(new Date())
                .build();
    }

    public String generateInstrumentName() {

        String firstCurrencyCode = faker.currency().code();
        String secondCurrencyCode = faker.currency().code();

        while (StringUtils.equals(firstCurrencyCode, secondCurrencyCode)) {
            secondCurrencyCode = faker.currency().code();
        }
        return firstCurrencyCode.concat("/").concat(secondCurrencyCode);
    }

    public Double generateRate(){

        Double rate = faker.number().randomDouble(4, 0, 1000);

        while (rate.equals(0)) {
            rate = faker.number().randomDouble(4, 0, 1000);
        }
        return rate;
    }

}
