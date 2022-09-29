package ru.otus.spring.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.Main;
import ru.otus.spring.config.CSVConfig;
import ru.otus.spring.domain.Question;

@Service
public class CSVReader implements QuestionReader {

  private final CSVConfig csvConfig;

  @Autowired
  public CSVReader(CSVConfig csvConfig) {
    this.csvConfig = csvConfig;
  }

  @Override
  public List<Question> read() {
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

  private String[] readCsv(){
    byte[] bytes = new byte[0];
    try {
      bytes = Main.class.getResourceAsStream(csvConfig.getCsvPath()).readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String csvString = new String(bytes);
    return csvString.split("\n");
  }
}
