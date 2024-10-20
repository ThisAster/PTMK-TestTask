package com.example.demo.app.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "employee")
public class EmployeeProperties {

    private List<String> maleFirstNames;
    private List<String> femaleFirstNames;
    private List<String> maleLastNames;
    private List<String> femaleLastNames;
    private List<String> lastNamesStartingWithF;
    private List<String> maleMiddleNames;
    private List<String> femaleMiddleNames;

}
