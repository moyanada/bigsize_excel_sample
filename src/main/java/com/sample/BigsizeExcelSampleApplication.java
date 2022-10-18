package com.sample;

import com.sample.batch.main.TestBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;


@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages={"com.sample.*"})
@RequiredArgsConstructor
public class BigsizeExcelSampleApplication {

    private final TestBatch testBatch;

    public static void main(String[] args) {
        SpringApplication.run(BigsizeExcelSampleApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            testBatch.doProcessOOM_for();
//            testBatch.doProcess();
//            testBatch.doSaveData();
            System.exit(0);
        };
    }

}
