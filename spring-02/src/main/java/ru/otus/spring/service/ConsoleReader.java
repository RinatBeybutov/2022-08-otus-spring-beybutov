package ru.otus.spring.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ConsoleReader implements AnswerReader{

  private final Scanner in;

  public ConsoleReader(InputStream inputStream) {
    this.in = new Scanner(inputStream);
  }

  @Override
  public String read() {
    return in.nextLine();
  }
}
