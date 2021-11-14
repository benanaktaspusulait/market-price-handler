package uk.co.santander.marketprice.resolver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Data
@ConfigurationProperties("kafka")
public class KafkaResolver implements Serializable {

    private String bootstrapAddress;
    private String groupId;
    private String topic;

}
