package uk.co.santander.marketprice.config;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class JavaFakerConfig {

    @Value("application.value")
    private String locale;

    @Bean
    public Faker faker(){
        return Faker.instance(new Locale(locale));
    }
}
