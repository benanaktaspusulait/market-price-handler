package uk.co.santander.marketprice.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.santander.marketprice.MarketPriceHandlerApplication;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.service.csv.CsvGenerator;
import uk.co.santander.marketprice.util.DateUtil;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = MarketPriceHandlerApplication.class)
public class CsvGeneratorTest {

    @Autowired
    CsvGenerator csvGenerator;

    private static final String DUMMY_DATE = "10-11-2021 10:21:45:123";

    @Test
    public void testReadCSVException() throws ParseException {

        Long id = 1L;
        String instrumentName = "USD/GBP";
        Double bid = Double.valueOf("1.35");
        Double ask = Double.valueOf("1.40");

        PriceFeed priceFeed = PriceFeed.builder()
                .id(id)
                .instrumentName(instrumentName)
                .bid(bid)
                .ask(ask)
                .timestamp(DateUtil.convertToDate(DUMMY_DATE, DateUtil.DATE_FORMAT))
                .build();

        String csv = csvGenerator.generateCSV(priceFeed);
        assertEquals(csv,"1,USD/GBP,1.35,1.4,10-11-2021 10:21:45:123");

    }

}
