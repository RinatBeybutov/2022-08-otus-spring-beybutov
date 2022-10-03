package ru.otus.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProps;
import ru.otus.domain.Question;

@Service
public class I18Reader implements QuestionReader {

  private final MessageSource messageSource;

  private final AppProps properties;

  @Autowired
  public I18Reader(MessageSource messageSource, AppProps properties) {
    this.messageSource = messageSource;
    this.properties = properties;
  }

  @Override
  public List<Question> read() {
    List<Question> questions = new ArrayList<>();
    for(int i=1; i<= AppProps.COUNT_QUESTIONS; i++) {
      String localizedQuestion = messageSource.getMessage("question" + i, null, properties.getLocale());
      String firstLocalizedQAnswer = messageSource.getMessage("answer"+i+"_1", null, properties.getLocale());
      String secondLocalizedQAnswer = messageSource.getMessage("answer"+i+"_2", null, properties.getLocale());
      String correctLocalizedQAnswer = messageSource.getMessage("answer"+i+"_3", null, properties.getLocale());
      Question question = new Question(
          i,
          localizedQuestion,
          new ArrayList<>(Arrays.asList(firstLocalizedQAnswer, secondLocalizedQAnswer)),
          correctLocalizedQAnswer);
      questions.add(question);
    }
    return questions;
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
}
