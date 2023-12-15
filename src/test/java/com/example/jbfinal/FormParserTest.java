package com.example.jbfinal;

import com.example.jbfinal.cleaner.DatabaseCleanerExtension;
import com.example.jbfinal.dataservice.ConvictService;
import com.example.jbfinal.parser.FormParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class FormParserTest {

    @Autowired
    private FormParser formParser;

    @Test
    public void test() {

    }

    @Test
    public void testEmptyString() {

        int actualValue = formParser.parseToMap("").size();
        int expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void testEmptyInput() {

        int actualValue = formParser.parseToMap("name=").size();
        int expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void testMultipleEmptyInput() {

        int actualValue = formParser.parseToMap("name=&surname=").size();
        int expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void testFilter() {

        String actualValue = null;
        String expectedValue = null;

        Map<String, String> map = formParser.parseToMap("name=  ++john ");
        actualValue = map.get("name");
        expectedValue = "john";

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void testFilterMultiple() {

        String actualValue = null;
        String expectedValue = null;

        Map<String, String> map = formParser.parseToMap("name=  ++john &surname=  ++johnson ++");
        actualValue = map.get("name");
        expectedValue = "john";

        assertThat(actualValue).isEqualTo(expectedValue);

        actualValue = map.get("surname");
        expectedValue = "johnson";

        assertThat(actualValue).isEqualTo(expectedValue);
    }

}
