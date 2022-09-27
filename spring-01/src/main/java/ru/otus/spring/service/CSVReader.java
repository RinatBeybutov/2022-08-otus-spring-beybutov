package ru.otus.spring.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.spring.Main;
import ru.otus.spring.config.CSVConfig;
import ru.otus.spring.domain.Question;

public class CSVReader implements Reader{

  private final CSVConfig csvConfig;

  public CSVReader(CSVConfig csvConfig) {
    this.csvConfig = csvConfig;
  }

  @Override
  public List<Question> read() throws IOException {
    String[] lines = readCsv();
    return createQuestionsFromStrings(lines);
  }

  private List<Question> createQuestionsFromStrings(String[] lines) {
    List<Question> questions = new ArrayList<>();
    for (String line : lines) {
      String[] fields = line.split(";");
      Question question = new Question(
          Integer.parseInt(fields[0]),
          fields[1],
          new ArrayList<>(Arrays.asList(fields[2], fields[3])),
          fields[4]);
      questions.add(question);
    }
    return questions;
  }

  private String[] readCsv() throws IOException {
    byte[] bytes = Main.class.getResourceAsStream(csvConfig.getCsvPath()).readAllBytes();
    String csvString = new String(bytes);
    return csvString.split("\n");
  }
}
