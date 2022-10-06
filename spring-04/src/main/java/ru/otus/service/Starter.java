package ru.otus.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProps;
import ru.otus.domain.Question;

@Service
public class Starter {

  private final Printer printer;
  private final AnswerReader answerReader;
  private final AppProps properties;
  private final MessageSource messageSource;
  private final QuestionReader questionReader;

  @Autowired
  public Starter(Printer printer, AnswerReader answerReader, MessageSource messageSource, AppProps props,
      QuestionReader questionReader) {
    this.printer = printer;
    this.answerReader = answerReader;
    this.properties = props;
    this.messageSource = messageSource;
    this.questionReader = questionReader;
  }

  public void start() {
    List<Question> questions = questionReader.read();
    String name = enterName();
    int countCorrectAnswers = askQuestions(questions);
    printResult(countCorrectAnswers, name);
  }

  private String enterName() {
    printer.print(messageSource.getMessage("greetings", null, properties.getLocale()));
    return answerReader.readAnswer();
  }

  private void printResult(int countCorrectAnswers, String name) {
    String you = messageSource.getMessage("you", null, properties.getLocale());
    String correctAnswers = messageSource.getMessage("correctAnswers", null,properties.getLocale());
    String passed = messageSource.getMessage("passed", null,properties.getLocale());
    String failed = messageSource.getMessage("failed", null,properties.getLocale());
    printer.print(name + ", " + you + " " + countCorrectAnswers + " " + correctAnswers);
    printer.print(countCorrectAnswers >= properties.getCountQuestionForPass() ? passed : failed);
  }

  private int askQuestions(List<Question> questions) {
    int correctAnswers = 0;
    for (Question q : questions) {
      printer.print(q.toString());
      String answer = answerReader.readAnswer();
      if(answer.equals(q.getCorrectAnswer())) {
        correctAnswers++;
      }
    }
    return correctAnswers;
  }
}
