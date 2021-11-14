package uk.co.santander.marketprice.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import uk.co.santander.marketprice.resolver.KafkaResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private @NonNull KafkaResolver kafkaResolver;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaResolver.getBootstrapAddress());
        return new KafkaAdmin(configs);
    }

}

