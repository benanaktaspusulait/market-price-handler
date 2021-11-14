package uk.co.santander.marketprice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.repository.PriceFeedRepository;
import uk.co.santander.marketprice.resolver.KafkaResolver;
import uk.co.santander.marketprice.service.csv.CsvGenerator;
import uk.co.santander.marketprice.service.csv.FeedGenerator;
import uk.co.santander.marketprice.service.mq.consumer.PriceConsumer;
import uk.co.santander.marketprice.service.mq.producer.PriceProducer;
import uk.co.santander.marketprice.service.price.PriceCalculateService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RequiredArgsConstructor
@SpringBootTest
public class PriceControllerTestJava {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private CsvGenerator csvGenerator;

    @Autowired
    private FeedGenerator feedGenerator;

    @Autowired
    private PriceProducer priceProducer;

    @Autowired
    private KafkaResolver kafkaResolver;

    @Autowired
    private PriceConsumer priceConsumer;

    private MockMvc mockMvc;

    @Autowired
    private PriceFeedRepository priceFeedRepository;

    @Autowired
    private PriceCalculateService priceCalculateService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
    }

    @Test
    public void getByInstrumentName() throws Exception {

        String instrumentName = feedGenerator.generateInstrumentName();
        priceFeedRepository.deleteAll();

        MockHttpServletResponse instrumentNameResponse =
                mockMvc.perform(get("/api/v1/prices/getByInstrumentName?instrumentName={instrumentName}", instrumentName))
                        .andDo(print()).andExpect(status().isOk())
                        .andExpect(content().contentType("application/json"))
                        .andExpect(jsonPath("$.status").value("false"))
                        .andExpect(jsonPath("$.exceptionMessage").value("Could not find price feed :" + instrumentName))
                        .andExpect(jsonPath("$.message").value("Success"))
                        .andReturn().getResponse();

        log.error(instrumentNameResponse.toString());
    }

    @Test
    public void integrationTest() throws Exception {

        PriceFeed priceFeed = feedGenerator.generatePriceFeed();
        String csv = csvGenerator.generateCSV(priceFeed);

        priceProducer.sendMessage(kafkaResolver.getTopic(), csv);
        priceConsumer.onMessage(csv);

        PriceFeed expectedPriceFeed = priceCalculateService.calculate(priceFeed);

                MockHttpServletResponse instrumentNameResponse =
                mockMvc.perform(get("/api/v1/prices/getByInstrumentName?instrumentName={instrumentName}", priceFeed.getInstrumentName()))
                        .andDo(print()).andExpect(status().isOk())
                        .andExpect(content().contentType("application/json"))
                        .andExpect(jsonPath("$.status").value("true"))
                        .andExpect(jsonPath("$.resultObject.ask").value(expectedPriceFeed.getAsk()))
                        .andExpect(jsonPath("$.resultObject.bid").value(expectedPriceFeed.getBid()))
                        .andExpect(jsonPath("$.resultObject.id").value(expectedPriceFeed.getId()))
                        .andExpect(jsonPath("$.resultObject.instrumentName").value(expectedPriceFeed.getInstrumentName()))

                        .andReturn().getResponse();

        log.error(instrumentNameResponse.toString());
    }
}
