package uk.co.santander.marketprice.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Builder
@RedisHash
public class PriceFeed implements Serializable {

    @Id
    private String instrumentName;
    private Long id;
    private Double bid;
    private Double ask;
    private Date timestamp;
}
