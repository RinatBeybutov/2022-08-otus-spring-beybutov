package ru.otus.service;

import java.io.InputStream;
import java.util.Scanner;

public class StreamReader implements AnswerReader{

  private final Scanner in;

  public StreamReader(InputStream inputStream) {
    this.in = new Scanner(inputStream);
  }

  @Override
  public String readAnswer() {
    return in.nextLine();
  }
}
