package ru.voronkovmm.dollar.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.voronkovmm.dollar.pojo.JustTestClass;

/**
 * В этом классе конфигурируется beans {@param objectMapper}, {@param justTestClass}
 * для работы с данными JSON в классе {@link ru.voronkovmm.dollar.controller.MyController} и тестирования
 *
 * author - Voronkov M.M.
 * @version 1.0
 */
@Configuration
public class BeanConfiguration {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    JustTestClass justTestClass() {
        return new JustTestClass();
    }
}
