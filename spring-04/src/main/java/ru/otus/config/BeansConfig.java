package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.AnswerReader;
import ru.otus.service.StreamReader;
import ru.otus.service.TextPrinter;
import ru.otus.service.TextPrinterImpl;

@Configuration
public class BeansConfig {

  @Bean
  public TextPrinter printer() {
    return new TextPrinterImpl(System.out);
  }

  @Bean
  public AnswerReader answerReader() {
    return new StreamReader(System.in);
  }

}
