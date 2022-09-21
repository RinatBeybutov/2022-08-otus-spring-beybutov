package ru.otus.spring.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ru.otus.spring.config.CSVConfig;
import ru.otus.spring.Main;
import ru.otus.spring.domain.Question;

public class Starter {

  List<Question> listQuestions = new ArrayList<>();
  CSVConfig csvConfig;

  PrinterImpl printer;

  public Starter(CSVConfig csvConfig) {
    this.csvConfig = csvConfig;
    printer = new PrinterImpl(System.out);
  }

  public void start() {
    try {
      read();
    } catch (IOException e) {
      System.out.println(e.getMessage());;
    }
    print();
  }

  private void read() throws IOException {
    byte[] bytes = Main.class.getResourceAsStream(csvConfig.getCsvPath()).readAllBytes();
    String csvString = new String(bytes);
    String[] lines = csvString.split("\n");
    for (String line : lines) {
      String[] fields = line.split(";");
      listQuestions.add(new Question(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3]));
    }
  }

  private void print() {
    listQuestions.forEach(q -> printer.print(q.toString()));
  }
}
