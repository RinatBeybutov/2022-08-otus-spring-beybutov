package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.AnswerReader;
import ru.otus.spring.service.ConsoleReader;
import ru.otus.spring.service.Printer;
import ru.otus.spring.service.PrinterImpl;

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
