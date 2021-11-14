package uk.co.santander.marketprice.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.santander.marketprice.MarketPriceHandlerApplication;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.service.csv.FeedGenerator;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest(classes = MarketPriceHandlerApplication.class)
public class FeedGeneratorTest {

    @Autowired
    FeedGenerator feedGenerator;

    @Test
    @Order(1)
    public void testPriceFeedGenerator() {

        PriceFeed priceFeed = feedGenerator.generatePriceFeed();

        assertNotEquals(priceFeed.getBid(), 0);
        assertNotEquals(priceFeed.getAsk(), 0);
        assertNotNull(priceFeed.getAsk(), "Ask value must not be null");
        assertNotNull(priceFeed.getBid(), "Bid value must not be null");
        assertNotNull(priceFeed.getId(), "Id value must not be null");
        assertNotNull(priceFeed.getTimestamp(), "Timestamp value must not be null");
        assertNotNull(priceFeed.getInstrumentName(), "InstrumentName value must not be null");
        assertNotEquals(priceFeed.getAsk(), priceFeed.getBid());
        assertEquals(priceFeed.getAsk().compareTo(priceFeed.getBid()), 1);
        assertEquals(priceFeed.getTimestamp().compareTo(new Date()), -1);

    }


}
