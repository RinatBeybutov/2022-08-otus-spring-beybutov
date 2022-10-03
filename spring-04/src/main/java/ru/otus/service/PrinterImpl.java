package ru.otus.service;

import java.io.PrintStream;

public class PrinterImpl implements Printer {

  private final PrintStream output;

  public PrinterImpl(PrintStream output) {
    this.output = output;
  }

  public void print(String stringToPrint) {
    output.println(stringToPrint);
  }

}
