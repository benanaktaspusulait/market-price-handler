package uk.co.santander.marketprice.service;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.santander.marketprice.service.csv.FeedGenerator;
import uk.co.santander.marketprice.service.price.PriceService;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PriceServiceTest {

    @Autowired
    private FeedGenerator feedGenerator;

    @Autowired
    private PriceService priceService;

    @BeforeEach
    void setup() {
    }

    @Test
    public void testFindById() {

        String instrumentName = feedGenerator.generateInstrumentName();
        assertThrows(ResourceNotFoundException.class, () -> priceService.findById(instrumentName));
    }


}
