package ru.serdyuk.diff;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.serdyuk.diff.services.DiffService;
import ru.serdyuk.diff.services.DiffServiceStandaloneImpl;

@Configuration
public class DiffConfiguration {

    @Bean
    DiffService standaloneDiffService() {
        return new DiffServiceStandaloneImpl();
    }
}
