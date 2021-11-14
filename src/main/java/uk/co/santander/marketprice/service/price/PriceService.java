package uk.co.santander.marketprice.service.price;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uk.co.santander.marketprice.model.PriceFeed;
import uk.co.santander.marketprice.repository.PriceFeedRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceService {

    @NonNull
    private final PriceFeedRepository priceFeedRepository;

    public PriceFeed findById(String instrumentName){
        return priceFeedRepository.findById(instrumentName).orElseThrow(() -> new ResourceNotFoundException(instrumentName));
    }
}
