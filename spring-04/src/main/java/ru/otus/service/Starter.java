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
  private final AppProps appProps;
  private final MessageSource messageSource;

  private final QuestionReader questionReader;

  @Autowired
  public Starter(Printer printer, AnswerReader answerReader, MessageSource messageSource, AppProps props,
      QuestionReader questionReader) {
    this.printer = printer;
    this.answerReader = answerReader;
    this.appProps = props;
    this.messageSource = messageSource;
    this.questionReader = questionReader;
    start();
  }

  public void start() {
    List<Question> questions = questionReader.read();
    String name = enterName();
    int countCorrectAnswers = askQuestions(questions);
    printResult(countCorrectAnswers, name);
  }

  private String enterName() {
    printer.print("Enter name and surname:");
    return answerReader.read();
  }

  private void printResult(int countCorrectAnswers, String name) {
    printer.print(name + ", you have " + countCorrectAnswers + " correct answers");
    printer.print(countCorrectAnswers >= appProps.getCountQuestionForPass() ? "You passed" : "You failed");
  }

  private int askQuestions(List<Question> questions) {
    int correctAnswers = 0;
    for (Question q : questions) {
      printer.print(q.toString());
      String answer = answerReader.read();
      if(answer.equals(q.getCorrectAnswer())) {
        correctAnswers++;
      }
    }
    return correctAnswers;
  }
}
