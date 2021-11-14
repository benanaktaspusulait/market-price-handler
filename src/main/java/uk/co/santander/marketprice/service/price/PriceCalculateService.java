package uk.co.santander.marketprice.service.price;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.co.santander.marketprice.model.PriceFeed;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class PriceCalculateService {

    @Value("${price.commission.rate.bid}")
    private Double bidRate;

    @Value("${price.commission.rate.ask}")
    private Double askRate;

    public PriceFeed calculate(PriceFeed priceFeedPojo) {

        priceFeedPojo.setAsk(calculate(priceFeedPojo.getAsk(), askRate));
        priceFeedPojo.setBid(calculate(priceFeedPojo.getBid(), bidRate));

        return priceFeedPojo;
    }

    private Double calculate(Double rate, Double percentage) {
        return new BigDecimal(rate + (rate * percentage)).setScale(4, RoundingMode.HALF_EVEN).doubleValue();
    }

}
