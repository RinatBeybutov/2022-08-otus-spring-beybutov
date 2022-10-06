package ru.otus.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.SpringBootDemo;
import ru.otus.config.AppProps;
import ru.otus.domain.Question;

@Service
public class CsvReader implements QuestionReader {

  private final AppProps properties;

  @Autowired
  public CsvReader(AppProps properties) {
    this.properties = properties;
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
    byte[] bytes;
    String pathCsvFile = properties.getLocale().toString().equals("ru_RU") ? properties.getRuLocalizationFile() : properties.getEngLocalizationFile();
    try {
      bytes = SpringBootDemo.class.getResourceAsStream(pathCsvFile).readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String csvString = new String(bytes);
    return csvString.split("\n");
  }
}
