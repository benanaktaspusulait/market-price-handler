package uk.co.santander.marketprice.service.csv;

import de.siegmar.fastcsv.reader.CommentStrategy;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.util.DateUtil;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Iterator;

@Data
@Slf4j
@Component
public class CsvParseService {

    CsvReader.CsvReaderBuilder csvReaderBuilder;

    @PostConstruct
    public void init() {
        csvReaderBuilder = CsvReader.builder()
                .fieldSeparator(',')
                .quoteCharacter('"')
                .commentStrategy(CommentStrategy.SKIP)
                .commentCharacter('#')
                .skipEmptyRows(true)
                .errorOnDifferentFieldCount(false);
    }

    public PriceFeed readCSV(String text) throws ParseException {

        Iterator<CsvRow> csvRowIterator = csvReaderBuilder.build(text).stream().iterator();
        CsvRow csvRow = csvRowIterator.next();

        return PriceFeed.builder()
                .id(Long.valueOf(csvRow.getField(0)))
                .instrumentName(csvRow.getField(1))
                .bid(Double.valueOf(csvRow.getField(2)))
                .ask(Double.valueOf(csvRow.getField(3)))
                .timestamp(DateUtil.convertToDate(csvRow.getField(4), DateUtil.DATE_FORMAT))
                .build();

    }
}
