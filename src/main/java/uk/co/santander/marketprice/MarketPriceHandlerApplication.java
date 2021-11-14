package uk.co.santander.marketprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import uk.co.santander.marketprice.resolver.KafkaResolver;

@EnableScheduling
@SpringBootApplication
@EnableRedisRepositories
@EnableConfigurationProperties({KafkaResolver.class})
public class MarketPriceHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketPriceHandlerApplication.class, args);
    }

}
