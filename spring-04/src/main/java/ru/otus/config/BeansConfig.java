package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.AnswerReader;
import ru.otus.service.ConsoleReader;
import ru.otus.service.Printer;
import ru.otus.service.PrinterImpl;

@Configuration
public class BeansConfig {

  @Bean
  public Printer printer() {
    return new PrinterImpl(System.out);
  }

  @Bean
  public AnswerReader answerReader() {
    return new ConsoleReader(System.in);
  }

}
