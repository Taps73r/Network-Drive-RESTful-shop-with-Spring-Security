
package com.example.demo.config;

import com.example.demo.model.NetworkDriveCategory;
import com.example.demo.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoryConfig {
    @Bean
    CommandLineRunner categoryLineRunner(CategoryRepository repository) {
        return args -> {
            NetworkDriveCategory big = new NetworkDriveCategory(
                    1L,
                    "big"
            );
            NetworkDriveCategory mid = new NetworkDriveCategory(
                    2L,
                    "middle"
            );
            NetworkDriveCategory small = new NetworkDriveCategory(
                    3L,
                    "small"
            );
            repository.saveAll(
                    List.of(big, mid, small)
            );
        };
    }
}
