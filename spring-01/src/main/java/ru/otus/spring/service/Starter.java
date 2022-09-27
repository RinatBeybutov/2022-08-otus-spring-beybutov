package ru.otus.spring.service;

import java.io.IOException;
import java.util.List;
import ru.otus.spring.domain.Question;

public class Starter {
  private final Reader reader;
  private final Printer printer;

  public Starter(Reader reader, Printer printer) {
    this.reader = reader;
    this.printer = printer;
  }

  public void start() {
    try {
      List<Question> questions = reader.read();
      print(questions);
    } catch (IOException e) {
      printer.print(e.getMessage());
    }
  }

  private void print(List<Question> questions) {
    questions.forEach(q -> printer.print(q.toString()));
  }
}
