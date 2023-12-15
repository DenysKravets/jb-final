package com.example.jbfinal;

import com.example.jbfinal.cleaner.DatabaseCleanerExtension;
import com.example.jbfinal.dataservice.ConvictService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(DatabaseCleanerExtension.class)
class JbFinalApplicationTests {

    @Autowired
    private ConvictService convictService;

    @Test
    void contextLoads() {

        int size = convictService.getConvicts("").size();
        assertThat(size).isEqualTo(0);

        convictService.createConvict(
                "Jack",
                "Smoothie",
                "Fang of smoldering wind",
                1.0,
                "blond",
                "amber",
                "missing 3 fingers on left hand",
                "China",
                "",
                "",
                "Chinese, English",
                "criminal",
                "doing bad stuff",
                "jackson street boys"
        );

        size = convictService.getConvicts("").size();
        assertThat(size).isEqualTo(1);

    }

    @Test
    void contextLoads2() {

        int size = convictService.getConvicts("").size();
        assertThat(size).isEqualTo(0);

        convictService.createConvict(
                "Jack",
                "Smoothie",
                "Fang of smoldering wind",
                1.0,
                "blond",
                "amber",
                "missing 3 fingers on left hand",
                "China",
                "",
                "",
                "Chinese, English",
                "criminal",
                "doing bad stuff",
                "jackson street boys"
        );

        size = convictService.getConvicts("").size();
        assertThat(size).isEqualTo(1);

    }

}
