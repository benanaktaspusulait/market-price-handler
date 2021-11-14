package uk.co.santander.marketprice.service.csv;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.util.DateUtil;

@Slf4j
@Component
@RequiredArgsConstructor
public class CsvGenerator {

    @NonNull
    private FeedGenerator feedGenerator;

    private final StringBuilder stringBuilder = new StringBuilder();

    public String generateCSV() {
        PriceFeed price = feedGenerator.generatePriceFeed();
        return generateCSV(price);
    }

    public String generateCSV(PriceFeed price) {

        synchronized (this) {
            stringBuilder.delete(0, stringBuilder.length());
        }

        return stringBuilder
                .append(price.getId())
                .append(",")
                .append(price.getInstrumentName())
                .append(",")
                .append(price.getBid())
                .append(",")
                .append(price.getAsk())
                .append(",")
                .append(DateUtil.convert(price.getTimestamp(),DateUtil.DATE_FORMAT))
                .toString();
    }


}
