package uk.co.santander.marketprice.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.santander.marketprice.MarketPriceHandlerApplication;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.service.csv.CsvGenerator;
import uk.co.santander.marketprice.service.csv.CsvParseService;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest(classes = MarketPriceHandlerApplication.class)
public class CsvParseServiceTest {

    @Autowired
    CsvParseService csvParseService;

    @Autowired
    CsvGenerator csvGenerator;

    @Test
    public void testReadCSV() throws ParseException {

        Long id = 1L;
        String instrumentName = "USD/EUR";
        Double bid = Double.valueOf("1.15");
        Double ask = Double.valueOf("1.27");
        Date timestamp = new Date();

        PriceFeed priceFeed = PriceFeed.builder()
                .id(id)
                .instrumentName(instrumentName)
                .bid(bid)
                .ask(ask)
                .timestamp(timestamp)
                .build();

        String csv = csvGenerator.generateCSV(priceFeed);

        PriceFeed priceFeedRead = csvParseService.readCSV(csv);

        assertEquals(priceFeed.getAsk(), priceFeedRead.getAsk());
        assertEquals(priceFeed.getBid(), priceFeedRead.getBid());
        assertEquals(priceFeed.getId(), priceFeedRead.getId());
        assertEquals(priceFeed.getInstrumentName(), priceFeedRead.getInstrumentName());
        assertEquals(priceFeed.getTimestamp(), priceFeedRead.getTimestamp());

        assertEquals(priceFeedRead.getAsk(), ask);
        assertEquals(priceFeedRead.getBid(), bid);
        assertEquals(priceFeedRead.getId(), id);
        assertEquals(priceFeedRead.getInstrumentName(), instrumentName);
        assertEquals(priceFeedRead.getTimestamp(), timestamp);

    }

    @Test
    public void testReadCSVException() {

        Long id = 1L;
        String instrumentName = "USD/GBP";
        Double bid = Double.valueOf("1.35");
        Double ask = Double.valueOf("1.40");

        PriceFeed priceFeed = PriceFeed.builder()
                .id(id)
                .instrumentName(instrumentName)
                .bid(bid)
                .ask(ask)
                .timestamp(null)
                .build();

        String csv = csvGenerator.generateCSV(priceFeed);

        assertThrows(ParseException.class, () -> csvParseService.readCSV(csv));
    }


}
