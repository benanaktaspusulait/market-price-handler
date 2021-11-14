package uk.co.santander.marketprice.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import uk.co.santander.marketprice.model.PriceFeed;

@Repository
public interface PriceFeedRepository extends KeyValueRepository<PriceFeed, String> {
}
