package ru.otus.service;

import java.io.PrintStream;

public class TextPrinterImpl implements TextPrinter {

  private final PrintStream output;

  public TextPrinterImpl(PrintStream output) {
    this.output = output;
  }

  public void print(String stringToPrint) {
    output.println(stringToPrint);
  }

}
