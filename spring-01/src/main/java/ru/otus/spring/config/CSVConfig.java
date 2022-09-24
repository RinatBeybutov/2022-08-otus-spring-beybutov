package ru.otus.spring.config;

public class CSVConfig {

  private final String csvPath;

  public CSVConfig(String csvPath) {
    this.csvPath = csvPath;
  }

  public String getCsvPath() {
    return csvPath;
  }
}
