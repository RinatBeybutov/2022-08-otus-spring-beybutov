package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class CSVConfig {

  private final String csvPath;

  public CSVConfig(@Value("${csvPath}") String csvPath) {
    this.csvPath = csvPath;
  }

  public String getCsvPath() {
    return csvPath;
  }
}
